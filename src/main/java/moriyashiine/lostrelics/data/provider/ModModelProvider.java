/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.data.provider;

import moriyashiine.lostrelics.client.renderer.item.properties.numeric.SnakeChargeProperty;
import moriyashiine.lostrelics.common.LostRelics;
import moriyashiine.lostrelics.common.init.ModItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.world.item.Item;

import java.util.Optional;

public class ModModelProvider extends FabricModelProvider {
	public static final ModelTemplate FLAT_SMALL_HANDHELD_ITEM = new ModelTemplate(Optional.of(LostRelics.id("item/small_handheld")), Optional.empty(), TextureSlot.LAYER0);

	public ModModelProvider(FabricPackOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators generators) {
	}

	@Override
	public void generateItemModels(ItemModelGenerators generators) {
		generators.generateFlatItem(ModItems.JUNGLE_ALTAR, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(ModItems.CURSED_AMULET, ModelTemplates.FLAT_ITEM);
		generators.generateFlatItem(ModItems.SMOKING_MIRROR, ModelTemplates.FLAT_ITEM);
		registerTripleToothedSnake(generators, ModItems.TRIPLE_TOOTHED_SNAKE);
		generators.generateFlatItem(ModItems.TAINTED_BLOOD_CRYSTAL, ModelTemplates.FLAT_ITEM);
		ModelTemplates.CROSSBOW.create(LostRelics.id("item/crossbow_tainted_blood_crystal"), TextureMapping.layer0(new Material(LostRelics.id("item/crossbow_tainted_blood_crystal"))), generators.modelOutput);
		generators.generateFlatItem(ModItems.TURQUOISE_EYE, ModelTemplates.FLAT_ITEM);
	}

	public static void registerTripleToothedSnake(ItemModelGenerators generators, Item item) {
		ItemModel.Unbaked none = ItemModelUtils.plainModel(generators.createFlatItemModel(item, FLAT_SMALL_HANDHELD_ITEM));
		ItemModel.Unbaked one = ItemModelUtils.plainModel(generators.createFlatItemModel(item, "_1", FLAT_SMALL_HANDHELD_ITEM));
		ItemModel.Unbaked two = ItemModelUtils.plainModel(generators.createFlatItemModel(item, "_2", FLAT_SMALL_HANDHELD_ITEM));
		ItemModel.Unbaked three = ItemModelUtils.plainModel(generators.createFlatItemModel(item, "_3", FLAT_SMALL_HANDHELD_ITEM));
		ItemModel.Unbaked four = ItemModelUtils.plainModel(generators.createFlatItemModel(item, "_4", FLAT_SMALL_HANDHELD_ITEM));
		generators.itemModelOutput.accept(
				item,
				ItemModelUtils.rangeSelect(
						new SnakeChargeProperty(),
						1,
						none,
						ItemModelUtils.override(one, 1),
						ItemModelUtils.override(two, 2),
						ItemModelUtils.override(three, 3),
						ItemModelUtils.override(four, 4)
				)
		);
	}
}
