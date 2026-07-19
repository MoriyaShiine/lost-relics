package moriyashiine.lostrelics.client.renderer.entity.model;

import moriyashiine.lostrelics.common.LostRelics;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.model.player.PlayerModel;

public class RelicSkeletonModel extends PlayerModel {
	public static final ModelLayerLocation LAYER = new ModelLayerLocation(LostRelics.id("relic_skeleton"), "main");

	public RelicSkeletonModel(ModelPart root) {
		super(root, true);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition mesh = HumanoidModel.createMesh(CubeDeformation.NONE, 0);
		PartDefinition root = mesh.getRoot();
		PartDefinition head = root.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -7.5F, -4.35F, 7, 6, 7, CubeDeformation.NONE)
				.texOffs(20, 15).addBox(-2, -1.5F, -4.15F, 4, 1, 1, new CubeDeformation(0.01F))
				.texOffs(0, 14).addBox(-4, -6.5F, -4.6F, 8, 4, 1, new CubeDeformation(0.1F)), PartPose.ZERO);
		head.addOrReplaceChild("lowerJaw", CubeListBuilder.create().texOffs(4, 21).addBox(-3, -1.5F, -3.6F, 6, 2, 4, CubeDeformation.NONE), PartPose.offsetAndRotation(0, -0.25F, 0, 0.0873F, 0, 0));
		PartDefinition body = root.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(0, 45).addBox(-4, 0, -2, 8, 8, 4, new CubeDeformation(-0.01F))
				.texOffs(55, 0).addBox(-1, -1.75F, 1, 2, 12, 1, CubeDeformation.NONE)
				.texOffs(30, 0).addBox(-4, 9, -2, 8, 3, 4, new CubeDeformation(-0.01F)), PartPose.ZERO);
		PartDefinition leftArm = root.addOrReplaceChild(PartNames.LEFT_ARM, CubeListBuilder.create().texOffs(0, 29).addBox(-1, -2, -1, 2, 12, 2, CubeDeformation.NONE), PartPose.offset(5, 2, 0));
		PartDefinition rightArm = root.addOrReplaceChild(PartNames.RIGHT_ARM, CubeListBuilder.create().texOffs(0, 29).mirror().addBox(-1, -2, -1, 2, 12, 2, CubeDeformation.NONE), PartPose.offset(-5, 2, 0));
		PartDefinition leftLeg = root.addOrReplaceChild(PartNames.LEFT_LEG, CubeListBuilder.create().texOffs(12, 29).addBox(-1, 0, -1, 2, 12, 2, CubeDeformation.NONE), PartPose.offset(1.9F, 12, 0));
		PartDefinition rightLeg = root.addOrReplaceChild(PartNames.RIGHT_LEG, CubeListBuilder.create().texOffs(12, 29).mirror().addBox(-1, 0, -1, 2, 12, 2, CubeDeformation.NONE), PartPose.offset(-1.9F, 12, 0));
		head.addOrReplaceChild(PartNames.HAT, CubeListBuilder.create(), PartPose.ZERO);
		leftArm.addOrReplaceChild("left_sleeve", CubeListBuilder.create(), PartPose.ZERO);
		rightArm.addOrReplaceChild("right_sleeve", CubeListBuilder.create(), PartPose.ZERO);
		leftLeg.addOrReplaceChild("left_pants", CubeListBuilder.create(), PartPose.ZERO);
		rightLeg.addOrReplaceChild("right_pants", CubeListBuilder.create(), PartPose.ZERO);
		body.addOrReplaceChild(PartNames.JACKET, CubeListBuilder.create(), PartPose.ZERO);
		return LayerDefinition.create(mesh, 64, 64);
	}
}
