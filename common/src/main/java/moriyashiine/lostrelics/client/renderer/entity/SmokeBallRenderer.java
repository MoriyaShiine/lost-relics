package moriyashiine.lostrelics.client.renderer.entity;

import moriyashiine.lostrelics.common.world.entity.projectile.SmokeBall;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;

public class SmokeBallRenderer extends EntityRenderer<SmokeBall, EntityRenderState> {
	public SmokeBallRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public EntityRenderState createRenderState() {
		return new EntityRenderState();
	}
}
