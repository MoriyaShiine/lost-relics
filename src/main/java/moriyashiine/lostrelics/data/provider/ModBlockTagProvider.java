/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.data.provider;

import moriyashiine.lostrelics.common.init.ModBlocks;
import moriyashiine.lostrelics.common.tag.ModBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
	public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
		valueLookupBuilder(ModBlockTags.TREASURE)
				.forceAddTag(ConventionalBlockTags.ORES)
				.forceAddTag(ConventionalBlockTags.CHESTS);
		valueLookupBuilder(ModBlockTags.UNIMPORTANT_TREASURE)
				.forceAddTag(BlockTags.COAL_ORES)
				.forceAddTag(BlockTags.COPPER_ORES)
				.forceAddTag(BlockTags.IRON_ORES);

		valueLookupBuilder(BlockTags.PICKAXE_MINEABLE)
				.add(ModBlocks.JUNGLE_ALTAR);
		valueLookupBuilder(BlockTags.NEEDS_DIAMOND_TOOL)
				.add(ModBlocks.JUNGLE_ALTAR);
	}
}
