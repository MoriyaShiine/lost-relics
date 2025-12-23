/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.init;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerParticleType;

public class ModParticleTypes {
	public static final SimpleParticleType TREASURE_SENSE = registerParticleType("treasure_sense", FabricParticleTypes.simple(true));

	public static void init() {
	}
}
