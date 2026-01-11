/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.init;

import moriyashiine.lostrelics.common.entity.mob.DoppelgangerEntity;
import moriyashiine.lostrelics.common.entity.projectile.SmokeBallEntity;
import moriyashiine.lostrelics.common.entity.projectile.TaintedBloodCrystalEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.PlayerLikeEntity;
import net.minecraft.entity.SpawnGroup;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerEntityType;

public class ModEntityTypes {
	public static final EntityType<DoppelgangerEntity> DOPPELGANGER = registerEntityType("doppelganger", EntityType.Builder.<DoppelgangerEntity>create(DoppelgangerEntity::new, SpawnGroup.MISC)
					.dimensions(0.6F, 1.8F)
					.eyeHeight(1.62F)
					.vehicleAttachment(PlayerLikeEntity.VEHICLE_ATTACHMENT)
					.maxTrackingRange(8),
			DoppelgangerEntity.createDoppelgangerAttributes());
	public static final EntityType<SmokeBallEntity> SMOKE_BALL = registerEntityType("smoke_ball", EntityType.Builder.<SmokeBallEntity>create(SmokeBallEntity::new, SpawnGroup.MISC)
			.dropsNothing()
			.dimensions(0.5F, 0.5F)
			.eyeHeight(0.13F)
			.maxTrackingRange(4)
			.trackingTickInterval(20));

	public static final EntityType<TaintedBloodCrystalEntity> TAINTED_BLOOD_CRYSTAL = registerEntityType("tainted_blood_crystal", EntityType.Builder.<TaintedBloodCrystalEntity>create(TaintedBloodCrystalEntity::new, SpawnGroup.MISC)
			.dropsNothing()
			.dimensions(0.5F, 0.5F)
			.eyeHeight(0.13F)
			.maxTrackingRange(4)
			.trackingTickInterval(20));

	public static void init() {
	}
}
