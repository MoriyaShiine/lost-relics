/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.client.event;

import moriyashiine.lostrelics.common.init.ModItems;
import moriyashiine.lostrelics.common.util.LostRelicsUtil;
import moriyashiine.strawberrylib.api.event.client.OutlineEntityEvent;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.OptionalInt;

public class TurquoiseEyeClientEvent {
	private static final MinecraftClient client = MinecraftClient.getInstance();

	private static boolean isRelicUsable = false;

	public static class Tick implements ClientTickEvents.EndWorldTick {
		@Override
		public void onEndTick(ClientWorld world) {
			ItemStack relicStack = LostRelicsUtil.getRelic(client.player, ModItems.TURQUOISE_EYE);
			isRelicUsable = LostRelicsUtil.isUsable(client.player, relicStack);
		}
	}

	public static class Outline implements OutlineEntityEvent {
		private static final OutlineData DATA = new OutlineData(TriState.TRUE, OptionalInt.of(0x0A9A92));

		@Override
		public @Nullable OutlineEntityEvent.OutlineData getOutlineData(Entity entity) {
			if (isRelicUsable && entity instanceof LivingEntity living && living.getHealth() == living.getMaxHealth() && entity.distanceTo(client.player) <= 32 && living.canSee(client.player)) {
				return DATA;
			}
			return null;
		}
	}
}
