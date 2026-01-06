/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common;

import com.swacky.ohmega.api.event.AccessoryCanEquipEvent;
import moriyashiine.lostrelics.common.event.EquippableRelicEvent;
import moriyashiine.lostrelics.common.event.relic.CursedAmuletEvent;
import moriyashiine.lostrelics.common.event.relic.SmokingMirrorEvent;
import moriyashiine.lostrelics.common.event.relic.TripleToothedSnakeEvent;
import moriyashiine.lostrelics.common.event.relic.TurquoiseEyeEvent;
import moriyashiine.lostrelics.common.init.*;
import moriyashiine.lostrelics.common.supporter.SupporterInit;
import moriyashiine.strawberrylib.api.SLib;
import moriyashiine.strawberrylib.api.event.ModifyCriticalStatusEvent;
import moriyashiine.strawberrylib.api.event.ModifyDamageTakenEvent;
import moriyashiine.strawberrylib.api.event.PreventHostileTargetingEvent;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.effect.ServerMobEffectEvents;
import net.fabricmc.fabric.api.item.v1.EnchantmentEvents;
import net.minecraft.util.Identifier;

public class LostRelics implements ModInitializer {
	public static final String MOD_ID = "lostrelics";

	@Override
	public void onInitialize() {
		SLib.init(MOD_ID);
		SupporterInit.init();
		initRegistries();
		initEvents();
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
		ModParticleTypes.init();
		ModRecipeSerializers.init();
		ModSoundEvents.init();
	}

	private void initEvents() {
		AccessoryCanEquipEvent.EVENT.register(new EquippableRelicEvent());

		ServerMobEffectEvents.ALLOW_ADD.register(new CursedAmuletEvent.EffectImmunity());
		ModifyDamageTakenEvent.MULTIPLY_TOTAL.register(new CursedAmuletEvent.FireWeakness());
		PreventHostileTargetingEvent.EVENT.register(new CursedAmuletEvent.UndeadNeutrality());

		ModifyDamageTakenEvent.MULTIPLY_BASE.register(new SmokingMirrorEvent());

		EnchantmentEvents.ALLOW_ENCHANTING.register(new TripleToothedSnakeEvent());

		ModifyCriticalStatusEvent.EVENT.register(new TurquoiseEyeEvent.Attack());
		ServerMobEffectEvents.ALLOW_ADD.register(new TurquoiseEyeEvent.EffectImmunity());
	}
}