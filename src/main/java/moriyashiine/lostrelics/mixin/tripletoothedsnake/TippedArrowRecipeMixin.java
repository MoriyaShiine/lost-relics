/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.mixin.tripletoothedsnake;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.lostrelics.common.init.ModComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.TippedArrowRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TippedArrowRecipe.class)
public class TippedArrowRecipeMixin {
	@ModifyReturnValue(method = "craft(Lnet/minecraft/recipe/input/CraftingRecipeInput;Lnet/minecraft/registry/RegistryWrapper$WrapperLookup;)Lnet/minecraft/item/ItemStack;", at = @At(value = "RETURN", ordinal = 1))
	private ItemStack lostrelics$tripleToothedSnake(ItemStack original, @Local(ordinal = 0) ItemStack inventoryStack) {
		if (inventoryStack.contains(ModComponentTypes.TAINTED_POTION)) {
			original.set(ModComponentTypes.TAINTED_POTION, true);
		}
		return original;
	}
}
