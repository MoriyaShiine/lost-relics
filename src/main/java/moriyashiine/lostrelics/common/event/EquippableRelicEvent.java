/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.event;

import com.swacky.ohmega.api.event.AccessoryCanEquipEvent;
import com.swacky.ohmega.api.event.EquipContext;
import moriyashiine.lostrelics.common.item.EquippableRelicItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class EquippableRelicEvent implements AccessoryCanEquipEvent {
	@Override
	public boolean process(PlayerEntity player, ItemStack stack, EquipContext context, boolean current) {
		return !(context == EquipContext.RIGHT_CLICK_HELD_ITEM && stack.getItem() instanceof EquippableRelicItem);
	}
}
