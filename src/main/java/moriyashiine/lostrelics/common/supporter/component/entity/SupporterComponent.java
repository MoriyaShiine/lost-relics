/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.supporter.component.entity;

import moriyashiine.lostrelics.client.supporter.GemType;
import moriyashiine.lostrelics.client.supporter.SupporterOptions;
import moriyashiine.lostrelics.common.init.ModEntityComponents;
import moriyashiine.lostrelics.common.supporter.payload.SyncGemTypePayload;
import moriyashiine.strawberrylib.api.module.SLibClientUtils;
import moriyashiine.strawberrylib.impl.common.supporter.SupporterInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ClientTickingComponent;

public class SupporterComponent implements AutoSyncedComponent, ClientTickingComponent {
	private final PlayerEntity obj;
	private GemType gemType = GemType.DEFAULT;

	public SupporterComponent(PlayerEntity obj) {
		this.obj = obj;
	}

	@Override
	public void readData(ReadView readView) {
		gemType = readView.read("GemType", GemType.CODEC).orElse(GemType.DEFAULT);
	}

	@Override
	public void writeData(WriteView writeView) {
		writeView.put("GemType", GemType.CODEC, gemType);
	}

	@Override
	public void clientTick() {
		if (SLibClientUtils.isHost(obj) && SupporterInit.isSupporter(obj)) {
			if (getGemType() != SupporterOptions.GEM_TYPE.getValue()) {
				gemType = SupporterOptions.GEM_TYPE.getValue();
				SyncGemTypePayload.send(gemType);
			}
		}
	}

	public void sync() {
		ModEntityComponents.SUPPORTER.sync(obj);
	}

	public GemType getGemType() {
		return gemType;
	}

	public void setGemType(GemType gemType) {
		this.gemType = gemType;
	}
}
