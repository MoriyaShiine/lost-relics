/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.mixin.tripletoothedsnake;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.lostrelics.common.init.ModComponentTypes;
import moriyashiine.lostrelics.common.init.ModItems;
import moriyashiine.lostrelics.common.world.item.TripleToothedSnakeItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PotionBrewing.class)
public class PotionBrewingMixin {
	@ModifyReturnValue(method = "mix", at = @At("RETURN"))
	private ItemStack lostrelics$tripleToothedSnake(ItemStack original, ItemStack ingredient, ItemStack source) {
		if (source.has(ModComponentTypes.TAINTED_POTION)) {
			if (source.is(Items.POTION) && ingredient.is(Items.GUNPOWDER)) {
				return source.transmuteCopy(Items.SPLASH_POTION, 1);
			} else if (source.is(Items.SPLASH_POTION) && ingredient.is(Items.DRAGON_BREATH)) {
				return source.transmuteCopy(Items.LINGERING_POTION, 1);
			}
		}
		if (source.getItem() instanceof PotionItem potionItem && isAwkward(source) && ingredient.is(ModItems.TAINTED_BLOOD_CRYSTAL)) {
			ItemStack potionStack = potionItem.getDefaultInstance();
			potionStack.set(DataComponents.POTION_CONTENTS, TripleToothedSnakeItem.create(ingredient.get(DataComponents.POTION_CONTENTS).getAllEffects()));
			potionStack.set(ModComponentTypes.TAINTED_POTION, true);
			return potionStack;
		}
		return original;
	}

	@ModifyReturnValue(method = "hasPotionMix", at = @At("RETURN"))
	private boolean lostrelics$tripleToothedSnake(boolean original, ItemStack source, ItemStack ingredient) {
		return original || (isAwkward(source) && ingredient.is(ModItems.TAINTED_BLOOD_CRYSTAL));
	}

	@ModifyReturnValue(method = "isPotionIngredient", at = @At("RETURN"))
	private boolean lostrelics$tripleToothedSnake(boolean original, ItemStack ingredient) {
		return original || (ingredient.is(ModItems.TAINTED_BLOOD_CRYSTAL) && ingredient.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).hasEffects());
	}

	@Unique
	private static boolean isAwkward(ItemStack stack) {
		return stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).potion().orElse(null) == Potions.AWKWARD;
	}
}
