/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.client.render.block.entity.state;

import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;
import net.minecraft.client.render.item.ItemRenderState;

public class AltarBlockEntityRenderState extends BlockEntityRenderState {
	public ItemRenderState stackState = new ItemRenderState();
	public float rotation = 0;
}
