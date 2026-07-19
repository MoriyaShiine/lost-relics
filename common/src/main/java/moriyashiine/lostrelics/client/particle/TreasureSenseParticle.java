package moriyashiine.lostrelics.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.ARGB;
import net.minecraft.util.RandomSource;

public class TreasureSenseParticle extends SingleQuadParticle {
	private final SpriteSet sprites;

	public TreasureSenseParticle(ClientLevel level, double x, double y, double z, SpriteSet sprites, int color) {
		super(level, x, y, z, 0, 0, 0, sprites.first());
		xd = yd = zd = friction = 0;
		quadSize = 0.1F;
		this.sprites = sprites;
		rCol = ARGB.redFloat(color);
		gCol = ARGB.greenFloat(color);
		bCol = ARGB.blueFloat(color);
		alpha = 0.75F;
		lifetime = 3;
	}

	@Override
	protected Layer getLayer() {
		return Layer.TRANSLUCENT;
	}

	@Override
	public void tick() {
		super.tick();
		setSprite(sprites.get(age, lifetime));
	}

	@Override
	protected int getLightCoords(float a) {
		return 240;
	}

	public record Provider(SpriteSet sprites) implements ParticleProvider<SimpleParticleType> {
		@Override
		public Particle createParticle(SimpleParticleType options, ClientLevel level, double x, double y, double z, double xAux, double yAux, double zAux, RandomSource random) {
			return new TreasureSenseParticle(level, x, y, z, sprites(), (int) xAux);
		}
	}
}
