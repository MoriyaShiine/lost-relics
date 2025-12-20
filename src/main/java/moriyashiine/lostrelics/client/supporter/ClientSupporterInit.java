/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.client.supporter;

import moriyashiine.lostrelics.common.supporter.SupporterInit;
import moriyashiine.lostrelics.common.supporter.payload.SyncRelicSkeletonGemTypePayload;
import moriyashiine.strawberrylib.api.module.SLibSupporterUtils;
import net.minecraft.client.option.SimpleOption;

import java.util.Arrays;

public class ClientSupporterInit {
	private static final SimpleOption.PotentialValuesBasedCallbacks<GemType> GEM_TYPE_VALUES = new SimpleOption.PotentialValuesBasedCallbacks<>(
			Arrays.stream(GemType.values()).toList(),
			GemType.CODEC);

	public static void init() {
		SLibSupporterUtils.registerOption(SupporterInit.RELIC_SKELETON_GEM_TYPE,
				(optionText, value) -> value.getOptionsName(), GEM_TYPE_VALUES, GemType.DEFAULT,
				GemType::valueOf, GemType::name, SyncRelicSkeletonGemTypePayload::send);
	}
}
