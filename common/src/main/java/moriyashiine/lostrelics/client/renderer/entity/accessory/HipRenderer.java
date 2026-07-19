package moriyashiine.lostrelics.client.renderer.entity.accessory;

import com.mojang.math.Axis;
import com.swacky.ohmega.api.client.renderer.HumanoidRenderContext;
import com.swacky.ohmega.api.client.renderer.IHumanoidAccessoryRenderer;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.Direction;

public class HipRenderer implements IHumanoidAccessoryRenderer {
	private final ItemModelResolver itemModelResolver;

	public HipRenderer(ItemModelResolver itemModelResolver) {
		this.itemModelResolver = itemModelResolver;
	}

	@Override
	public void submit(HumanoidRenderContext context) {
		context.poseStack.pushPose();
		context.tryLockToPart(PartNames.BODY);
		context.tryOffsetToPartFace(PartNames.BODY, Direction.EAST);
		context.poseStack.mulPose(Axis.XP.rotationDegrees(180));
		context.poseStack.mulPose(Axis.YP.rotationDegrees(90));
		context.poseStack.translate(-1 / 12F, -1 / 3F, context.state.legsEquipment.isEmpty() ? -1 / 128F : -1 / 32F);
		context.poseStack.scale(1 / 4F, 1 / 4F, 1 / 4F);
		context.submitItem(itemModelResolver, new ItemStackRenderState(), context.stack);
		context.poseStack.popPose();
	}
}
