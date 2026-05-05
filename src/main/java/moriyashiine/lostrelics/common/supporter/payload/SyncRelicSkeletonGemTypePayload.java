/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.common.supporter.payload;

import moriyashiine.lostrelics.common.LostRelics;
import moriyashiine.lostrelics.common.supporter.SupporterInit;
import moriyashiine.lostrelics.common.world.entity.GemType;
import moriyashiine.strawberrylib.api.module.SLibSupporterUtils;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record SyncRelicSkeletonGemTypePayload(GemType gemType) implements CustomPacketPayload {
	public static final Type<SyncRelicSkeletonGemTypePayload> ID = new Type<>(LostRelics.id("sync_relic_skeleton_gem_type"));
	public static final StreamCodec<FriendlyByteBuf, SyncRelicSkeletonGemTypePayload> CODEC = StreamCodec.composite(
			GemType.PACKET_CODEC, SyncRelicSkeletonGemTypePayload::gemType,
			SyncRelicSkeletonGemTypePayload::new
	);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}

	public static void send(GemType gemType) {
		ClientPlayNetworking.send(new SyncRelicSkeletonGemTypePayload(gemType));
	}

	public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<SyncRelicSkeletonGemTypePayload> {
		@Override
		public void receive(SyncRelicSkeletonGemTypePayload payload, ServerPlayNetworking.Context context) {
			if (SLibSupporterUtils.isSupporter(context.player())) {
				SLibSupporterUtils.setData(context.player(), SupporterInit.RELIC_SKELETON_GEM_TYPE, payload.gemType());
			}
		}
	}
}
