/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.client.event;

import moriyashiine.lostrelics.client.renderer.entity.state.RelicSkeletonRenderState;
import moriyashiine.lostrelics.common.init.ModItems;
import moriyashiine.lostrelics.common.util.LostRelicsUtil;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;

public class CursedAmuletClientEvent implements ClientTickEvents.EndTick {
	public static Identifier relicSkeletonTexture = null;

	@Override
	public void onEndTick(Minecraft client) {
		if (client.player != null && LostRelicsUtil.hasRelic(client.player, ModItems.CURSED_AMULET)) {
			relicSkeletonTexture = RelicSkeletonRenderState.getGemType(client.player).getTexture();
		} else {
			relicSkeletonTexture = null;
		}
	}
}
