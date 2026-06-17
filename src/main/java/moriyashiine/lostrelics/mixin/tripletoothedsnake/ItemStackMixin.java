/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.mixin.tripletoothedsnake;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.lostrelics.common.init.LostRelicsDataComponents;
import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements ItemInstance, DataComponentHolder {
	@Shadow
	public abstract Item getItem();

	@ModifyReturnValue(method = "getItemName", at = @At("RETURN"))
	private Component lostrelics$tripleToothedSnake(Component original) {
		if (has(LostRelicsDataComponents.TAINTED_POTION)) {
			if (is(Items.POTION) || is(Items.SPLASH_POTION) || is(Items.LINGERING_POTION) || is(Items.TIPPED_ARROW)) {
				return Component.translatable((getItem().getDescriptionId() + ".lostrelics.tainted_potion").replace(".effect.empty", ""));
			}
		}
		return original;
	}
}
