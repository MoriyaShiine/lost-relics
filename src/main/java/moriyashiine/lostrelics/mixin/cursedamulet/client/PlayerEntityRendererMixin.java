/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.mixin.cursedamulet.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.lostrelics.client.event.CursedAmuletClientEvent;
import moriyashiine.lostrelics.client.render.entity.model.RelicSkeletonEntityModel;
import moriyashiine.lostrelics.client.render.entity.state.RelicSkeletonEntityRenderState;
import moriyashiine.lostrelics.common.init.ModItems;
import moriyashiine.lostrelics.common.util.LostRelicsUtil;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerLikeEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.PlayerLikeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin<AvatarlikeEntity extends PlayerLikeEntity & ClientPlayerLikeEntity> extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityRenderState, PlayerEntityModel> {
	@Unique
	private PlayerEntityModel originalModel, relicSkeletonModel;

	public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel model, float shadowRadius) {
		super(ctx, model, shadowRadius);
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void lostrelics$cursedAmulet(EntityRendererFactory.Context ctx, boolean slim, CallbackInfo ci) {
		originalModel = model;
		relicSkeletonModel = new RelicSkeletonEntityModel(ctx.getPart(RelicSkeletonEntityModel.LAYER));
	}

	@Inject(method = "renderArm", at = @At("HEAD"), cancellable = true)
	private void lostrelics$cursedAmulet(MatrixStack matrices, OrderedRenderCommandQueue queue, int light, Identifier skinTexture, ModelPart arm, boolean sleeveVisible, CallbackInfo ci) {
		if (CursedAmuletClientEvent.relicSkeletonTexture != null) {
			boolean left = arm == model.leftArm;
			ModelPart relicSkeletonArm = left ? relicSkeletonModel.leftArm : relicSkeletonModel.rightArm;
			relicSkeletonArm.resetTransform();
			relicSkeletonArm.visible = true;
			relicSkeletonArm.originY += 1;
			relicSkeletonArm.roll = 0.1F * (left ? -1 : 1);
			queue.submitModelPart(relicSkeletonArm, matrices, RenderLayers.entityTranslucent(CursedAmuletClientEvent.relicSkeletonTexture), light, OverlayTexture.DEFAULT_UV, null);
			ci.cancel();
		}
	}

	@ModifyReturnValue(method = "getTexture(Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;)Lnet/minecraft/util/Identifier;", at = @At("RETURN"))
	private Identifier lostrelics$cursedAmulet(Identifier original, PlayerEntityRenderState state) {
		@Nullable RelicSkeletonEntityRenderState relicSkeletonEntityRenderState = state.getData(RelicSkeletonEntityRenderState.KEY);
		if (relicSkeletonEntityRenderState != null && relicSkeletonEntityRenderState.enabled) {
			return relicSkeletonEntityRenderState.gemType.getTexture();
		}
		return original;
	}

	@Inject(method = "setupTransforms(Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;FF)V", at = @At("TAIL"))
	private void lostrelics$cursedAmulet(PlayerEntityRenderState state, MatrixStack matrices, float bodyYaw, float baseHeight, CallbackInfo ci) {
		@Nullable RelicSkeletonEntityRenderState relicSkeletonEntityRenderState = state.getData(RelicSkeletonEntityRenderState.KEY);
		if (relicSkeletonEntityRenderState != null && relicSkeletonEntityRenderState.enabled) {
			model = relicSkeletonModel;
		} else {
			model = originalModel;
		}
	}

	@Inject(method = "updateRenderState(Lnet/minecraft/entity/PlayerLikeEntity;Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;F)V", at = @At("TAIL"))
	private void lostrelics$cursedAmulet(AvatarlikeEntity entity, PlayerEntityRenderState state, float tickProgress, CallbackInfo ci) {
		if (entity instanceof PlayerEntity player) {
			RelicSkeletonEntityRenderState relicSkeletonEntityRenderState = new RelicSkeletonEntityRenderState();
			relicSkeletonEntityRenderState.enabled = LostRelicsUtil.hasRelic(player, ModItems.CURSED_AMULET);
			relicSkeletonEntityRenderState.gemType = RelicSkeletonEntityRenderState.getGemType(player);
			state.setData(RelicSkeletonEntityRenderState.KEY, relicSkeletonEntityRenderState);
		}
	}
}
