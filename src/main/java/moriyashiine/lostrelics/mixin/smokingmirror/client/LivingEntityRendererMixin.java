/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.mixin.smokingmirror.client;

import moriyashiine.lostrelics.client.render.entity.state.DoppelgangerEntityRenderState;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin<S extends LivingEntityRenderState, M extends EntityModel<? super S>> {
	@Shadow
	protected M model;

	@SuppressWarnings("unchecked")
	@Inject(method = "render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V", at = @At("HEAD"))
	private void lostrelics$smokingMirror(S state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState, CallbackInfo ci) {
		if (state instanceof DoppelgangerEntityRenderState doppelgangerState) {
			model = (M) doppelgangerState.model;
		}
	}
}
