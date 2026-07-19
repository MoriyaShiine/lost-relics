package moriyashiine.lostrelics.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import it.unimi.dsi.fastutil.HashCommon;
import moriyashiine.lostrelics.client.renderer.blockentity.state.AltarRenderState;
import moriyashiine.lostrelics.common.world.level.block.entity.AltarBlockEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public record AltarRenderer(
		ItemModelResolver itemModelResolver) implements BlockEntityRenderer<AltarBlockEntity, AltarRenderState> {
	public AltarRenderer(BlockEntityRendererProvider.Context context) {
		this(context.itemModelResolver());
	}

	@Override
	public AltarRenderState createRenderState() {
		return new AltarRenderState();
	}

	@Override
	public void submit(AltarRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
		if (!state.stackState.isEmpty()) {
			poseStack.pushPose();
			poseStack.translate(0.5, 1.25, 0.5);
			poseStack.mulPose(Axis.YP.rotationDegrees(state.rotation));
			state.stackState.submit(poseStack, submitNodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
			poseStack.popPose();
		}
	}

	@Override
	public void extractRenderState(AltarBlockEntity blockEntity, AltarRenderState state, float partialTicks, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
		BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
		itemModelResolver.updateForTopItem(state.stackState, blockEntity.getStack(), ItemDisplayContext.GROUND, blockEntity.getLevel(), null, HashCommon.long2int(blockEntity.getBlockPos().asLong()));
		state.rotation = (int) blockEntity.getBlockPos().asLong() + blockEntity.getLevel().getGameTime() * 4 + partialTicks;
	}
}
