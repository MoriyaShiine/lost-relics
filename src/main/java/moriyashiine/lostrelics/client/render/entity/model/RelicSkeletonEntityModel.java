/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.client.render.entity.model;

import moriyashiine.lostrelics.common.LostRelics;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.PlayerEntityModel;

public class RelicSkeletonEntityModel extends PlayerEntityModel {
	public static final EntityModelLayer LAYER = new EntityModelLayer(LostRelics.id("relic_skeleton"), "main");

	public RelicSkeletonEntityModel(ModelPart root) {
		super(root, true);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData data = BipedEntityModel.getModelData(Dilation.NONE, 0);
		ModelPartData root = data.getRoot();
		ModelPartData head = root.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-3.5F, -7.5F, -4.35F, 7, 6, 7, Dilation.NONE)
				.uv(20, 15).cuboid(-2, -1.5F, -4.15F, 4, 1, 1, new Dilation(0.01F))
				.uv(0, 14).cuboid(-4, -6.5F, -4.6F, 8, 4, 1, new Dilation(0.1F)), ModelTransform.NONE);
		head.addChild("lowerJaw", ModelPartBuilder.create().uv(4, 21).cuboid(-3, -1.5F, -3.6F, 6, 2, 4, Dilation.NONE), ModelTransform.of(0, -0.25F, 0, 0.0873F, 0, 0));
		ModelPartData body = root.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(0, 45).cuboid(-4, 0, -2, 8, 8, 4, new Dilation(-0.01F))
				.uv(55, 0).cuboid(-1, -1.75F, 1, 2, 12, 1, Dilation.NONE)
				.uv(30, 0).cuboid(-4, 9, -2, 8, 3, 4, new Dilation(-0.01F)), ModelTransform.NONE);
		ModelPartData leftArm = root.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(0, 29).cuboid(-1, -2, -1, 2, 12, 2, Dilation.NONE), ModelTransform.origin(5, 2, 0));
		ModelPartData rightArm = root.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(0, 29).mirrored().cuboid(-1, -2, -1, 2, 12, 2, Dilation.NONE), ModelTransform.origin(-5, 2, 0));
		ModelPartData leftLeg = root.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().uv(12, 29).cuboid(-1, 0, -1, 2, 12, 2, Dilation.NONE), ModelTransform.origin(1.9F, 12, 0));
		ModelPartData rightLeg = root.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(12, 29).mirrored().cuboid(-1, 0, -1, 2, 12, 2, Dilation.NONE), ModelTransform.origin(-1.9F, 12, 0));
		head.addChild(EntityModelPartNames.HAT, ModelPartBuilder.create(), ModelTransform.NONE);
		leftArm.addChild("left_sleeve", ModelPartBuilder.create(), ModelTransform.NONE);
		rightArm.addChild("right_sleeve", ModelPartBuilder.create(), ModelTransform.NONE);
		leftLeg.addChild("left_pants", ModelPartBuilder.create(), ModelTransform.NONE);
		rightLeg.addChild("right_pants", ModelPartBuilder.create(), ModelTransform.NONE);
		body.addChild(EntityModelPartNames.JACKET, ModelPartBuilder.create(), ModelTransform.NONE);
		return TexturedModelData.of(data, 64, 64);
	}
}
