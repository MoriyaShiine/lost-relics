/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.common.init;

import moriyashiine.lostrelics.common.world.entity.monster.Doppelganger;
import moriyashiine.lostrelics.common.world.entity.projectile.SmokeBall;
import moriyashiine.lostrelics.common.world.entity.projectile.TaintedBloodCrystal;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerEntityType;

public class ModEntityTypes {
	public static final EntityType<Doppelganger> DOPPELGANGER = registerEntityType("doppelganger", EntityType.Builder.<Doppelganger>of(Doppelganger::new, MobCategory.MISC)
					.sized(0.6F, 1.8F)
					.eyeHeight(1.62F)
					.vehicleAttachment(Avatar.DEFAULT_VEHICLE_ATTACHMENT)
					.clientTrackingRange(8),
			Doppelganger.createAttributes());
	public static final EntityType<SmokeBall> SMOKE_BALL = registerEntityType("smoke_ball", EntityType.Builder.<SmokeBall>of(SmokeBall::new, MobCategory.MISC)
			.noLootTable()
			.sized(0.5F, 0.5F)
			.eyeHeight(0.13F)
			.clientTrackingRange(4)
			.updateInterval(20));

	public static final EntityType<TaintedBloodCrystal> TAINTED_BLOOD_CRYSTAL = registerEntityType("tainted_blood_crystal", EntityType.Builder.<TaintedBloodCrystal>of(TaintedBloodCrystal::new, MobCategory.MISC)
			.noLootTable()
			.sized(0.5F, 0.5F)
			.eyeHeight(0.13F)
			.clientTrackingRange(4)
			.updateInterval(20));

	public static void init() {
	}
}
