/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.common.world.item;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;

public class TurquoiseEyeItem extends ToggleableRelicItem {
	public TurquoiseEyeItem(Properties properties) {
		super(properties, "tooltip.lostrelics.treasure_sense");
	}

	@Override
	public void onEquip(@NonNull Player player, @NonNull ItemStack stack) {
		player.removeEffect(MobEffects.INVISIBILITY);
	}
}
