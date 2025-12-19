/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.supporter.payload;

import moriyashiine.lostrelics.client.supporter.GemType;
import moriyashiine.lostrelics.common.LostRelics;
import moriyashiine.lostrelics.common.init.ModEntityComponents;
import moriyashiine.lostrelics.common.supporter.component.entity.SupporterComponent;
import moriyashiine.strawberrylib.impl.common.supporter.SupporterInit;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record SyncGemTypePayload(GemType gemType) implements CustomPayload {
	public static final Id<SyncGemTypePayload> ID = new Id<>(LostRelics.id("sync_gem_type"));
	public static final PacketCodec<PacketByteBuf, SyncGemTypePayload> CODEC = PacketCodec.tuple(
			GemType.PACKET_CODEC, SyncGemTypePayload::gemType,
			SyncGemTypePayload::new
	);

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}

	public static void send(GemType gemType) {
		ClientPlayNetworking.send(new SyncGemTypePayload(gemType));
	}

	public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<SyncGemTypePayload> {
		@Override
		public void receive(SyncGemTypePayload payload, ServerPlayNetworking.Context context) {
			if (SupporterInit.isSupporter(context.player())) {
				SupporterComponent supporterComponent = ModEntityComponents.SUPPORTER.get(context.player());
				supporterComponent.setGemType(payload.gemType());
				supporterComponent.sync();
			}
		}
	}
}
