package moriyashiine.lostrelics.datagen.provider;

import moriyashiine.lostrelics.common.init.LostRelicsBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class LostRelicsBlockLootSubProvider extends FabricBlockLootSubProvider {
	public LostRelicsBlockLootSubProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	public void generate() {
		dropSelf(LostRelicsBlocks.JUNGLE_ALTAR);
	}
}
