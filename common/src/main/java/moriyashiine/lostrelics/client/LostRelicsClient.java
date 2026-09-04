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
import moriyashiine.lostrelics.client.supporter.LostRelicsClientSupporterInit;
import moriyashiine.lostrelics.common.init.LostRelicsBlockEntityTypes;
import moriyashiine.lostrelics.common.init.LostRelicsEntityTypes;
import moriyashiine.lostrelics.common.init.LostRelicsParticleTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;

public class LostRelicsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		LostRelicsClientSupporterInit.init();
		initBlocks();
		initEntities();
		initParticles();
		initEvents();
	}

	private void initBlocks() {
		BlockEntityRenderers.register(LostRelicsBlockEntityTypes.ALTAR, AltarRenderer::new);
	}

	private void initEntities() {
		ModelLayerRegistry.registerModelLayer(RelicSkeletonModel.LAYER, RelicSkeletonModel::createBodyLayer);
		ModelLayerRegistry.registerModelLayer(SlimHumanoidModel.LAYER, SlimHumanoidModel::createBodyLayer);
		EntityRenderers.register(LostRelicsEntityTypes.DOPPELGANGER, DoppelgangerRenderer::new);
		EntityRenderers.register(LostRelicsEntityTypes.SMOKE_BALL, SmokeBallRenderer::new);
		EntityRenderers.register(LostRelicsEntityTypes.TAINTED_BLOOD_CRYSTAL, TaintedBloodCrystalRenderer::new);
	}

	private void initParticles() {
		ParticleProviderRegistry.getInstance().register(LostRelicsParticleTypes.TREASURE_SENSE, TreasureSenseParticle.Provider::new);
	}

	private void initEvents() {
		CursedAmuletClientEvent.init();
		TurquoiseEyeClientEvent.init();
	}
}
