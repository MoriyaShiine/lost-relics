package moriyashiine.lostrelics.client.event;

import moriyashiine.lostrelics.common.init.LostRelicsDataComponents;
import moriyashiine.lostrelics.common.init.LostRelicsItems;
import moriyashiine.lostrelics.common.init.LostRelicsParticleTypes;
import moriyashiine.lostrelics.common.tag.LostRelicsBlockTags;
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
	public static void init() {
		ClientTickEvents.END_LEVEL_TICK.register(new Tick());
		OutlineEntityEvent.EVENT.register(new Outline());
	}

	private static final Minecraft client = Minecraft.getInstance();

	private static boolean isRelicUsable = false;

	private static class Tick implements ClientTickEvents.EndLevelTick {
		@Override
		public void onEndTick(ClientLevel level) {
			ItemStack relic = LostRelicsUtil.getRelic(client.player, LostRelicsItems.TURQUOISE_EYE);
			isRelicUsable = LostRelicsUtil.isUsable(client.player, relic);
			if (level.getGameTime() % 20 == 0 && relic.getOrDefault(LostRelicsDataComponents.RELIC_TOGGLE, false)) {
				for (BlockPos pos : BlockPos.withinManhattan(client.player.blockPosition(), 12, 12, 12)) {
					BlockState state = level.getBlockState(pos);
					if (state.is(LostRelicsBlockTags.TREASURE) && !state.is(LostRelicsBlockTags.UNIMPORTANT_TREASURE)) {
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
							level.addParticle(LostRelicsParticleTypes.TREASURE_SENSE, dX, dY, dZ, altColor ? 0x086F72 : 0x12C3B5, 0, 0);
							altColor = !altColor;
						}
					}
				}
			}
		}
	}

	private static class Outline implements OutlineEntityEvent {
		private static final OutlineData DATA = new OutlineData(TriState.TRUE, OptionalInt.of(0x0A9A92));

		@Override
		public OutlineEntityEvent.@Nullable OutlineData getOutlineData(Entity entity) {
			if (entity instanceof LivingEntity living && LostRelicsUtil.hasRelic(living, LostRelicsItems.TURQUOISE_EYE)) {
				return DATA;
			}
			if (isRelicUsable && client.player != null && entity instanceof LivingEntity living && living.getHealth() == living.getMaxHealth() && entity.distanceTo(client.player) <= 32 && living.hasLineOfSight(client.player)) {
				return DATA;
			}
			return null;
		}
	}
}
