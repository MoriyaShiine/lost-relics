package moriyashiine.lostrelics.common.util;

import eu.pb4.trinkets.api.TrinketSlotAccess;
import eu.pb4.trinkets.api.TrinketsApi;
import moriyashiine.lostrelics.common.init.LostRelicsEntityComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class LostRelicsUtil {
	public static ItemStack getRelic(LivingEntity entity, Item relic) {
		return TrinketsApi.getAttachment(entity).findFirst(relic).map(TrinketSlotAccess::get).orElse(ItemStack.EMPTY);
	}

	public static boolean hasRelic(LivingEntity entity, Item relic) {
		return TrinketsApi.getAttachment(entity).isEquipped(relic);
	}

	public static boolean isUsable(LivingEntity entity, ItemStack relic) {
		if (relic.isEmpty()) {
			return false;
		}
		if (entity instanceof Player player) {
			return !player.getCooldowns().isOnCooldown(relic);
		}
		return true;
	}

	public static void setCooldown(Player player, ItemStack relic, int cooldown) {
		LostRelicsEntityComponents.PERSISTENT_COOLDOWN.get(player).setCooldown(relic, cooldown);
		player.getCooldowns().addCooldown(relic, cooldown);
	}
}
