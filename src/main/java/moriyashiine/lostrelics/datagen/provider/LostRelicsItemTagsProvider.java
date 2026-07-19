/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.datagen.provider;

import com.swacky.ohmega.api.common.accessorytype.AccessoryType;
import com.swacky.ohmega.common.init.OhmegaTags;
import moriyashiine.lostrelics.common.references.LostRelicsItemIds;
import moriyashiine.lostrelics.common.tag.ModItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;

import java.util.concurrent.CompletableFuture;

public class LostRelicsItemTagsProvider extends FabricTagsProvider.ItemTagsProvider {
	public LostRelicsItemTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		builder(ModItemTags.RELICS)
				.addTag(ModItemTags.JUNGLE_RELICS);
		builder(ModItemTags.JUNGLE_RELICS)
				.add(LostRelicsItemIds.CURSED_AMULET)
				.add(LostRelicsItemIds.SMOKING_MIRROR)
				.add(LostRelicsItemIds.TRIPLE_TOOTHED_SNAKE)
				.add(LostRelicsItemIds.TURQUOISE_EYE);

		builder(ItemTags.ARROWS)
				.add(LostRelicsItemIds.TAINTED_BLOOD_CRYSTAL);
		builder(ItemTags.SWORDS)
				.add(LostRelicsItemIds.TRIPLE_TOOTHED_SNAKE);

		builder(OhmegaTags.get(AccessoryType.NORMAL_ID))
				.add(LostRelicsItemIds.CURSED_AMULET)
				.add(LostRelicsItemIds.SMOKING_MIRROR)
				.add(LostRelicsItemIds.TURQUOISE_EYE);

		builder(TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("nycto", "hurts_vampires")))
				.add(LostRelicsItemIds.CURSED_AMULET);
	}
}
