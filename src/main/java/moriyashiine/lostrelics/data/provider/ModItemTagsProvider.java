/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.data.provider;

import com.swacky.ohmega.common.accessorytype.AccessoryType;
import com.swacky.ohmega.common.init.OhmegaTags;
import moriyashiine.lostrelics.common.init.ModItems;
import moriyashiine.lostrelics.common.tag.ModItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends FabricTagsProvider.ItemTagsProvider {
	public ModItemTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
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

		valueLookupBuilder(OhmegaTags.get(AccessoryType.NORMAL_ID))
				.add(ModItems.CURSED_AMULET)
				.add(ModItems.SMOKING_MIRROR)
				.add(ModItems.TURQUOISE_EYE);
	}
}
