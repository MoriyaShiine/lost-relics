/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.client.render.entity.model;

import moriyashiine.lostrelics.common.LostRelics;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;

public class SlimBipedEntityModel<T extends BipedEntityRenderState> extends BipedEntityModel<T> {
	public static final EntityModelLayer LAYER = new EntityModelLayer(LostRelics.id("slim_biped"), "main");

	public SlimBipedEntityModel(ModelPart modelPart) {
		super(modelPart);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData data = BipedEntityModel.getModelData(Dilation.NONE, 0);
		ModelPartData root = data.getRoot();
		root.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(32, 48).cuboid(-1, -2, -2, 3, 12, 4, Dilation.NONE), ModelTransform.origin(5, 2, 0));
		root.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(40, 16).cuboid(-2, -2, -2, 3, 12, 4, Dilation.NONE), ModelTransform.origin(-5, 2, 0));
		return TexturedModelData.of(data, 64, 64);
	}

	@Override
	public void setArmAngle(BipedEntityRenderState state, Arm arm, MatrixStack matrices) {
		getRootPart().applyTransform(matrices);
		ModelPart armPart = getArm(arm);
		float x = 0.5F * (arm == Arm.RIGHT ? 1 : -1);
		armPart.originX += x;
		armPart.applyTransform(matrices);
		armPart.originX -= x;
	}
}
