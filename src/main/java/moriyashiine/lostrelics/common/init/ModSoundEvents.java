/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.init;

import net.minecraft.sound.SoundEvent;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerSoundEvent;

public class ModSoundEvents {
	public static final SoundEvent BLOCK_ALTAR_CONVERT = registerSoundEvent("block.altar.convert");

	public static final SoundEvent ENTITY_TAINTED_BLOOD_CRYSTAL_SHATTER = registerSoundEvent("entity.tainted_blood_crystal.shatter");

	public static void init() {
	}
}
