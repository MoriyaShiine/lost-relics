package moriyashiine.lostrelics.common.init;

import moriyashiine.lostrelics.common.world.item.crafting.TaintedBloodCrystalRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerRecipeSerializer;

public class LostRelicsRecipeSerializers {
	public static final RecipeSerializer<TaintedBloodCrystalRecipe> TAINTED_BLOOD_CRYSTAL = registerRecipeSerializer("tainted_blood_crystal", new RecipeSerializer<>(TaintedBloodCrystalRecipe.MAP_CODEC, TaintedBloodCrystalRecipe.STREAM_CODEC));

	public static void init() {
	}
}
