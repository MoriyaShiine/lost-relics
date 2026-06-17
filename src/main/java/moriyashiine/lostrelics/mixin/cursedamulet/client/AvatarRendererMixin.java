/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.mixin.cursedamulet.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.blaze3d.vertex.PoseStack;
import moriyashiine.lostrelics.client.event.CursedAmuletClientEvent;
import moriyashiine.lostrelics.client.renderer.entity.model.RelicSkeletonModel;
import moriyashiine.lostrelics.client.renderer.entity.state.RelicSkeletonRenderState;
import moriyashiine.lostrelics.common.init.LostRelicsItems;
import moriyashiine.lostrelics.common.util.LostRelicsUtil;
import net.minecraft.client.entity.ClientAvatarEntity;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Avatar;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AvatarRenderer.class)
public abstract class AvatarRendererMixin<AvatarlikeEntity extends Avatar & ClientAvatarEntity> extends LivingEntityRenderer<AbstractClientPlayer, AvatarRenderState, PlayerModel> {
	@Unique
	private PlayerModel originalModel, relicSkeletonModel;

	public AvatarRendererMixin(EntityRendererProvider.Context context, PlayerModel model, float shadow) {
		super(context, model, shadow);
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void lostrelics$cursedAmulet(EntityRendererProvider.Context context, boolean slimSteve, CallbackInfo ci) {
		originalModel = model;
		relicSkeletonModel = new RelicSkeletonModel(context.bakeLayer(RelicSkeletonModel.LAYER));
	}

	@Inject(method = "renderHand", at = @At("HEAD"), cancellable = true)
	private void lostrelics$cursedAmulet(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, Identifier skinTexture, ModelPart arm, boolean hasSleeve, CallbackInfo ci) {
		if (CursedAmuletClientEvent.relicSkeletonTexture != null) {
			boolean left = arm == model.leftArm;
			ModelPart relicSkeletonArm = left ? relicSkeletonModel.leftArm : relicSkeletonModel.rightArm;
			relicSkeletonArm.resetPose();
			relicSkeletonArm.visible = true;
			relicSkeletonArm.y += 1;
			relicSkeletonArm.zRot = 0.1F * (left ? -1 : 1);
			submitNodeCollector.submitModelPart(relicSkeletonArm, poseStack, RenderTypes.entityTranslucent(CursedAmuletClientEvent.relicSkeletonTexture), lightCoords, OverlayTexture.NO_OVERLAY, null);
			ci.cancel();
		}
	}

	@ModifyReturnValue(method = "getTextureLocation(Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;)Lnet/minecraft/resources/Identifier;", at = @At("RETURN"))
	private Identifier lostrelics$cursedAmulet(Identifier original, AvatarRenderState state) {
		RelicSkeletonRenderState relicSkeletonRenderState = state.getData(RelicSkeletonRenderState.KEY);
		if (relicSkeletonRenderState != null && relicSkeletonRenderState.enabled) {
			return relicSkeletonRenderState.gemType.getTexture();
		}
		return original;
	}

	@Inject(method = "setupRotations(Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;FF)V", at = @At("TAIL"))
	private void lostrelics$cursedAmulet(AvatarRenderState state, PoseStack poseStack, float bodyRot, float entityScale, CallbackInfo ci) {
		RelicSkeletonRenderState relicSkeletonRenderState = state.getData(RelicSkeletonRenderState.KEY);
		if (relicSkeletonRenderState != null && relicSkeletonRenderState.enabled) {
			model = relicSkeletonModel;
		}
	}

	@Inject(method = "submitNameDisplay(Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V", at = @At("TAIL"))
	private void lostrelics$cursedAmulet(AvatarRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera, CallbackInfo ci) {
		model = originalModel;
	}

	@Inject(method = "extractRenderState(Lnet/minecraft/world/entity/Avatar;Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;F)V", at = @At("TAIL"))
	private void lostrelics$cursedAmulet(AvatarlikeEntity entity, AvatarRenderState state, float partialTicks, CallbackInfo ci) {
		RelicSkeletonRenderState relicSkeletonRenderState = new RelicSkeletonRenderState();
		relicSkeletonRenderState.enabled = LostRelicsUtil.hasRelic(entity, LostRelicsItems.CURSED_AMULET);
		relicSkeletonRenderState.gemType = RelicSkeletonRenderState.getGemType(entity);
		state.setData(RelicSkeletonRenderState.KEY, relicSkeletonRenderState);
	}
}
