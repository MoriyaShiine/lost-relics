/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.common.world.item.crafting;

import com.mojang.serialization.MapCodec;
import moriyashiine.lostrelics.common.init.LostRelicsItems;
import moriyashiine.lostrelics.common.init.LostRelicsRecipeSerializers;
import moriyashiine.lostrelics.common.world.item.TripleToothedSnakeItem;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class TaintedBloodCrystalRecipe extends CustomRecipe {
	public static final TaintedBloodCrystalRecipe INSTANCE = new TaintedBloodCrystalRecipe();
	public static final MapCodec<TaintedBloodCrystalRecipe> MAP_CODEC = MapCodec.unit(INSTANCE);
	public static final StreamCodec<RegistryFriendlyByteBuf, TaintedBloodCrystalRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);

	@Override
	public boolean matches(CraftingInput input, Level level) {
		int foundSnakes = 0, foundChargedSnakes = 0;
		for (int i = 0; i < input.size(); i++) {
			ItemStack stack = input.getItem(i);
			if (stack.is(LostRelicsItems.TRIPLE_TOOTHED_SNAKE)) {
				foundSnakes++;
				if (TripleToothedSnakeItem.getCharges(stack) > 0) {
					foundChargedSnakes++;
				}
			} else if (!stack.isEmpty()) {
				return false;
			}
		}
		return foundSnakes == 1 && foundChargedSnakes == 1;
	}

	@Override
	public ItemStack assemble(CraftingInput input) {
		for (int i = 0; i < input.size(); i++) {
			ItemStack stack = input.getItem(i);
			if (stack.is(LostRelicsItems.TRIPLE_TOOTHED_SNAKE)) {
				ItemStack crystal = new ItemStack(LostRelicsItems.TAINTED_BLOOD_CRYSTAL, TripleToothedSnakeItem.getCharges(stack));
				crystal.set(DataComponents.POTION_CONTENTS, stack.get(DataComponents.POTION_CONTENTS));
				return crystal;
			}
		}
		return ItemStack.EMPTY;
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
		NonNullList<ItemStack> remainingItems = super.getRemainingItems(input);
		for (int i = 0; i < remainingItems.size(); i++) {
			ItemStack stack = input.getItem(i);
			TripleToothedSnakeItem.setCharges(stack, 0);
			remainingItems.set(i, stack.copy());
		}
		return remainingItems;
	}

	@Override
	public RecipeSerializer<TaintedBloodCrystalRecipe> getSerializer() {
		return LostRelicsRecipeSerializers.TAINTED_BLOOD_CRYSTAL;
	}
}
