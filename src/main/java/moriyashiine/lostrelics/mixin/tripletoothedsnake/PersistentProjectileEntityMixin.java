/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.mixin.tripletoothedsnake;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import moriyashiine.lostrelics.common.init.ModEntityTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PersistentProjectileEntity.class)
public abstract class PersistentProjectileEntityMixin extends ProjectileEntity {
	public PersistentProjectileEntityMixin(EntityType<? extends ProjectileEntity> entityType, World world) {
		super(entityType, world);
	}

	@WrapWithCondition(method = "onEntityHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setStuckArrowCount(I)V"))
	private boolean lostrelics$tripleToothedSnake(LivingEntity instance, int stuckArrowCount) {
		return getType() != ModEntityTypes.TAINTED_BLOOD_CRYSTAL;
	}
}
