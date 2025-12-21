/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.client.render.entity;

import moriyashiine.lostrelics.client.render.entity.model.SlimBipedEntityModel;
import moriyashiine.lostrelics.client.render.entity.state.DoppelgangerEntityRenderState;
import moriyashiine.lostrelics.common.entity.mob.DoppelgangerEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EquipmentModelData;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerSkinType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import org.jspecify.annotations.Nullable;

public class DoppelgangerEntityRenderer extends BipedEntityRenderer<DoppelgangerEntity, DoppelgangerEntityRenderState, BipedEntityModel<DoppelgangerEntityRenderState>> {
	private final BipedEntityModel<DoppelgangerEntityRenderState> slimModel, wideModel;

	public DoppelgangerEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new BipedEntityModel<>(context.getPart(EntityModelLayers.PLAYER)), 0.5F);
		slimModel = new SlimBipedEntityModel<>(context.getPart(SlimBipedEntityModel.LAYER));
		wideModel = model;
		addFeature(new ArmorFeatureRenderer<>(this, EquipmentModelData.mapToEntityModel(EntityModelLayers.PLAYER_EQUIPMENT, context.getEntityModels(), BipedEntityModel::new), context.getEquipmentRenderer()));
	}

	@Override
	public Identifier getTexture(DoppelgangerEntityRenderState state) {
		return state.texture;
	}

	@Override
	public DoppelgangerEntityRenderState createRenderState() {
		return new DoppelgangerEntityRenderState();
	}

	@Override
	public void updateRenderState(DoppelgangerEntity entity, DoppelgangerEntityRenderState state, float tickProgress) {
		super.updateRenderState(entity, state, tickProgress);
		state.texture = DefaultSkinHelper.getTexture();
		state.model = slimModel;
		if (entity.getCopiedEntity() instanceof PlayerEntity copiedPlayer && MinecraftClient.getInstance().getEntityRenderDispatcher().getAndUpdateRenderState(copiedPlayer, tickProgress) instanceof PlayerEntityRenderState copiedState) {
			state.texture = copiedState.skinTextures.body().texturePath();
			if (copiedState.skinTextures.model() == PlayerSkinType.WIDE) {
				state.model = wideModel;
			}
		}
	}

	@Override
	protected @Nullable RenderLayer getRenderLayer(DoppelgangerEntityRenderState state, boolean showBody, boolean translucent, boolean showOutline) {
		return RenderLayers.entityTranslucent(getTexture(state));
	}

	@Override
	protected int getMixColor(DoppelgangerEntityRenderState state) {
		return ColorHelper.fromFloats(0.5F, 0.75F, 0.75F, 0.75F);
	}

	@Override
	protected void scale(DoppelgangerEntityRenderState state, MatrixStack matrices) {
		matrices.scale(0.9375F, 0.9375F, 0.9375F);
	}
}
