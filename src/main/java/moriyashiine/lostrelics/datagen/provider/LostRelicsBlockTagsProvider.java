/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.datagen.provider;

import moriyashiine.lostrelics.common.references.LostRelicsBlockItemIds;
import moriyashiine.lostrelics.common.tag.ModBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;

import java.util.concurrent.CompletableFuture;

public class LostRelicsBlockTagsProvider extends FabricTagsProvider.BlockTagsProvider {
	public LostRelicsBlockTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		builder(ModBlockTags.TREASURE)
				.forceAddTag(ConventionalBlockTags.ORES)
				.forceAddTag(ConventionalBlockTags.CHESTS);
		builder(ModBlockTags.UNIMPORTANT_TREASURE)
				.forceAddTag(ConventionalBlockTags.COAL_ORES)
				.forceAddTag(BlockTags.COPPER_ORES)
				.forceAddTag(BlockTags.IRON_ORES);

		builder(BlockTags.MINEABLE_WITH_PICKAXE)
				.add(LostRelicsBlockItemIds.JUNGLE_ALTAR);
		builder(BlockTags.NEEDS_DIAMOND_TOOL)
				.add(LostRelicsBlockItemIds.JUNGLE_ALTAR);
	}
}
