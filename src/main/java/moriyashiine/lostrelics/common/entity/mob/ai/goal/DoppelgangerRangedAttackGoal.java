/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.entity.mob.ai.goal;

import moriyashiine.lostrelics.common.entity.mob.DoppelgangerEntity;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import org.jetbrains.annotations.Nullable;

public class DoppelgangerRangedAttackGoal extends Goal {
	private final DoppelgangerEntity doppelganger;
	private int cooldown = 0;

	public DoppelgangerRangedAttackGoal(DoppelgangerEntity doppelganger) {
		this.doppelganger = doppelganger;
	}

	@Override
	public boolean canStart() {
		return doppelganger.getTarget() != null && doppelganger.getTarget().isAlive() && !getRangedWeaponStack().isEmpty();
	}

	@Override
	public boolean shouldRunEveryTick() {
		return true;
	}

	@Override
	public void tick() {
		if (cooldown > 0) {
			cooldown--;
		}
		if (cooldown == 0) {
			@Nullable LivingEntity copiedEntity = doppelganger.getCopiedEntity();
			if (copiedEntity != null) {
				@Nullable LivingEntity target = doppelganger.getTarget();
				if (target != null && doppelganger.distanceTo(target) <= 24 && doppelganger.canSee(target)) {
					cooldown = 60;
					doppelganger.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, target.getEntityPos());
					ItemStack rangedWeaponStack = getRangedWeaponStack().copy();
					ItemStack projectileType = copiedEntity.getProjectileType(rangedWeaponStack).copy();
					if (projectileType.isEmpty()) {
						projectileType = Items.ARROW.getDefaultStack();
					}
					PersistentProjectileEntity projectile = ProjectileUtil.createArrowProjectile(doppelganger, projectileType, 1, rangedWeaponStack);
					double dX = target.getX() - doppelganger.getX();
					double dY = target.getBodyY(1 / 3F) - projectile.getY();
					double dZ = target.getZ() - doppelganger.getZ();
					if (doppelganger.getEntityWorld() instanceof ServerWorld world) {
						ProjectileEntity.spawnWithVelocity(projectile, world, projectileType, dX, dY + Math.sqrt(dX * dX + dZ * dZ) * 0.2F, dZ, 1.6F, 3);
					}
					SLibUtils.playSound(doppelganger, SoundEvents.ENTITY_ARROW_SHOOT);
				}
			}
		}
	}

	private ItemStack getRangedWeaponStack() {
		if (doppelganger.getMainHandStack().getItem() instanceof RangedWeaponItem) {
			return doppelganger.getMainHandStack();
		} else if (doppelganger.getOffHandStack().getItem() instanceof RangedWeaponItem) {
			return doppelganger.getOffHandStack();
		}
		return ItemStack.EMPTY;
	}
}
