/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.common.world.item;

import com.swacky.ohmega.api.common.item.EquipContext;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class TurquoiseEyeItem extends ToggleableRelicItem {
	public TurquoiseEyeItem(Properties properties) {
		super(properties, "tooltip.lost_relics.treasure_sense");
	}

	@Override
	public void onEquip(@NonNull LivingEntity entity, @NonNull ItemStack stack, @NonNull EquipContext context) {
		entity.removeEffect(MobEffects.INVISIBILITY);
	}

	@Override
	public double getMobVisibilityMultiplier(@NonNull ItemStack stack, @Nullable Entity targetingEntity) {
		return targetingEntity == null || targetingEntity instanceof Enemy || targetingEntity instanceof NeutralMob ? 2 : 1;
	}
}
