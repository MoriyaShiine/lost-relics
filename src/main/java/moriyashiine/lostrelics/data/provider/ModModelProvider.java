/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.data.provider;

import moriyashiine.lostrelics.common.LostRelics;
import moriyashiine.lostrelics.common.init.ModItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.*;

import java.util.Optional;

public class ModModelProvider extends FabricModelProvider {
	public static final Model SMALL_HANDHELD = new Model(Optional.of(LostRelics.id("item/small_handheld")), Optional.empty(), TextureKey.LAYER0);

	public ModModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator generator) {
	}

	@Override
	public void generateItemModels(ItemModelGenerator generator) {
		generator.register(ModItems.JUNGLE_ALTAR, Models.GENERATED);
		generator.register(ModItems.CURSED_AMULET, Models.GENERATED);
		generator.register(ModItems.SMOKING_MIRROR, Models.GENERATED);
		generator.register(ModItems.TRIPLE_TOOTHED_SNAKE, SMALL_HANDHELD);
		generator.register(ModItems.TURQUOISE_EYE, Models.GENERATED);
	}
}
