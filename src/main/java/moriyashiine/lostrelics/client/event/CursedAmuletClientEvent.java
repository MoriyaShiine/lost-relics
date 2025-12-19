/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.client.event;

import moriyashiine.lostrelics.client.render.entity.model.RelicSkeletonEntityModel;
import moriyashiine.lostrelics.common.entity.mob.RelicSkeletonEntity;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class CursedAmuletClientEvent implements ClientTickEvents.EndTick {
	public static RelicSkeletonEntityModel relicSkeletonModel = null;
	public static Identifier relicSkeletonTexture = null;

	@Override
	public void onEndTick(MinecraftClient client) {
		if (client.player != null) {
			@Nullable LivingEntity replacement = SLibUtils.getModelReplacement(client.player);
			if (replacement instanceof RelicSkeletonEntity relicSkeleton) {
				relicSkeletonModel = new RelicSkeletonEntityModel(client.getLoadedEntityModels().getModelPart(RelicSkeletonEntityModel.LAYER));
				relicSkeletonTexture = relicSkeleton.getGemType().getTexture();
			} else {
				relicSkeletonModel = null;
				relicSkeletonTexture = null;
			}
		} else {
			relicSkeletonModel = null;
			relicSkeletonTexture = null;
		}
	}
}
