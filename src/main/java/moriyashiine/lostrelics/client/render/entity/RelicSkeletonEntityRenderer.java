/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.client.render.entity;

import moriyashiine.lostrelics.client.render.entity.model.RelicSkeletonEntityModel;
import moriyashiine.lostrelics.client.render.entity.state.RelicSkeletonEntityRenderState;
import moriyashiine.lostrelics.common.LostRelics;
import moriyashiine.lostrelics.common.entity.mob.RelicSkeletonEntity;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EquipmentModelData;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class RelicSkeletonEntityRenderer extends BipedEntityRenderer<RelicSkeletonEntity, RelicSkeletonEntityRenderState, RelicSkeletonEntityModel> {
	private static final Identifier[] RELIC_SKELETON_TEXTURES = new Identifier[6];

	static {
		RELIC_SKELETON_TEXTURES[0] = LostRelics.id("textures/entity/relic_skeleton/amethyst.png");
		RELIC_SKELETON_TEXTURES[1] = LostRelics.id("textures/entity/relic_skeleton/diamond.png");
		RELIC_SKELETON_TEXTURES[2] = LostRelics.id("textures/entity/relic_skeleton/emerald.png");
		RELIC_SKELETON_TEXTURES[3] = LostRelics.id("textures/entity/relic_skeleton/gold.png");
		RELIC_SKELETON_TEXTURES[4] = LostRelics.id("textures/entity/relic_skeleton/jade.png");
		RELIC_SKELETON_TEXTURES[5] = LostRelics.id("textures/entity/relic_skeleton/turquoise.png");
	}

	public RelicSkeletonEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new RelicSkeletonEntityModel(context.getPart(RelicSkeletonEntityModel.LAYER)), 0.5F);
		addFeature(new ArmorFeatureRenderer<>(this, EquipmentModelData.mapToEntityModel(EntityModelLayers.SKELETON_EQUIPMENT, context.getEntityModels(), RelicSkeletonEntityModel::new), context.getEquipmentRenderer()));
	}

	@Override
	public Identifier getTexture(RelicSkeletonEntityRenderState state) {
		return RELIC_SKELETON_TEXTURES[state.textureIndex];
	}

	@Override
	public RelicSkeletonEntityRenderState createRenderState() {
		return new RelicSkeletonEntityRenderState();
	}

	@Override
	public void updateRenderState(RelicSkeletonEntity entity, RelicSkeletonEntityRenderState state, float tickProgress) {
		super.updateRenderState(entity, state, tickProgress);
		int index = entity.getPlayerUuid().hashCode() % RELIC_SKELETON_TEXTURES.length;
		if (index < 0) {
			index += RELIC_SKELETON_TEXTURES.length;
		}
		state.textureIndex = index;
	}

	@Override
	protected void scale(RelicSkeletonEntityRenderState state, MatrixStack matrices) {
		matrices.scale(0.9375F, 0.9375F, 0.9375F);
	}
}
