/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.common.world.item;

import com.swacky.ohmega.api.common.item.EquipContext;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;

public class TurquoiseEyeItem extends ToggleableRelicItem {
	public TurquoiseEyeItem(Properties properties) {
		super(properties, "tooltip.lost-relics.treasure_sense");
	}

	@Override
	public void onEquip(@NonNull LivingEntity entity, @NonNull ItemStack stack, @NonNull EquipContext context) {
		entity.removeEffect(MobEffects.INVISIBILITY);
	}
}
