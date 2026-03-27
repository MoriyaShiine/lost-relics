/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.client.renderer.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import moriyashiine.lostrelics.common.LostRelics;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.HumanoidArm;

public class SlimHumanoidModel<T extends HumanoidRenderState> extends HumanoidModel<T> {
	public static final ModelLayerLocation LAYER = new ModelLayerLocation(LostRelics.id("slim_biped"), "main");

	public SlimHumanoidModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition mesh = HumanoidModel.createMesh(CubeDeformation.NONE, 0);
		PartDefinition root = mesh.getRoot();
		root.addOrReplaceChild(PartNames.LEFT_ARM, CubeListBuilder.create().texOffs(32, 48).addBox(-1, -2, -2, 3, 12, 4, CubeDeformation.NONE), PartPose.offset(5, 2, 0));
		root.addOrReplaceChild(PartNames.RIGHT_ARM, CubeListBuilder.create().texOffs(40, 16).addBox(-2, -2, -2, 3, 12, 4, CubeDeformation.NONE), PartPose.offset(-5, 2, 0));
		return LayerDefinition.create(mesh, 64, 64);
	}

	@Override
	public void translateToHand(HumanoidRenderState state, HumanoidArm arm, PoseStack poseStack) {
		root().translateAndRotate(poseStack);
		ModelPart armPart = getArm(arm);
		float x = 0.5F * (arm == HumanoidArm.RIGHT ? 1 : -1);
		armPart.x += x;
		armPart.translateAndRotate(poseStack);
		armPart.x -= x;
	}
}
