/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.item;

import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.jspecify.annotations.NonNull;

public class TurquoiseEyeItem extends ToggleableRelicItem {
	public TurquoiseEyeItem(Settings settings) {
		super(settings, "tooltip.lostrelics.treasure_sense");
	}

	@Override
	public void onEquip(@NonNull PlayerEntity player, @NonNull ItemStack stack) {
		player.removeStatusEffect(StatusEffects.INVISIBILITY);
	}
}
