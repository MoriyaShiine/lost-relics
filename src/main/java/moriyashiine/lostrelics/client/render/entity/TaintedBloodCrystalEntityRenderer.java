/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.client.render.entity;

import moriyashiine.lostrelics.common.LostRelics;
import moriyashiine.lostrelics.common.entity.projectile.TaintedBloodCrystalEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.client.render.entity.state.ProjectileEntityRenderState;
import net.minecraft.util.Identifier;

public class TaintedBloodCrystalEntityRenderer extends ProjectileEntityRenderer<TaintedBloodCrystalEntity, ProjectileEntityRenderState> {
	private static final Identifier TEXTURE = LostRelics.id("textures/entity/projectiles/tainted_blood_crystal.png");

	public TaintedBloodCrystalEntityRenderer(EntityRendererFactory.Context context) {
		super(context);
	}

	@Override
	public ProjectileEntityRenderState createRenderState() {
		return new ProjectileEntityRenderState();
	}

	@Override
	protected Identifier getTexture(ProjectileEntityRenderState state) {
		return TEXTURE;
	}
}
