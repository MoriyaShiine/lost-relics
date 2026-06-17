/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.common;

import moriyashiine.lostrelics.common.event.CursedAmuletEvent;
import moriyashiine.lostrelics.common.event.SmokingMirrorEvent;
import moriyashiine.lostrelics.common.event.TripleToothedSnakeEvent;
import moriyashiine.lostrelics.common.event.TurquoiseEyeEvent;
import moriyashiine.lostrelics.common.init.*;
import moriyashiine.lostrelics.common.supporter.SupporterInit;
import moriyashiine.strawberrylib.api.SLib;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.Identifier;

public class LostRelics implements ModInitializer {
	public static final String MOD_ID = "lost-relics";

	public static boolean nyctoLoaded = false;

	@Override
	public void onInitialize() {
		SLib.init(MOD_ID);
		SupporterInit.init();
		initRegistries();
		initEvents();
		nyctoLoaded = FabricLoader.getInstance().isModLoaded("nycto");
	}

	public static Identifier id(String value) {
		return Identifier.fromNamespaceAndPath(MOD_ID, value);
	}

	private void initRegistries() {
		LostRelicsBlocks.init();
		LostRelicsBlockEntityTypes.init();
		LostRelicsDataComponents.init();
		LostRelicsEntityTypes.init();
		LostRelicsItems.init();
		LostRelicsParticleTypes.init();
		LostRelicsRecipeSerializers.init();
		LostRelicsSoundEvents.init();
	}

	private void initEvents() {
		CursedAmuletEvent.init();
		SmokingMirrorEvent.init();
		TripleToothedSnakeEvent.init();
		TurquoiseEyeEvent.init();
	}
}