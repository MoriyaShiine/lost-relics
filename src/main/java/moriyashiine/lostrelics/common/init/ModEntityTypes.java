/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.init;

import moriyashiine.lostrelics.common.entity.projectile.TaintedBloodCrystalEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerEntityType;

public class ModEntityTypes {
	public static final EntityType<TaintedBloodCrystalEntity> TAINTED_BLOOD_CRYSTAL = registerEntityType("tainted_blood_crystal", EntityType.Builder.<TaintedBloodCrystalEntity>create(TaintedBloodCrystalEntity::new, SpawnGroup.MISC)
			.dropsNothing()
			.dimensions(0.5F, 0.5F)
			.eyeHeight(0.13F)
			.maxTrackingRange(4)
			.trackingTickInterval(20));

	public static void init() {
	}
}
