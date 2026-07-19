package moriyashiine.lostrelics.client.renderer.entity.state;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.Identifier;

public class DoppelgangerRenderState extends HumanoidRenderState {
	public Identifier texture = DefaultPlayerSkin.getDefaultTexture();
	public HumanoidModel<DoppelgangerRenderState> model;
}
