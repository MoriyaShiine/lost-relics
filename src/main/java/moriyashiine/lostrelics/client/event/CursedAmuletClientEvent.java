/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.client.event;

import moriyashiine.lostrelics.client.render.entity.state.RelicSkeletonEntityRenderState;
import moriyashiine.lostrelics.common.init.ModItems;
import moriyashiine.lostrelics.common.util.LostRelicsUtil;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

public class CursedAmuletClientEvent implements ClientTickEvents.EndTick {
	public static Identifier relicSkeletonTexture = null;

	@Override
	public void onEndTick(MinecraftClient client) {
		if (client.player != null && LostRelicsUtil.hasRelic(client.player, ModItems.CURSED_AMULET)) {
			relicSkeletonTexture = RelicSkeletonEntityRenderState.getGemType(client.player).getTexture();
		} else {
			relicSkeletonTexture = null;
		}
	}
}
