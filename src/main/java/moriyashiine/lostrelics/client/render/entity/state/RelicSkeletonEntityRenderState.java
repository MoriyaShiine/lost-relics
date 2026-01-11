/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.client.render.entity.state;

import moriyashiine.lostrelics.common.entity.mob.GemType;
import moriyashiine.lostrelics.common.supporter.SupporterInit;
import moriyashiine.strawberrylib.api.module.SLibSupporterUtils;
import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;
import net.minecraft.entity.player.PlayerEntity;

public class RelicSkeletonEntityRenderState {
	public static final RenderStateDataKey<RelicSkeletonEntityRenderState> KEY = RenderStateDataKey.create(() -> "relic skeleton");

	public boolean enabled = false;
	public GemType gemType = GemType.DEFAULT;

	public static GemType getGemType(PlayerEntity player) {
		if (SLibSupporterUtils.isSupporter(player)) {
			GemType gemType = SLibSupporterUtils.getData(player, SupporterInit.RELIC_SKELETON_GEM_TYPE);
			if (gemType != GemType.DEFAULT) {
				return gemType;
			}
		}
		int index = player.getUuid().hashCode() % (GemType.values().length - 1);
		if (index < 0) {
			index += (GemType.values().length - 1);
		}
		return GemType.values()[index + 1];
	}
}
