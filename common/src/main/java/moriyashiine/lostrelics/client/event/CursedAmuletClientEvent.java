package moriyashiine.lostrelics.client.event;

import moriyashiine.lostrelics.client.renderer.entity.state.RelicSkeletonRenderState;
import moriyashiine.lostrelics.common.init.LostRelicsItems;
import moriyashiine.lostrelics.common.util.LostRelicsUtil;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;

public class CursedAmuletClientEvent implements ClientTickEvents.EndTick {
	public static void init() {
		ClientTickEvents.END_CLIENT_TICK.register(new CursedAmuletClientEvent());
	}

	public static Identifier relicSkeletonTexture = null;

	@Override
	public void onEndTick(Minecraft client) {
		if (client.player != null && LostRelicsUtil.hasRelic(client.player, LostRelicsItems.CURSED_AMULET)) {
			relicSkeletonTexture = RelicSkeletonRenderState.getGemType(client.player).getTexture();
		} else {
			relicSkeletonTexture = null;
		}
	}
}
