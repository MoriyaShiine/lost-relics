package moriyashiine.lostrelics.common.world.item;

import com.swacky.ohmega.api.common.item.AccessoryHelper;
import com.swacky.ohmega.api.common.item.EquipContext;
import moriyashiine.lostrelics.common.LostRelics;
import moriyashiine.lostrelics.common.init.LostRelicsItems;
import moriyashiine.lostrelics.common.init.LostRelicsSoundEvents;
import moriyashiine.lostrelics.common.tag.LostRelicsMobEffectTags;
import moriyashiine.lostrelics.common.util.LostRelicsUtil;
import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import moriyashiine.strawberrylib.api.objects.enums.ParticleAnchor;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Consumer;

public class CursedAmuletItem extends EquippableRelicItem {
	public static final Map<Holder<Attribute>, AttributeModifier> GOOD_MODIFIERS, BAD_MODIFIERS;

	static {
		GOOD_MODIFIERS = new HashMap<>();
		GOOD_MODIFIERS.put(Attributes.ARMOR, new AttributeModifier(LostRelics.id("cursed_amulet_good_armor"), 4, AttributeModifier.Operation.ADD_VALUE));
		GOOD_MODIFIERS.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(LostRelics.id("cursed_amulet_good_attack_damage"), 3, AttributeModifier.Operation.ADD_VALUE));

		BAD_MODIFIERS = new HashMap<>();
		BAD_MODIFIERS.put(Attributes.ARMOR, new AttributeModifier(LostRelics.id("cursed_amulet_bad_armor"), -4, AttributeModifier.Operation.ADD_VALUE));
		BAD_MODIFIERS.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(LostRelics.id("cursed_amulet_bad_attack_damage"), -3, AttributeModifier.Operation.ADD_VALUE));
	}

	public CursedAmuletItem(Properties properties) {
		super(properties);
	}

	@Override
	public void accessoryTick(@NonNull LivingEntity entity, @NonNull ItemStack stack) {
		if (entity.level() instanceof ServerLevel level) {
			boolean apply = entity.slib$isSurvival();
			boolean applyNegative = level.isBrightOutside() && level.canSeeSky(entity.blockPosition());
			GOOD_MODIFIERS.forEach((attribute, modifier) -> SLibUtils.applyAttributeModifier(entity, attribute, modifier, apply && !applyNegative));
			BAD_MODIFIERS.forEach((attribute, modifier) -> SLibUtils.applyAttributeModifier(entity, attribute, modifier, apply && applyNegative));
		}
		if (LostRelics.nyctoLoaded && NyctoAPI.isVampire(entity)) {
			ItemStack relic = stack.copyAndClear();
			AccessoryHelper.getData(entity).doUnequip(entity, relic, EquipContext.SLOT);
			if (entity.level() instanceof ServerLevel level) {
				SLibUtils.insertOrDrop(level, entity, relic);
			}
		}
	}

	@Override
	public boolean canEquip(@NonNull LivingEntity entity, @NonNull ItemStack stack, @NonNull EquipContext context) {
		if (LostRelics.nyctoLoaded && NyctoAPI.isVampire(entity)) {
			return false;
		}
		return super.canEquip(entity, stack, context);
	}

	@Override
	public void onEquip(@NonNull LivingEntity entity, @NonNull ItemStack stack, @NonNull EquipContext context) {
		if (!entity.level().isClientSide()) {
			SLibUtils.playSound(entity, LostRelicsSoundEvents.ENTITY_GENERIC_TRANSFORM);
			SLibUtils.addParticles(entity, ParticleTypes.SMOKE, 48, ParticleAnchor.BODY);
		}
		for (MobEffectInstance effect : new HashSet<>(entity.getActiveEffects())) {
			if (isEffectPreventable(effect.getEffect())) {
				entity.removeEffect(effect.getEffect());
			}
		}
	}

	@Override
	public void onUnequip(@NonNull LivingEntity entity, @NonNull ItemStack stack, @NonNull EquipContext context) {
		if (!entity.level().isClientSide()) {
			SLibUtils.playSound(entity, LostRelicsSoundEvents.ENTITY_GENERIC_TRANSFORM);
			SLibUtils.addParticles(entity, ParticleTypes.SMOKE, 48, ParticleAnchor.BODY);
			GOOD_MODIFIERS.forEach((attribute, modifier) -> SLibUtils.applyAttributeModifier(entity, attribute, modifier, false));
			BAD_MODIFIERS.forEach((attribute, modifier) -> SLibUtils.applyAttributeModifier(entity, attribute, modifier, false));
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay displayComponent, Consumer<Component> textConsumer, TooltipFlag type) {
		textConsumer.accept(Component.empty());
		textConsumer.accept(Component.translatable("item.modifiers.armor").withStyle(ChatFormatting.GRAY));
		GOOD_MODIFIERS.forEach((attribute, modifier) -> textConsumer.accept(Component.translatable("attribute.modifier.plus." + modifier.operation().id(), ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(modifier.amount()), Component.translatable(attribute.value().getDescriptionId())).append("?").withStyle(ChatFormatting.LIGHT_PURPLE)));
	}

	public static boolean doNegativesApply(Entity entity) {
		if (entity instanceof LivingEntity living && living.slib$isSurvival()) {
			return LostRelicsUtil.hasRelic(living, LostRelicsItems.CURSED_AMULET);
		}
		return false;
	}

	public static boolean isEffectPreventable(Holder<MobEffect> effect) {
		return effect.value().getCategory() == MobEffectCategory.HARMFUL && !effect.is(LostRelicsMobEffectTags.BYPASSES_CURSED_AMULET);
	}
}
