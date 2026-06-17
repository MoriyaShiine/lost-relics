/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.datagen;

import moriyashiine.lostrelics.datagen.provider.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class LostRelicsDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(LostRelicsAdvancementProvider::new);
		pack.addProvider(LostRelicsBlockLootSubProvider::new);
		pack.addProvider(LostRelicsBlockTagsProvider::new);
		pack.addProvider(LostRelicsEntityTypeTagsProvider::new);
		pack.addProvider(LostRelicsItemTagsProvider::new);
		pack.addProvider(LostRelicsMobEffectTagsProvider::new);
		pack.addProvider(LostRelicsModelProvider::new);
		pack.addProvider(LostRelicsRecipeProvider::new);
		pack.addProvider(LostRelicsSoundsProvider::new);
	}
}
