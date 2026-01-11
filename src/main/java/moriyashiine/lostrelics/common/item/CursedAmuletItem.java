/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.item;

import moriyashiine.lostrelics.common.LostRelics;
import moriyashiine.lostrelics.common.init.ModItems;
import moriyashiine.lostrelics.common.init.ModSoundEvents;
import moriyashiine.lostrelics.common.tag.ModStatusEffectTags;
import moriyashiine.lostrelics.common.util.LostRelicsUtil;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import moriyashiine.strawberrylib.api.objects.enums.ParticleAnchor;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Consumer;

public class CursedAmuletItem extends EquippableRelicItem {
	public static final Map<RegistryEntry<EntityAttribute>, EntityAttributeModifier> GOOD_MODIFIERS, BAD_MODIFIERS;

	static {
		GOOD_MODIFIERS = new HashMap<>();
		GOOD_MODIFIERS.put(EntityAttributes.ARMOR, new EntityAttributeModifier(LostRelics.id("cursed_amulet_good_armor"), 4, EntityAttributeModifier.Operation.ADD_VALUE));
		GOOD_MODIFIERS.put(EntityAttributes.ATTACK_DAMAGE, new EntityAttributeModifier(LostRelics.id("cursed_amulet_good_attack_damage"), 3, EntityAttributeModifier.Operation.ADD_VALUE));

		BAD_MODIFIERS = new HashMap<>();
		BAD_MODIFIERS.put(EntityAttributes.ARMOR, new EntityAttributeModifier(LostRelics.id("cursed_amulet_bad_armor"), -4, EntityAttributeModifier.Operation.ADD_VALUE));
		BAD_MODIFIERS.put(EntityAttributes.ATTACK_DAMAGE, new EntityAttributeModifier(LostRelics.id("cursed_amulet_bad_attack_damage"), -3, EntityAttributeModifier.Operation.ADD_VALUE));
	}

	public CursedAmuletItem(Settings settings) {
		super(settings);
	}

	@Override
	public void tick(@NonNull PlayerEntity player, @NonNull ItemStack stack) {
		if (player.getEntityWorld() instanceof ServerWorld world) {
			boolean apply = !player.isCreative() && player.isPartOfGame();
			boolean applyNegative = world.isDay() && world.isSkyVisible(player.getBlockPos());
			GOOD_MODIFIERS.forEach((attribute, modifier) -> SLibUtils.conditionallyApplyAttributeModifier(player, attribute, modifier, apply && !applyNegative));
			BAD_MODIFIERS.forEach((attribute, modifier) -> SLibUtils.conditionallyApplyAttributeModifier(player, attribute, modifier, apply && applyNegative));
		}
	}

	@Override
	public void onEquip(@NonNull PlayerEntity player, @NonNull ItemStack stack) {
		if (!player.getEntityWorld().isClient()) {
			SLibUtils.playSound(player, ModSoundEvents.ENTITY_GENERIC_TRANSFORM);
			SLibUtils.addParticles(player, ParticleTypes.SMOKE, 48, ParticleAnchor.BODY);
		}
		for (StatusEffectInstance effect : new HashSet<>(player.getStatusEffects())) {
			if (isEffectPreventable(effect.getEffectType())) {
				player.removeStatusEffect(effect.getEffectType());
			}
		}
	}

	@Override
	public void onUnequip(@NonNull PlayerEntity player, @NonNull ItemStack stack) {
		if (!player.getEntityWorld().isClient()) {
			SLibUtils.playSound(player, ModSoundEvents.ENTITY_GENERIC_TRANSFORM);
			SLibUtils.addParticles(player, ParticleTypes.SMOKE, 48, ParticleAnchor.BODY);
			GOOD_MODIFIERS.forEach((attribute, modifier) -> SLibUtils.conditionallyApplyAttributeModifier(player, attribute, modifier, false));
			BAD_MODIFIERS.forEach((attribute, modifier) -> SLibUtils.conditionallyApplyAttributeModifier(player, attribute, modifier, false));
		}
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
		textConsumer.accept(Text.empty());
		textConsumer.accept(Text.translatable("item.modifiers.armor").formatted(Formatting.GRAY));
		GOOD_MODIFIERS.forEach((attribute, modifier) -> textConsumer.accept(Text.translatable("attribute.modifier.plus." + modifier.operation().getId(), AttributeModifiersComponent.DECIMAL_FORMAT.format(modifier.value()), Text.translatable(attribute.value().getTranslationKey())).append("?").formatted(Formatting.LIGHT_PURPLE)));
	}

	public static boolean doNegativesApply(Entity entity) {
		if (entity instanceof LivingEntity living && !living.isInCreativeMode()) {
			return LostRelicsUtil.hasRelic(living, ModItems.CURSED_AMULET);
		}
		return false;
	}

	public static boolean isEffectPreventable(RegistryEntry<StatusEffect> effect) {
		return effect.value().getCategory() == StatusEffectCategory.HARMFUL && !effect.isIn(ModStatusEffectTags.BYPASSES_CURSED_AMULET);
	}
}
