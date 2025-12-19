/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.client;

import moriyashiine.lostrelics.client.event.CursedAmuletClientEvent;
import moriyashiine.lostrelics.client.event.TurquoiseEyeClientEvent;
import moriyashiine.lostrelics.client.render.block.entity.AltarBlockEntityRenderer;
import moriyashiine.lostrelics.client.render.entity.RelicSkeletonEntityRenderer;
import moriyashiine.lostrelics.client.render.entity.TaintedBloodCrystalEntityRenderer;
import moriyashiine.lostrelics.client.render.entity.model.RelicSkeletonEntityModel;
import moriyashiine.lostrelics.common.init.ModBlockEntityTypes;
import moriyashiine.lostrelics.common.init.ModEntityTypes;
import moriyashiine.strawberrylib.api.event.client.OutlineEntityEvent;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.EntityRendererFactories;

public class LostRelicsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockEntityRendererFactories.register(ModBlockEntityTypes.ALTAR, AltarBlockEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(RelicSkeletonEntityModel.LAYER, RelicSkeletonEntityModel::getTexturedModelData);
		EntityRendererFactories.register(ModEntityTypes.RELIC_SKELETON, RelicSkeletonEntityRenderer::new);
		EntityRendererFactories.register(ModEntityTypes.TAINTED_BLOOD_CRYSTAL, TaintedBloodCrystalEntityRenderer::new);
		initEvents();
	}

	private void initEvents() {
		ClientTickEvents.END_CLIENT_TICK.register(new CursedAmuletClientEvent());

		ClientTickEvents.END_WORLD_TICK.register(new TurquoiseEyeClientEvent.Tick());
		OutlineEntityEvent.EVENT.register(new TurquoiseEyeClientEvent.Outline());
	}
}
