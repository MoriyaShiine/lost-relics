/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.client.event;

import moriyashiine.lostrelics.common.init.ModComponentTypes;
import moriyashiine.lostrelics.common.init.ModItems;
import moriyashiine.lostrelics.common.init.ModParticleTypes;
import moriyashiine.lostrelics.common.tag.ModBlockTags;
import moriyashiine.lostrelics.common.util.LostRelicsUtil;
import moriyashiine.strawberrylib.api.event.client.OutlineEntityEvent;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
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
			if (world.getTime() % 20 == 0 && relicStack.getOrDefault(ModComponentTypes.RELIC_TOGGLE, false)) {
				for (BlockPos pos : BlockPos.iterateOutwards(client.player.getBlockPos(), 12, 12, 12)) {
					BlockState state = world.getBlockState(pos);
					if (state.isIn(ModBlockTags.TREASURE) && !state.isIn(ModBlockTags.UNIMPORTANT_TREASURE)) {
						double x = client.player.getX();
						double y = client.player.getBodyY(0.5);
						double z = client.player.getZ();
						double bX = pos.getX() + 0.5;
						double bY = pos.getY() + 0.5;
						double bZ = pos.getZ() + 0.5;
						boolean altColor = false;
						for (float i = 0.1F; i <= 1; i += 0.1F) {
							double dX = x - (x - bX) * i;
							double dY = y - (y - bY) * i;
							double dZ = z - (z - bZ) * i;
							world.addParticleClient(ModParticleTypes.TREASURE_SENSE, dX, dY, dZ, altColor ? 0x086F72 : 0x12C3B5, 0, 0);
							altColor = !altColor;
						}
					}
				}
			}
		}
	}

	public static class Outline implements OutlineEntityEvent {
		private static final OutlineData DATA = new OutlineData(TriState.TRUE, OptionalInt.of(0x0A9A92));

		@Override
		public @Nullable OutlineEntityEvent.OutlineData getOutlineData(Entity entity) {
			if (entity instanceof LivingEntity living && LostRelicsUtil.hasRelic(living, ModItems.TURQUOISE_EYE)) {
				return DATA;
			}
			if (isRelicUsable && client.player != null && entity instanceof LivingEntity living && living.getHealth() == living.getMaxHealth() && entity.distanceTo(client.player) <= 32 && living.canSee(client.player)) {
				return DATA;
			}
			return null;
		}
	}
}
