/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.data.provider;

import moriyashiine.lostrelics.common.init.ModItems;
import moriyashiine.lostrelics.common.tag.ModItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
	public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
		valueLookupBuilder(ModItemTags.RELICS)
				.addTag(ModItemTags.JUNGLE_RELICS);
		valueLookupBuilder(ModItemTags.JUNGLE_RELICS).add(
				ModItems.CURSED_AMULET,
				ModItems.SMOKING_MIRROR,
				ModItems.TRIPLE_TOOTHED_SNAKE,
				ModItems.TURQUOISE_EYE);

		valueLookupBuilder(ItemTags.ARROWS)
				.add(ModItems.TAINTED_BLOOD_CRYSTAL);
		valueLookupBuilder(ItemTags.SWORDS)
				.add(ModItems.TRIPLE_TOOTHED_SNAKE);
	}
}
