package moriyashiine.lostrelics.common.world.item;

import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.callback.TrinketCallback;
import moriyashiine.lostrelics.common.LostRelics;
import moriyashiine.lostrelics.common.init.LostRelicsItems;
import moriyashiine.lostrelics.common.init.LostRelicsSoundEvents;
import moriyashiine.lostrelics.common.tag.LostRelicsMobEffectTags;
import moriyashiine.lostrelics.common.util.LostRelicsUtil;
import moriyashiine.nycto.api.NyctoAPI;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import moriyashiine.strawberrylib.api.objects.enums.ParticleAnchor;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.BiConsumer;

public class CursedAmuletItem extends Item implements TrinketCallback {
	private static final Identifier ATTRIBUTE_ID = LostRelics.id("cursed_amulet");
	private static final Identifier GOOD_ATTRIBUTE_ID = ATTRIBUTE_ID.withSuffix("_good");
	private static final Identifier BAD_ATTRIBUTE_ID = ATTRIBUTE_ID.withSuffix("_bad");
	public static final Map<Holder<Attribute>, AttributeModifier> GOOD_MODIFIERS, BAD_MODIFIERS;

	static {
		GOOD_MODIFIERS = new HashMap<>();
		GOOD_MODIFIERS.put(Attributes.ARMOR, new AttributeModifier(GOOD_ATTRIBUTE_ID, 4, AttributeModifier.Operation.ADD_VALUE));
		GOOD_MODIFIERS.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(GOOD_ATTRIBUTE_ID, 3, AttributeModifier.Operation.ADD_VALUE));

		BAD_MODIFIERS = new HashMap<>();
		BAD_MODIFIERS.put(Attributes.ARMOR, new AttributeModifier(BAD_ATTRIBUTE_ID, -4, AttributeModifier.Operation.ADD_VALUE));
		BAD_MODIFIERS.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BAD_ATTRIBUTE_ID, -3, AttributeModifier.Operation.ADD_VALUE));
	}

	public CursedAmuletItem(Properties properties) {
		super(properties);
	}

	@Override
	public void tick(ItemStack stack, TrinketSlotAccess slot, LivingEntity entity) {
		if (entity.level() instanceof ServerLevel level) {
			boolean apply = entity.slib$isSurvival();
			boolean applyNegative = level.isBrightOutside() && level.canSeeSky(entity.blockPosition());
			GOOD_MODIFIERS.forEach((attribute, modifier) -> SLibUtils.applyAttributeModifier(entity, attribute, modifier, apply && !applyNegative));
			BAD_MODIFIERS.forEach((attribute, modifier) -> SLibUtils.applyAttributeModifier(entity, attribute, modifier, apply && applyNegative));
			if (LostRelics.nyctoLoaded && NyctoAPI.isVampire(entity)) {
				SLibUtils.insertOrDrop(level, entity, stack.copyAndClear());
			}
		}
	}

	@Override
	public boolean canEquip(ItemStack stack, TrinketSlotAccess slot, LivingEntity entity) {
		return !LostRelics.nyctoLoaded || !NyctoAPI.isVampire(entity);
	}

	@Override
	public void onEquip(ItemStack stack, TrinketSlotAccess slot, LivingEntity entity) {
		SLibUtils.playSound(entity, LostRelicsSoundEvents.ENTITY_GENERIC_TRANSFORM);
		SLibUtils.addParticles(entity, ParticleTypes.SMOKE, 48, ParticleAnchor.BODY);
		for (MobEffectInstance effect : new HashSet<>(entity.getActiveEffects())) {
			if (isEffectPreventable(effect.getEffect())) {
				entity.removeEffect(effect.getEffect());
			}
		}
	}

	@Override
	public void onUnequip(ItemStack stack, TrinketSlotAccess slot, LivingEntity entity) {
		SLibUtils.playSound(entity, LostRelicsSoundEvents.ENTITY_GENERIC_TRANSFORM);
		SLibUtils.addParticles(entity, ParticleTypes.SMOKE, 48, ParticleAnchor.BODY);
		GOOD_MODIFIERS.forEach((attribute, modifier) -> SLibUtils.applyAttributeModifier(entity, attribute, modifier, false));
		BAD_MODIFIERS.forEach((attribute, modifier) -> SLibUtils.applyAttributeModifier(entity, attribute, modifier, false));
	}

	@Override
	public void forEachTrinketModifier(ItemStack stack, TrinketSlotAccess slot, LivingEntity entity, Identifier slotIdentifier, BiConsumer<Holder<Attribute>, AttributeModifier> consumer) {
		GOOD_MODIFIERS.forEach((attribute, modifier) -> consumer.accept(attribute, new AttributeModifier(ATTRIBUTE_ID, 0, modifier.operation())));
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
