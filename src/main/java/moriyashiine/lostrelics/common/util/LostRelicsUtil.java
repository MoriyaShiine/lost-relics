/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class LostRelicsUtil {
	public static boolean hasRelic(LivingEntity entity, Item relic) {
		return !getRelic(entity, relic).isEmpty();
	}

	public static ItemStack getRelic(LivingEntity entity, Item relic) {
		if (entity instanceof PlayerEntity player) {
			for (ItemStack stack : player.getInventory()) {
				if (stack.isOf(relic)) {
					return stack;
				}
			}
		}
		return ItemStack.EMPTY;
	}
}
