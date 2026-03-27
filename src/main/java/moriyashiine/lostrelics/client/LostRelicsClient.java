/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.client;

import moriyashiine.lostrelics.client.event.CursedAmuletClientEvent;
import moriyashiine.lostrelics.client.event.TurquoiseEyeClientEvent;
import moriyashiine.lostrelics.client.particle.TreasureSenseParticle;
import moriyashiine.lostrelics.client.renderer.blockentity.AltarRenderer;
import moriyashiine.lostrelics.client.renderer.entity.DoppelgangerRenderer;
import moriyashiine.lostrelics.client.renderer.entity.SmokeBallRenderer;
import moriyashiine.lostrelics.client.renderer.entity.TaintedBloodCrystalRenderer;
import moriyashiine.lostrelics.client.renderer.entity.model.RelicSkeletonModel;
import moriyashiine.lostrelics.client.renderer.entity.model.SlimHumanoidModel;
import moriyashiine.lostrelics.client.supporter.ClientSupporterInit;
import moriyashiine.lostrelics.common.init.ModBlockEntityTypes;
import moriyashiine.lostrelics.common.init.ModEntityTypes;
import moriyashiine.lostrelics.common.init.ModParticleTypes;
import moriyashiine.strawberrylib.api.event.client.OutlineEntityEvent;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;

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
		BlockEntityRenderers.register(ModBlockEntityTypes.ALTAR, AltarRenderer::new);
	}

	private void initEntities() {
		ModelLayerRegistry.registerModelLayer(RelicSkeletonModel.LAYER, RelicSkeletonModel::createBodyLayer);
		ModelLayerRegistry.registerModelLayer(SlimHumanoidModel.LAYER, SlimHumanoidModel::createBodyLayer);
		EntityRenderers.register(ModEntityTypes.DOPPELGANGER, DoppelgangerRenderer::new);
		EntityRenderers.register(ModEntityTypes.SMOKE_BALL, SmokeBallRenderer::new);
		EntityRenderers.register(ModEntityTypes.TAINTED_BLOOD_CRYSTAL, TaintedBloodCrystalRenderer::new);
	}

	private void initParticles() {
		ParticleProviderRegistry.getInstance().register(ModParticleTypes.TREASURE_SENSE, TreasureSenseParticle.Provider::new);
	}

	private void initEvents() {
		ClientTickEvents.END_CLIENT_TICK.register(new CursedAmuletClientEvent());

		ClientTickEvents.END_LEVEL_TICK.register(new TurquoiseEyeClientEvent.Tick());
		OutlineEntityEvent.EVENT.register(new TurquoiseEyeClientEvent.Outline());
	}
}
