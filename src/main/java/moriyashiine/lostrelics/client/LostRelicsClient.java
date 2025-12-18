/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.client;

import moriyashiine.lostrelics.client.event.TurquoiseEyeClientEvent;
import moriyashiine.lostrelics.client.render.block.entity.AltarBlockEntityRenderer;
import moriyashiine.lostrelics.common.init.ModBlockEntityTypes;
import moriyashiine.strawberrylib.api.event.client.OutlineEntityEvent;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;

public class LostRelicsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockEntityRendererFactories.register(ModBlockEntityTypes.ALTAR, AltarBlockEntityRenderer::new);
		initEvents();
	}

	private void initEvents() {
		ClientTickEvents.END_WORLD_TICK.register(new TurquoiseEyeClientEvent.Tick());
		OutlineEntityEvent.EVENT.register(new TurquoiseEyeClientEvent.Outline());
	}
}
