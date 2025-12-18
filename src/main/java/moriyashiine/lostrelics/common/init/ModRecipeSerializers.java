/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.init;

import moriyashiine.lostrelics.common.recipe.TaintedBloodCrystalRecipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerRecipeSerializer;

public class ModRecipeSerializers {
	public static final RecipeSerializer<TaintedBloodCrystalRecipe> TAINTED_BLOOD_CRYSTAL = registerRecipeSerializer("tainted_blood_crystal", new SpecialCraftingRecipe.SpecialRecipeSerializer<>(TaintedBloodCrystalRecipe::new));

	public static void init() {
	}
}
