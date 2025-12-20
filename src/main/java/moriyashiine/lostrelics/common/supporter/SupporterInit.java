/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.supporter;

import moriyashiine.lostrelics.common.LostRelics;
import moriyashiine.lostrelics.common.entity.mob.GemType;
import moriyashiine.lostrelics.common.supporter.payload.SyncRelicSkeletonGemTypePayload;
import moriyashiine.strawberrylib.api.module.SLibSupporterUtils;
import moriyashiine.strawberrylib.api.supporter.objects.SupporterDataKey;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class SupporterInit {
	public static final SupporterDataKey<GemType> RELIC_SKELETON_GEM_TYPE = SLibSupporterUtils.registerData(LostRelics.id("relic_skeleton_gem_type"), GemType.CODEC, GemType.DEFAULT);

	public static void init() {
		initPayloads();
	}

	private static void initPayloads() {
		// server payloads
		PayloadTypeRegistry.playC2S().register(SyncRelicSkeletonGemTypePayload.ID, SyncRelicSkeletonGemTypePayload.CODEC);
		// server receivers
		ServerPlayNetworking.registerGlobalReceiver(SyncRelicSkeletonGemTypePayload.ID, new SyncRelicSkeletonGemTypePayload.Receiver());
	}
}
