/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.client.renderer.blockentity.state;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

public class AltarRenderState extends BlockEntityRenderState {
	public ItemStackRenderState stackState = new ItemStackRenderState();
	public float rotation = 0;
}
