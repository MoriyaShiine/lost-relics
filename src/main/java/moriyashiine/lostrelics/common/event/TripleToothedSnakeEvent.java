/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.event;

import moriyashiine.lostrelics.common.init.ModItems;
import net.fabricmc.fabric.api.item.v1.EnchantingContext;
import net.fabricmc.fabric.api.item.v1.EnchantmentEvents;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;

public class TripleToothedSnakeEvent implements EnchantmentEvents.AllowEnchanting {
	@Override
	public TriState allowEnchanting(RegistryEntry<Enchantment> enchantment, ItemStack target, EnchantingContext enchantingContext) {
		if (target.isOf(ModItems.TRIPLE_TOOTHED_SNAKE)) {
			if (enchantment.matchesKey(Enchantments.UNBREAKING) || enchantment.matchesKey(Enchantments.MENDING)) {
				return TriState.FALSE;
			}
		}
		return TriState.DEFAULT;
	}
}
