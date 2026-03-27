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
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

import java.util.OptionalInt;

public class TurquoiseEyeClientEvent {
	private static final Minecraft client = Minecraft.getInstance();

	private static boolean isRelicUsable = false;

	public static class Tick implements ClientTickEvents.EndLevelTick {
		@Override
		public void onEndTick(ClientLevel level) {
			ItemStack relicStack = LostRelicsUtil.getRelic(client.player, ModItems.TURQUOISE_EYE);
			isRelicUsable = LostRelicsUtil.isUsable(client.player, relicStack);
			if (level.getGameTime() % 20 == 0 && relicStack.getOrDefault(ModComponentTypes.RELIC_TOGGLE, false)) {
				for (BlockPos pos : BlockPos.withinManhattan(client.player.blockPosition(), 12, 12, 12)) {
					BlockState state = level.getBlockState(pos);
					if (state.is(ModBlockTags.TREASURE) && !state.is(ModBlockTags.UNIMPORTANT_TREASURE)) {
						double x = client.player.getX();
						double y = client.player.getY(0.5);
						double z = client.player.getZ();
						double bX = pos.getX() + 0.5;
						double bY = pos.getY() + 0.5;
						double bZ = pos.getZ() + 0.5;
						boolean altColor = false;
						for (float i = 0.1F; i <= 1; i += 0.1F) {
							double dX = x - (x - bX) * i;
							double dY = y - (y - bY) * i;
							double dZ = z - (z - bZ) * i;
							level.addParticle(ModParticleTypes.TREASURE_SENSE, dX, dY, dZ, altColor ? 0x086F72 : 0x12C3B5, 0, 0);
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
		public OutlineEntityEvent.@Nullable OutlineData getOutlineData(Entity entity) {
			if (entity instanceof LivingEntity living && LostRelicsUtil.hasRelic(living, ModItems.TURQUOISE_EYE)) {
				return DATA;
			}
			if (isRelicUsable && client.player != null && entity instanceof LivingEntity living && living.getHealth() == living.getMaxHealth() && entity.distanceTo(client.player) <= 32 && living.hasLineOfSight(client.player)) {
				return DATA;
			}
			return null;
		}
	}
}
