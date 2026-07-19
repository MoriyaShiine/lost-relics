package moriyashiine.lostrelics.datagen.provider;

import moriyashiine.lostrelics.common.references.LostRelicsEntityTypeIds;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagKey;

import java.util.concurrent.CompletableFuture;

public class LostRelicsEntityTypeTagsProvider extends FabricTagsProvider.EntityTypeTagsProvider {
	public LostRelicsEntityTypeTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		builder(EntityTypeTags.ARROWS)
				.add(LostRelicsEntityTypeIds.TAINTED_BLOOD_CRYSTAL);

		builder(TagKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath("enchancement", "cannot_disarm")))
				.add(LostRelicsEntityTypeIds.DOPPELGANGER);
	}
}
