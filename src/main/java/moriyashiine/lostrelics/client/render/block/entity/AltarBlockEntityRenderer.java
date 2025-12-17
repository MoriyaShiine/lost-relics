/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.client.render.block.entity;

import it.unimi.dsi.fastutil.HashCommon;
import moriyashiine.lostrelics.client.render.block.entity.state.AltarBlockEntityRenderState;
import moriyashiine.lostrelics.common.block.entity.AltarBlockEntity;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.jspecify.annotations.Nullable;

public record AltarBlockEntityRenderer(
		ItemModelManager itemModelManager) implements BlockEntityRenderer<AltarBlockEntity, AltarBlockEntityRenderState> {
	public AltarBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
		this(ctx.itemModelManager());
	}

	@Override
	public AltarBlockEntityRenderState createRenderState() {
		return new AltarBlockEntityRenderState();
	}

	@Override
	public void render(AltarBlockEntityRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
		if (!state.stackState.isEmpty()) {
			matrices.push();
			matrices.translate(0.5, 1.25, 0.5);
			matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(state.rotation));
			state.stackState.render(matrices, queue, state.lightmapCoordinates, OverlayTexture.DEFAULT_UV, 0);
			matrices.pop();
		}
	}

	@Override
	public void updateRenderState(AltarBlockEntity blockEntity, AltarBlockEntityRenderState state, float tickProgress, Vec3d cameraPos, ModelCommandRenderer.@Nullable CrumblingOverlayCommand crumblingOverlay) {
		BlockEntityRenderer.super.updateRenderState(blockEntity, state, tickProgress, cameraPos, crumblingOverlay);
		itemModelManager.clearAndUpdate(state.stackState, blockEntity.getStack(), ItemDisplayContext.GROUND, blockEntity.getWorld(), null, HashCommon.long2int(blockEntity.getPos().asLong()));
		state.rotation = (int) blockEntity.getPos().asLong() + blockEntity.getWorld().getTime() * 4 + tickProgress;
	}
}
