/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.init;

import moriyashiine.lostrelics.common.entity.mob.RelicSkeletonEntity;
import moriyashiine.lostrelics.common.entity.projectile.TaintedBloodCrystalEntity;
import moriyashiine.strawberrylib.api.module.SLibRegistries;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.PlayerLikeEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.mob.AbstractSkeletonEntity;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerEntityType;

public class ModEntityTypes {
	public static final EntityType<RelicSkeletonEntity> RELIC_SKELETON = registerEntityType("relic_skeleton", EntityType.Builder.create(RelicSkeletonEntity::new, SpawnGroup.MISC)
					.disableSaving()
					.disableSummon()
					.dimensions(0.6F, 1.8F)
					.eyeHeight(1.62F)
					.vehicleAttachment(PlayerLikeEntity.VEHICLE_ATTACHMENT)
					.maxTrackingRange(32)
					.trackingTickInterval(2),
			AbstractSkeletonEntity.createAbstractSkeletonAttributes());

	public static final EntityType<TaintedBloodCrystalEntity> TAINTED_BLOOD_CRYSTAL = registerEntityType("tainted_blood_crystal", EntityType.Builder.<TaintedBloodCrystalEntity>create(TaintedBloodCrystalEntity::new, SpawnGroup.MISC)
			.dropsNothing()
			.dimensions(0.5F, 0.5F)
			.eyeHeight(0.13F)
			.maxTrackingRange(4)
			.trackingTickInterval(20));

	public static void init() {
		SLibRegistries.registerModelReplacementCopyFunction((player, replacement) -> {
			if (replacement instanceof RelicSkeletonEntity relicSkeleton) {
				relicSkeleton.updateGemType(player);
			}
		});
	}
}
