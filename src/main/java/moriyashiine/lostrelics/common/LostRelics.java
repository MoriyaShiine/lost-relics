/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common;

import moriyashiine.lostrelics.common.event.CursedAmuletEvent;
import moriyashiine.lostrelics.common.init.*;
import moriyashiine.strawberrylib.api.SLib;
import moriyashiine.strawberrylib.api.event.ModifyDamageTakenEvent;
import moriyashiine.strawberrylib.api.event.PreventHostileTargetingEvent;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class LostRelics implements ModInitializer {
	public static final String MOD_ID = "lostrelics";

	@Override
	public void onInitialize() {
		SLib.init(MOD_ID);
		ModBlocks.init();
		ModBlockEntityTypes.init();
		ModComponentTypes.init();
		ModItems.init();
		ModSoundEvents.init();
		initEvents();
	}

	public static Identifier id(String value) {
		return Identifier.of(MOD_ID, value);
	}

	private void initEvents() {
		ModifyDamageTakenEvent.MULTIPLY_TOTAL.register(new CursedAmuletEvent.FireWeakness());
		PreventHostileTargetingEvent.EVENT.register(new CursedAmuletEvent.UndeadNeutrality());
	}
}