/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common;

import moriyashiine.lostrelics.common.init.ModBlocks;
import moriyashiine.lostrelics.common.init.ModItems;
import moriyashiine.strawberrylib.api.SLib;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class LostRelics implements ModInitializer {
	public static final String MOD_ID = "lostrelics";

	@Override
	public void onInitialize() {
		SLib.init(MOD_ID);
		ModBlocks.init();
		ModItems.init();
	}

	public static Identifier id(String value) {
		return Identifier.of(MOD_ID, value);
	}
}