/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.supporter.payload;

import moriyashiine.lostrelics.client.supporter.GemType;
import moriyashiine.lostrelics.common.LostRelics;
import moriyashiine.lostrelics.common.supporter.SupporterInit;
import moriyashiine.strawberrylib.api.module.SLibSupporterUtils;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record SyncRelicSkeletonGemTypePayload(GemType gemType) implements CustomPayload {
	public static final Id<SyncRelicSkeletonGemTypePayload> ID = new Id<>(LostRelics.id("sync_relic_skeleton_gem_type"));
	public static final PacketCodec<PacketByteBuf, SyncRelicSkeletonGemTypePayload> CODEC = PacketCodec.tuple(
			GemType.PACKET_CODEC, SyncRelicSkeletonGemTypePayload::gemType,
			SyncRelicSkeletonGemTypePayload::new
	);

	@Override
	public Id<? extends CustomPayload> getId() {
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
