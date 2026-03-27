/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.common.world.item;

import moriyashiine.lostrelics.common.LostRelics;
import moriyashiine.lostrelics.common.init.ModItems;
import moriyashiine.lostrelics.common.init.ModSoundEvents;
import moriyashiine.lostrelics.common.tag.ModMobEffectTags;
import moriyashiine.lostrelics.common.util.LostRelicsUtil;
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
import net.minecraft.world.entity.player.Player;
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
	public void tick(@NonNull Player player, @NonNull ItemStack stack) {
		if (player.level() instanceof ServerLevel level) {
			boolean apply = !player.isCreative() && player.slib$exists();
			boolean applyNegative = level.isBrightOutside() && level.canSeeSky(player.blockPosition());
			GOOD_MODIFIERS.forEach((attribute, modifier) -> SLibUtils.conditionallyApplyAttributeModifier(player, attribute, modifier, apply && !applyNegative));
			BAD_MODIFIERS.forEach((attribute, modifier) -> SLibUtils.conditionallyApplyAttributeModifier(player, attribute, modifier, apply && applyNegative));
		}
	}

	@Override
	public void onEquip(@NonNull Player player, @NonNull ItemStack stack) {
		if (!player.level().isClientSide()) {
			SLibUtils.playSound(player, ModSoundEvents.ENTITY_GENERIC_TRANSFORM);
			SLibUtils.addParticles(player, ParticleTypes.SMOKE, 48, ParticleAnchor.BODY);
		}
		for (MobEffectInstance effect : new HashSet<>(player.getActiveEffects())) {
			if (isEffectPreventable(effect.getEffect())) {
				player.removeEffect(effect.getEffect());
			}
		}
	}

	@Override
	public void onUnequip(@NonNull Player player, @NonNull ItemStack stack) {
		if (!player.level().isClientSide()) {
			SLibUtils.playSound(player, ModSoundEvents.ENTITY_GENERIC_TRANSFORM);
			SLibUtils.addParticles(player, ParticleTypes.SMOKE, 48, ParticleAnchor.BODY);
			GOOD_MODIFIERS.forEach((attribute, modifier) -> SLibUtils.conditionallyApplyAttributeModifier(player, attribute, modifier, false));
			BAD_MODIFIERS.forEach((attribute, modifier) -> SLibUtils.conditionallyApplyAttributeModifier(player, attribute, modifier, false));
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay displayComponent, Consumer<Component> textConsumer, TooltipFlag type) {
		textConsumer.accept(Component.empty());
		textConsumer.accept(Component.translatable("item.modifiers.armor").withStyle(ChatFormatting.GRAY));
		GOOD_MODIFIERS.forEach((attribute, modifier) -> textConsumer.accept(Component.translatable("attribute.modifier.plus." + modifier.operation().id(), ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(modifier.amount()), Component.translatable(attribute.value().getDescriptionId())).append("?").withStyle(ChatFormatting.LIGHT_PURPLE)));
	}

	public static boolean doNegativesApply(Entity entity) {
		if (entity instanceof LivingEntity living && !living.hasInfiniteMaterials()) {
			return LostRelicsUtil.hasRelic(living, ModItems.CURSED_AMULET);
		}
		return false;
	}

	public static boolean isEffectPreventable(Holder<MobEffect> effect) {
		return effect.value().getCategory() == MobEffectCategory.HARMFUL && !effect.is(ModMobEffectTags.BYPASSES_CURSED_AMULET);
	}
}
