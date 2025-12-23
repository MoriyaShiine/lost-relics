/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.client;

import moriyashiine.lostrelics.client.event.CursedAmuletClientEvent;
import moriyashiine.lostrelics.client.event.TurquoiseEyeClientEvent;
import moriyashiine.lostrelics.client.particle.TreasureSenseParticle;
import moriyashiine.lostrelics.client.render.block.entity.AltarBlockEntityRenderer;
import moriyashiine.lostrelics.client.render.entity.DoppelgangerEntityRenderer;
import moriyashiine.lostrelics.client.render.entity.RelicSkeletonEntityRenderer;
import moriyashiine.lostrelics.client.render.entity.SmokeBallEntityRenderer;
import moriyashiine.lostrelics.client.render.entity.TaintedBloodCrystalEntityRenderer;
import moriyashiine.lostrelics.client.render.entity.model.RelicSkeletonEntityModel;
import moriyashiine.lostrelics.client.render.entity.model.SlimBipedEntityModel;
import moriyashiine.lostrelics.client.supporter.ClientSupporterInit;
import moriyashiine.lostrelics.common.init.ModBlockEntityTypes;
import moriyashiine.lostrelics.common.init.ModEntityTypes;
import moriyashiine.lostrelics.common.init.ModParticleTypes;
import moriyashiine.strawberrylib.api.event.client.OutlineEntityEvent;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.EntityRendererFactories;

public class LostRelicsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientSupporterInit.init();
		initBlocks();
		initEntities();
		initParticles();
		initEvents();
	}

	private void initBlocks() {
		BlockEntityRendererFactories.register(ModBlockEntityTypes.ALTAR, AltarBlockEntityRenderer::new);
	}

	private void initEntities() {
		EntityModelLayerRegistry.registerModelLayer(RelicSkeletonEntityModel.LAYER, RelicSkeletonEntityModel::getTexturedModelData);
		EntityRendererFactories.register(ModEntityTypes.RELIC_SKELETON, RelicSkeletonEntityRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(SlimBipedEntityModel.LAYER, SlimBipedEntityModel::getTexturedModelData);
		EntityRendererFactories.register(ModEntityTypes.DOPPELGANGER, DoppelgangerEntityRenderer::new);
		EntityRendererFactories.register(ModEntityTypes.SMOKE_BALL, SmokeBallEntityRenderer::new);
		EntityRendererFactories.register(ModEntityTypes.TAINTED_BLOOD_CRYSTAL, TaintedBloodCrystalEntityRenderer::new);
	}

	private void initParticles() {
		ParticleFactoryRegistry.getInstance().register(ModParticleTypes.TREASURE_SENSE, TreasureSenseParticle.Factory::new);
	}

	private void initEvents() {
		ClientTickEvents.END_CLIENT_TICK.register(new CursedAmuletClientEvent());

		ClientTickEvents.END_WORLD_TICK.register(new TurquoiseEyeClientEvent.Tick());
		OutlineEntityEvent.EVENT.register(new TurquoiseEyeClientEvent.Outline());
	}
}
