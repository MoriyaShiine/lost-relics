/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import moriyashiine.lostrelics.client.renderer.entity.model.SlimHumanoidModel;
import moriyashiine.lostrelics.client.renderer.entity.state.DoppelgangerRenderState;
import moriyashiine.lostrelics.common.world.entity.monster.Doppelganger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.player.PlayerModelType;
import org.jspecify.annotations.Nullable;

public class DoppelgangerRenderer extends HumanoidMobRenderer<Doppelganger, DoppelgangerRenderState, HumanoidModel<DoppelgangerRenderState>> {
	private final HumanoidModel<DoppelgangerRenderState> slimModel, wideModel;

	public DoppelgangerRenderer(EntityRendererProvider.Context context) {
		super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.5F);
		slimModel = new SlimHumanoidModel<>(context.bakeLayer(SlimHumanoidModel.LAYER));
		wideModel = model;
		addLayer(new HumanoidArmorLayer<>(this, ArmorModelSet.bake(ModelLayers.PLAYER_ARMOR, context.getModelSet(), HumanoidModel::new), context.getEquipmentRenderer()));
	}

	@Override
	public Identifier getTextureLocation(DoppelgangerRenderState state) {
		return state.texture;
	}

	@Override
	public DoppelgangerRenderState createRenderState() {
		return new DoppelgangerRenderState();
	}

	@Override
	public void extractRenderState(Doppelganger entity, DoppelgangerRenderState state, float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
		state.texture = DefaultPlayerSkin.getDefaultTexture();
		state.model = slimModel;
		if (entity.getCopiedEntity() instanceof Avatar avatar && Minecraft.getInstance().getEntityRenderDispatcher().extractEntity(avatar, partialTicks) instanceof AvatarRenderState copiedState) {
			state.texture = copiedState.skin.body().texturePath();
			if (copiedState.skin.model() == PlayerModelType.WIDE) {
				state.model = wideModel;
			}
		}
	}

	@Override
	protected @Nullable RenderType getRenderType(DoppelgangerRenderState state, boolean isBodyVisible, boolean forceTransparent, boolean appearGlowing) {
		return RenderTypes.entityTranslucent(getTextureLocation(state));
	}

	@Override
	protected int getModelTint(DoppelgangerRenderState state) {
		return ARGB.colorFromFloat(0.5F, 0.75F, 0.75F, 0.75F);
	}

	@Override
	protected void scale(DoppelgangerRenderState state, PoseStack poseStack) {
		poseStack.scale(0.9375F, 0.9375F, 0.9375F);
	}
}
