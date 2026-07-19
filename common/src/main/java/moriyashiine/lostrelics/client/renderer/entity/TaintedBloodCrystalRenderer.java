package moriyashiine.lostrelics.client.renderer.entity;

import moriyashiine.lostrelics.common.LostRelics;
import moriyashiine.lostrelics.common.world.entity.projectile.arrow.TaintedBloodCrystal;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.resources.Identifier;

public class TaintedBloodCrystalRenderer extends ArrowRenderer<TaintedBloodCrystal, ArrowRenderState> {
	private static final Identifier TEXTURE = LostRelics.id("textures/entity/projectiles/tainted_blood_crystal.png");

	public TaintedBloodCrystalRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public ArrowRenderState createRenderState() {
		return new ArrowRenderState();
	}

	@Override
	protected Identifier getTextureLocation(ArrowRenderState state) {
		return TEXTURE;
	}
}
