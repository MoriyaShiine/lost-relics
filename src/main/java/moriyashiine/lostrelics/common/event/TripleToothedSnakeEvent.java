/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.common.event;

import moriyashiine.lostrelics.common.init.ModItems;
import net.fabricmc.fabric.api.item.v1.EnchantingContext;
import net.fabricmc.fabric.api.item.v1.EnchantmentEvents;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

public class TripleToothedSnakeEvent implements EnchantmentEvents.AllowEnchanting {
	@Override
	public TriState allowEnchanting(Holder<Enchantment> enchantment, ItemStack target, EnchantingContext enchantingContext) {
		if (target.is(ModItems.TRIPLE_TOOTHED_SNAKE)) {
			if (enchantment.is(Enchantments.UNBREAKING) || enchantment.is(Enchantments.MENDING)) {
				return TriState.FALSE;
			}
		}
		return TriState.DEFAULT;
	}
}
