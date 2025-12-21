/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.client.render.entity.state;

import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.util.Identifier;

public class DoppelgangerEntityRenderState extends BipedEntityRenderState {
	public Identifier texture = DefaultSkinHelper.getTexture();
	public BipedEntityModel<DoppelgangerEntityRenderState> model;
}
