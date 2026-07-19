package moriyashiine.lostrelics.client.renderer.entity.accessory;

import com.mojang.math.Axis;
import com.swacky.ohmega.api.client.renderer.HumanoidRenderContext;
import com.swacky.ohmega.api.client.renderer.IHumanoidAccessoryRenderer;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.Direction;

public class EyeRenderer implements IHumanoidAccessoryRenderer {
	private final ItemModelResolver itemModelResolver;

	public EyeRenderer(ItemModelResolver itemModelResolver) {
		this.itemModelResolver = itemModelResolver;
	}

	@Override
	public void submit(HumanoidRenderContext context) {
		context.poseStack.pushPose();
		context.tryLockToPart(PartNames.HEAD);
		context.tryOffsetToPartFace(PartNames.HEAD, Direction.SOUTH);
		context.poseStack.mulPose(Axis.XP.rotationDegrees(180));
		context.poseStack.translate(1 / 8F, 0, 1 / 128F);
		context.poseStack.scale(1 / 4F, 1 / 4F, 1 / 4F);
		context.submitItem(itemModelResolver, new ItemStackRenderState(), context.stack);
		context.poseStack.popPose();
	}
}
