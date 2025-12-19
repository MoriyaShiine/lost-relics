/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common;

import moriyashiine.lostrelics.common.event.CursedAmuletEvent;
import moriyashiine.lostrelics.common.event.TripleToothedSnakeEvent;
import moriyashiine.lostrelics.common.event.TurquoiseEyeEvent;
import moriyashiine.lostrelics.common.init.*;
import moriyashiine.lostrelics.common.supporter.payload.SyncGemTypePayload;
import moriyashiine.strawberrylib.api.SLib;
import moriyashiine.strawberrylib.api.event.ModifyCriticalStatusEvent;
import moriyashiine.strawberrylib.api.event.ModifyDamageTakenEvent;
import moriyashiine.strawberrylib.api.event.PreventHostileTargetingEvent;
import moriyashiine.strawberrylib.api.event.TickEntityEvent;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.EnchantmentEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class LostRelics implements ModInitializer {
	public static final String MOD_ID = "lostrelics";

	@Override
	public void onInitialize() {
		SLib.init(MOD_ID);
		initRegistries();
		initEvents();
		initPayloads();
	}

	public static Identifier id(String value) {
		return Identifier.of(MOD_ID, value);
	}

	private void initRegistries() {
		ModBlocks.init();
		ModBlockEntityTypes.init();
		ModComponentTypes.init();
		ModEntityTypes.init();
		ModItems.init();
		ModRecipeSerializers.init();
		ModSoundEvents.init();
	}

	private void initEvents() {
		TickEntityEvent.EVENT.register(new CursedAmuletEvent.Tick());
		ModifyDamageTakenEvent.MULTIPLY_TOTAL.register(new CursedAmuletEvent.FireWeakness());
		PreventHostileTargetingEvent.EVENT.register(new CursedAmuletEvent.UndeadNeutrality());

		EnchantmentEvents.ALLOW_ENCHANTING.register(new TripleToothedSnakeEvent());

		ModifyCriticalStatusEvent.EVENT.register(new TurquoiseEyeEvent());
	}

	private void initPayloads() {
		// server payloads
		PayloadTypeRegistry.playC2S().register(SyncGemTypePayload.ID, SyncGemTypePayload.CODEC);
		// server receivers
		ServerPlayNetworking.registerGlobalReceiver(SyncGemTypePayload.ID, new SyncGemTypePayload.Receiver());
	}
}