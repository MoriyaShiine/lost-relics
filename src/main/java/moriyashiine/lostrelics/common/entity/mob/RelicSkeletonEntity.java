/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.entity.mob;

import moriyashiine.lostrelics.client.supporter.GemType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class RelicSkeletonEntity extends SkeletonEntity {
	@Nullable
	private UUID playerUuid = null;
	private GemType gemType = GemType.DEFAULT;

	public RelicSkeletonEntity(EntityType<? extends SkeletonEntity> entityType, World world) {
		super(entityType, world);
	}

	public UUID getPlayerUuid() {
		return playerUuid == null ? getUuid() : playerUuid;
	}

	public void setPlayerUuid(UUID playerUuid) {
		this.playerUuid = playerUuid;
	}

	public GemType getGemType() {
		return gemType;
	}

	public void setGemType(GemType gemType) {
		this.gemType = gemType;
	}
}
