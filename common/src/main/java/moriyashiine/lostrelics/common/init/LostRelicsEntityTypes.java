package moriyashiine.lostrelics.common.init;

import moriyashiine.lostrelics.common.references.LostRelicsEntityTypeIds;
import moriyashiine.lostrelics.common.world.entity.Doppelganger;
import moriyashiine.lostrelics.common.world.entity.projectile.SmokeBall;
import moriyashiine.lostrelics.common.world.entity.projectile.arrow.TaintedBloodCrystal;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerEntityType;

public class LostRelicsEntityTypes {
	public static final EntityType<Doppelganger> DOPPELGANGER = registerEntityType(LostRelicsEntityTypeIds.DOPPELGANGER, EntityType.Builder.<Doppelganger>of(Doppelganger::new, MobCategory.MISC)
					.sized(0.6F, 1.8F)
					.eyeHeight(1.62F)
					.vehicleAttachment(Avatar.DEFAULT_VEHICLE_ATTACHMENT)
					.clientTrackingRange(8),
			Doppelganger.createAttributes());
	public static final EntityType<SmokeBall> SMOKE_BALL = registerEntityType(LostRelicsEntityTypeIds.SMOKE_BALL, EntityType.Builder.<SmokeBall>of(SmokeBall::new, MobCategory.MISC)
			.noLootTable()
			.sized(0.5F, 0.5F)
			.eyeHeight(0.13F)
			.clientTrackingRange(4)
			.updateInterval(20));

	public static final EntityType<TaintedBloodCrystal> TAINTED_BLOOD_CRYSTAL = registerEntityType(LostRelicsEntityTypeIds.TAINTED_BLOOD_CRYSTAL, EntityType.Builder.<TaintedBloodCrystal>of(TaintedBloodCrystal::new, MobCategory.MISC)
			.noLootTable()
			.sized(0.5F, 0.5F)
			.eyeHeight(0.13F)
			.clientTrackingRange(4)
			.updateInterval(20));

	public static void init() {
	}
}
