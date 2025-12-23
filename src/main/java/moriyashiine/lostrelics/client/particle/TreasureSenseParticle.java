/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.client.particle;

import net.minecraft.client.particle.BillboardParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.random.Random;

public class TreasureSenseParticle extends BillboardParticle {
	private final SpriteProvider spriteProvider;

	public TreasureSenseParticle(ClientWorld world, double x, double y, double z, SpriteProvider spriteProvider, int color) {
		super(world, x, y, z, 0, 0, 0, spriteProvider.getFirst());
		velocityX = velocityY = velocityZ = velocityMultiplier = 0;
		scale = 0.1F;
		this.spriteProvider = spriteProvider;
		red = ColorHelper.getRedFloat(color);
		green = ColorHelper.getGreenFloat(color);
		blue = ColorHelper.getBlueFloat(color);
		alpha = 0.75F;
		maxAge = 3;
	}

	@Override
	protected RenderType getRenderType() {
		return RenderType.PARTICLE_ATLAS_TRANSLUCENT;
	}

	@Override
	public void tick() {
		super.tick();
		setSprite(spriteProvider.getSprite(age, maxAge));
	}

	@Override
	protected int getBrightness(float tint) {
		return 240;
	}

	public record Factory(SpriteProvider spriteProvider) implements ParticleFactory<SimpleParticleType> {
		@Override
		public Particle createParticle(SimpleParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Random random) {
			return new TreasureSenseParticle(world, x, y, z, spriteProvider(), (int) velocityX);
		}
	}
}
