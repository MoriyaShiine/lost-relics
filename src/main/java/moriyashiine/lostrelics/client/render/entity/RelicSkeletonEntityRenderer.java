/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.client.render.entity;

import moriyashiine.lostrelics.client.render.entity.model.RelicSkeletonEntityModel;
import moriyashiine.lostrelics.client.render.entity.state.RelicSkeletonEntityRenderState;
import moriyashiine.lostrelics.client.supporter.GemType;
import moriyashiine.lostrelics.common.entity.mob.RelicSkeletonEntity;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EquipmentModelData;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class RelicSkeletonEntityRenderer extends BipedEntityRenderer<RelicSkeletonEntity, RelicSkeletonEntityRenderState, RelicSkeletonEntityModel> {
	public RelicSkeletonEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new RelicSkeletonEntityModel(context.getPart(RelicSkeletonEntityModel.LAYER)), 0.5F);
		addFeature(new ArmorFeatureRenderer<>(this, EquipmentModelData.mapToEntityModel(EntityModelLayers.SKELETON_EQUIPMENT, context.getEntityModels(), RelicSkeletonEntityModel::new), context.getEquipmentRenderer()));
	}

	@Override
	public Identifier getTexture(RelicSkeletonEntityRenderState state) {
		return GemType.values()[state.textureIndex].getTexture();
	}

	@Override
	public RelicSkeletonEntityRenderState createRenderState() {
		return new RelicSkeletonEntityRenderState();
	}

	@Override
	public void updateRenderState(RelicSkeletonEntity entity, RelicSkeletonEntityRenderState state, float tickProgress) {
		super.updateRenderState(entity, state, tickProgress);
		state.textureIndex = getTextureIndex(entity);
	}

	@Override
	protected void scale(RelicSkeletonEntityRenderState state, MatrixStack matrices) {
		matrices.scale(0.9375F, 0.9375F, 0.9375F);
	}

	public static int getTextureIndex(RelicSkeletonEntity entity) {
		if (entity.getGemType() != GemType.DEFAULT) {
			return entity.getGemType().ordinal();
		}
		int index = entity.getPlayerUuid().hashCode() % (GemType.values().length - 1);
		if (index < 0) {
			index += (GemType.values().length - 1);
		}
		return index + 1;
	}
}
