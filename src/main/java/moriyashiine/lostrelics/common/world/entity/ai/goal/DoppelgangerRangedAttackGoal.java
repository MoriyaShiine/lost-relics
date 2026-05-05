/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.common.world.entity.ai.goal;

import moriyashiine.lostrelics.common.world.entity.Doppelganger;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;

public class DoppelgangerRangedAttackGoal extends Goal {
	private final Doppelganger doppelganger;
	private int attackTime = 0;

	public DoppelgangerRangedAttackGoal(Doppelganger doppelganger) {
		this.doppelganger = doppelganger;
	}

	@Override
	public boolean canUse() {
		return doppelganger.getTarget() != null && doppelganger.getTarget().isAlive() && !getRangedWeaponStack().isEmpty();
	}

	@Override
	public boolean requiresUpdateEveryTick() {
		return true;
	}

	@Override
	public void tick() {
		if (attackTime > 0) {
			attackTime--;
		}
		if (attackTime == 0) {
			LivingEntity copiedEntity = doppelganger.getCopiedEntity();
			if (copiedEntity != null) {
				LivingEntity target = doppelganger.getTarget();
				if (target != null && doppelganger.distanceTo(target) <= 24 && doppelganger.hasLineOfSight(target)) {
					attackTime = 60;
					doppelganger.lookAt(EntityAnchorArgument.Anchor.EYES, target.position());
					ItemStack rangedWeaponStack = getRangedWeaponStack().copy();
					ItemStack projectileType = copiedEntity.getProjectile(rangedWeaponStack).copy();
					if (projectileType.isEmpty()) {
						projectileType = Items.ARROW.getDefaultInstance();
					}
					AbstractArrow arrow = ProjectileUtil.getMobArrow(doppelganger, projectileType, 1, rangedWeaponStack);
					double dX = target.getX() - doppelganger.getX();
					double dY = target.getY(1 / 3F) - arrow.getY();
					double dZ = target.getZ() - doppelganger.getZ();
					if (doppelganger.level() instanceof ServerLevel level) {
						Projectile.spawnProjectileUsingShoot(arrow, level, projectileType, dX, dY + Math.sqrt(dX * dX + dZ * dZ) * 0.2F, dZ, 1.6F, 3);
					}
					SLibUtils.playSound(doppelganger, SoundEvents.ARROW_SHOOT);
				}
			}
		}
	}

	private ItemStack getRangedWeaponStack() {
		if (doppelganger.getMainHandItem().getItem() instanceof ProjectileWeaponItem) {
			return doppelganger.getMainHandItem();
		} else if (doppelganger.getOffhandItem().getItem() instanceof ProjectileWeaponItem) {
			return doppelganger.getOffhandItem();
		}
		return ItemStack.EMPTY;
	}
}
