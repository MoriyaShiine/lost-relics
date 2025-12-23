/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import org.jspecify.annotations.Nullable;

public class TurquoiseEyeItem extends ToggleableRelicItem {
	public TurquoiseEyeItem(Settings settings) {
		super(settings, "tooltip.lostrelics.treasure_sense");
	}

	@Override
	public void inventoryTick(ItemStack stack, ServerWorld world, Entity entity, @Nullable EquipmentSlot slot) {
		if (entity instanceof LivingEntity living) {
			living.removeStatusEffect(StatusEffects.INVISIBILITY);
		}
	}
}
