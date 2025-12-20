/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.client.render.entity.state;

import moriyashiine.lostrelics.common.entity.mob.GemType;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;

public class RelicSkeletonEntityRenderState extends BipedEntityRenderState {
	public GemType gemType = GemType.DEFAULT;
}
