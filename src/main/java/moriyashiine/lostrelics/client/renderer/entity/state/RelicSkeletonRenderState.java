/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.client.renderer.entity.state;

import moriyashiine.lostrelics.common.supporter.SupporterInit;
import moriyashiine.lostrelics.common.world.entity.monster.GemType;
import moriyashiine.strawberrylib.api.module.SLibSupporterUtils;
import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class RelicSkeletonRenderState {
	public static final RenderStateDataKey<RelicSkeletonRenderState> KEY = RenderStateDataKey.create(() -> "relic skeleton");

	public boolean enabled = false;
	public GemType gemType = GemType.DEFAULT;

	public static GemType getGemType(LivingEntity entity) {
		if (entity instanceof Player player && SLibSupporterUtils.isSupporter(player)) {
			GemType gemType = SLibSupporterUtils.getData(player, SupporterInit.RELIC_SKELETON_GEM_TYPE);
			if (gemType != GemType.DEFAULT) {
				return gemType;
			}
		}
		int index = entity.getUUID().hashCode() % (GemType.values().length - 1);
		if (index < 0) {
			index += (GemType.values().length - 1);
		}
		return GemType.values()[index + 1];
	}
}
