/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class LostRelics implements ModInitializer {
	public static final String MOD_ID = "lostrelics";

	@Override
	public void onInitialize() {
	}

	public static Identifier id(String value) {
		return Identifier.of(MOD_ID, value);
	}
}