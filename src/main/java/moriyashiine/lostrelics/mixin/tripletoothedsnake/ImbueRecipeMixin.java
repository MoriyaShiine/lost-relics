/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.mixin.tripletoothedsnake;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.lostrelics.common.init.ModComponentTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.ImbueRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ImbueRecipe.class)
public class ImbueRecipeMixin {
	@ModifyReturnValue(method = "assemble(Lnet/minecraft/world/item/crafting/CraftingInput;)Lnet/minecraft/world/item/ItemStack;", at = @At("RETURN"))
	private ItemStack lostrelics$tripleToothedSnake(ItemStack original, @Local(name = "source") ItemStack source) {
		if (source.is(Items.LINGERING_POTION) && original.is(Items.TIPPED_ARROW) && source.has(ModComponentTypes.TAINTED_POTION)) {
			original.set(ModComponentTypes.TAINTED_POTION, true);
		}
		return original;
	}
}
