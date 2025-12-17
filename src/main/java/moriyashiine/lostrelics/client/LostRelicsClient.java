/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.client;

import moriyashiine.lostrelics.client.render.block.entity.AltarBlockEntityRenderer;
import moriyashiine.lostrelics.common.init.ModBlockEntityTypes;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class LostRelicsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockEntityRendererFactories.register(ModBlockEntityTypes.ALTAR, AltarBlockEntityRenderer::new);
	}
}
