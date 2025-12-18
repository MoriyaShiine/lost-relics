/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.item;

import moriyashiine.lostrelics.common.init.ModComponentTypes;
import moriyashiine.lostrelics.common.tag.ModStatusEffectTags;
import moriyashiine.lostrelics.common.util.LostRelicsUtil;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class TripleToothedSnakeItem extends Item {
	public TripleToothedSnakeItem(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult use(World world, PlayerEntity user, Hand hand) {
		ItemStack stack = user.getStackInHand(hand);
		if (user.isSneaking() && LostRelicsUtil.isUsable(user, stack)) {
			if (getCharges(stack) == 0) {
				List<StatusEffectInstance> effects = new ArrayList<>();
				for (StatusEffectInstance instance : user.getStatusEffects()) {
					if (instance.getEffectType().value().getCategory() != StatusEffectCategory.BENEFICIAL && !instance.getEffectType().isIn(ModStatusEffectTags.CANNOT_BE_SIPHONED)) {
						effects.add(instance);
					}
				}
				if (!effects.isEmpty()) {
					if (!world.isClient()) {
						setCharges(stack, 4);
						stack.set(DataComponentTypes.POTION_CONTENTS, create(effects));
						effects.forEach(instance -> user.removeStatusEffect(instance.getEffectType()));
						LostRelicsUtil.setCooldown(user, stack, 600);
						float absorption = user.getAbsorptionAmount();
						user.setAbsorptionAmount(0);
						user.serverDamage(world.getDamageSources().indirectMagic(user, user), 8);
						user.setAbsorptionAmount(absorption);
					}
					return ActionResult.SUCCESS;
				}
			}
		}
		return super.use(world, user, hand);
	}

	@Override
	public void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		int charges = getCharges(stack);
		if (charges > 0) {
			if (stack.contains(DataComponentTypes.POTION_CONTENTS)) {
				stack.get(DataComponentTypes.POTION_CONTENTS).getEffects().forEach(instance -> target.addStatusEffect(new StatusEffectInstance(instance)));
			}
			if (!attacker.isInCreativeMode()) {
				setCharges(stack, charges - 1);
			}
		}
	}

	public static int getCharges(ItemStack stack) {
		return stack.getOrDefault(ModComponentTypes.SNAKE_CHARGE, 0);
	}

	public static void setCharges(ItemStack stack, int charges) {
		stack.set(ModComponentTypes.SNAKE_CHARGE, charges);
		if (charges == 0) {
			stack.remove(DataComponentTypes.POTION_CONTENTS);
		}
	}

	public static PotionContentsComponent create(Iterable<StatusEffectInstance> effects) {
		PotionContentsComponent potionContentsComponent = PotionContentsComponent.DEFAULT;
		for (StatusEffectInstance instance : effects) {
			potionContentsComponent = potionContentsComponent.with(instance);
		}
		return potionContentsComponent;
	}
}
