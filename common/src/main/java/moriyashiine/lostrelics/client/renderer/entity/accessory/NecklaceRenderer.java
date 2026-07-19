package moriyashiine.lostrelics.client.renderer.entity.accessory;

import com.mojang.math.Axis;
import com.swacky.ohmega.api.client.renderer.HumanoidRenderContext;
import com.swacky.ohmega.api.client.renderer.IHumanoidAccessoryRenderer;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.Direction;

public class NecklaceRenderer implements IHumanoidAccessoryRenderer {
	private final ItemModelResolver itemModelResolver;

	public NecklaceRenderer(ItemModelResolver itemModelResolver) {
		this.itemModelResolver = itemModelResolver;
	}

	@Override
	public void submit(HumanoidRenderContext context) {
		context.poseStack.pushPose();
		context.tryLockToPart(PartNames.BODY);
		context.tryOffsetToPartFace(PartNames.BODY, Direction.SOUTH);
		context.poseStack.mulPose(Axis.XP.rotationDegrees(180));
		context.poseStack.translate(0, 1 / 4F, context.state.chestEquipment.isEmpty() ? 1 / 128F - 1 / 1024F : 1 / 14F);
		context.poseStack.scale(1 / 4F, 1 / 4F, 1 / 4F);
		context.submitItem(itemModelResolver, new ItemStackRenderState(), context.stack);
		context.poseStack.popPose();
	}
}
