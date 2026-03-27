/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.data.provider;

import moriyashiine.lostrelics.common.init.ModBlocks;
import moriyashiine.lostrelics.common.tag.ModBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends FabricTagsProvider.BlockTagsProvider {
	public ModBlockTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		valueLookupBuilder(ModBlockTags.TREASURE)
				.forceAddTag(ConventionalBlockTags.ORES)
				.forceAddTag(ConventionalBlockTags.CHESTS);
		valueLookupBuilder(ModBlockTags.UNIMPORTANT_TREASURE)
				.forceAddTag(BlockTags.COAL_ORES)
				.forceAddTag(BlockTags.COPPER_ORES)
				.forceAddTag(BlockTags.IRON_ORES);

		valueLookupBuilder(BlockTags.MINEABLE_WITH_PICKAXE)
				.add(ModBlocks.JUNGLE_ALTAR);
		valueLookupBuilder(BlockTags.NEEDS_DIAMOND_TOOL)
				.add(ModBlocks.JUNGLE_ALTAR);
	}
}
