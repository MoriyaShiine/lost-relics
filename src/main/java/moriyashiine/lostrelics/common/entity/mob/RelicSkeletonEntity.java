/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.entity.mob;

import moriyashiine.lostrelics.common.supporter.SupporterInit;
import moriyashiine.strawberrylib.api.module.SLibSupporterUtils;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class RelicSkeletonEntity extends SkeletonEntity {
	private GemType gemType = GemType.DEFAULT;

	public RelicSkeletonEntity(EntityType<? extends SkeletonEntity> entityType, World world) {
		super(entityType, world);
	}

	public GemType getGemType() {
		return gemType;
	}

	public void updateGemType(PlayerEntity player) {
		if (SLibSupporterUtils.isSupporter(player)) {
			gemType = SLibSupporterUtils.getData(player, SupporterInit.RELIC_SKELETON_GEM_TYPE);
			if (gemType != GemType.DEFAULT) {
				return;
			}
		}
		int index = player.getUuid().hashCode() % (GemType.values().length - 1);
		if (index < 0) {
			index += (GemType.values().length - 1);
		}
		gemType = GemType.values()[index + 1];
	}
}
