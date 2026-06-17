/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.common.world.item;

import moriyashiine.lostrelics.common.init.LostRelicsDataComponents;
import moriyashiine.lostrelics.common.tag.ModMobEffectTags;
import moriyashiine.lostrelics.common.util.LostRelicsUtil;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class TripleToothedSnakeItem extends Item {
	public TripleToothedSnakeItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (player.isShiftKeyDown() && LostRelicsUtil.isUsable(player, stack)) {
			if (getCharges(stack) == 0) {
				List<MobEffectInstance> effects = new ArrayList<>();
				for (MobEffectInstance instance : player.getActiveEffects()) {
					if (instance.getEffect().value().getCategory() != MobEffectCategory.BENEFICIAL && !instance.getEffect().is(ModMobEffectTags.CANNOT_BE_SIPHONED)) {
						effects.add(instance);
					}
				}
				if (!effects.isEmpty()) {
					if (!level.isClientSide()) {
						setCharges(stack, 4);
						stack.set(DataComponents.POTION_CONTENTS, create(effects));
						effects.forEach(instance -> player.removeEffect(instance.getEffect()));
						LostRelicsUtil.setCooldown(player, stack, 600);
						float absorption = player.getAbsorptionAmount();
						player.setAbsorptionAmount(0);
						SLibUtils.runWithPvpBypass(() -> player.hurt(level.damageSources().indirectMagic(player, player), 8));
						player.setAbsorptionAmount(absorption);
					}
					return InteractionResult.SUCCESS;
				}
			}
		}
		return super.use(level, player, hand);
	}

	@Override
	public void hurtEnemy(ItemStack stack, LivingEntity mob, LivingEntity attacker) {
		int charges = getCharges(stack);
		if (charges > 0) {
			if (stack.has(DataComponents.POTION_CONTENTS)) {
				stack.get(DataComponents.POTION_CONTENTS).getAllEffects().forEach(instance -> mob.addEffect(new MobEffectInstance(instance)));
			}
			if (!attacker.hasInfiniteMaterials()) {
				setCharges(stack, charges - 1);
			}
		}
	}

	public static int getCharges(ItemStack stack) {
		return stack.getOrDefault(LostRelicsDataComponents.SNAKE_CHARGE, 0);
	}

	public static void setCharges(ItemStack stack, int charges) {
		stack.set(LostRelicsDataComponents.SNAKE_CHARGE, charges);
		if (charges == 0) {
			stack.remove(DataComponents.POTION_CONTENTS);
		}
	}

	public static PotionContents create(Iterable<MobEffectInstance> effects) {
		PotionContents potionContents = PotionContents.EMPTY;
		for (MobEffectInstance instance : effects) {
			potionContents = potionContents.withEffectAdded(instance);
		}
		return potionContents;
	}
}
