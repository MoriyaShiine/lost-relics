/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.data.provider;

import moriyashiine.lostrelics.client.render.item.property.numeric.SnakeChargeProperty;
import moriyashiine.lostrelics.common.LostRelics;
import moriyashiine.lostrelics.common.init.ModItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.*;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.item.Item;

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
		registerTripleToothedSnake(generator, ModItems.TRIPLE_TOOTHED_SNAKE);
		generator.register(ModItems.TAINTED_BLOOD_CRYSTAL, Models.GENERATED);
		Models.CROSSBOW.upload(LostRelics.id("item/crossbow_tainted_blood_crystal"), TextureMap.layer0(LostRelics.id("item/crossbow_tainted_blood_crystal")), generator.modelCollector);
		generator.register(ModItems.TURQUOISE_EYE, Models.GENERATED);
	}

	public static void registerTripleToothedSnake(ItemModelGenerator generator, Item item) {
		ItemModel.Unbaked none = ItemModels.basic(generator.upload(item, SMALL_HANDHELD));
		ItemModel.Unbaked one = ItemModels.basic(generator.registerSubModel(item, "_1", SMALL_HANDHELD));
		ItemModel.Unbaked two = ItemModels.basic(generator.registerSubModel(item, "_2", SMALL_HANDHELD));
		ItemModel.Unbaked three = ItemModels.basic(generator.registerSubModel(item, "_3", SMALL_HANDHELD));
		ItemModel.Unbaked four = ItemModels.basic(generator.registerSubModel(item, "_4", SMALL_HANDHELD));
		generator.output.accept(
				item,
				ItemModels.rangeDispatch(
						new SnakeChargeProperty(),
						1,
						none,
						ItemModels.rangeDispatchEntry(one, 1),
						ItemModels.rangeDispatchEntry(two, 2),
						ItemModels.rangeDispatchEntry(three, 3),
						ItemModels.rangeDispatchEntry(four, 4)
				)
		);
	}
}
