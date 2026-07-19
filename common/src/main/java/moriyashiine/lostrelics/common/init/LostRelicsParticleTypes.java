package moriyashiine.lostrelics.common.init;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerParticleType;

public class LostRelicsParticleTypes {
	public static final SimpleParticleType TREASURE_SENSE = registerParticleType("treasure_sense", FabricParticleTypes.simple(true));

	public static void init() {
	}
}
