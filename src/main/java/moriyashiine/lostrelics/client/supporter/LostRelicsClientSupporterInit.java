/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.client.supporter;

import moriyashiine.lostrelics.common.supporter.SupporterInit;
import moriyashiine.lostrelics.common.supporter.payload.SyncRelicSkeletonGemTypePayload;
import moriyashiine.lostrelics.common.world.entity.GemType;
import moriyashiine.strawberrylib.api.module.SLibSupporterUtils;
import net.minecraft.client.OptionInstance;

import java.util.Arrays;

public class LostRelicsClientSupporterInit {
	private static final OptionInstance.Enum<GemType> GEM_TYPE_VALUES = new OptionInstance.Enum<>(
			Arrays.stream(GemType.values()).toList(),
			GemType.CODEC);

	public static void init() {
		SLibSupporterUtils.registerOption(SupporterInit.RELIC_SKELETON_GEM_TYPE,
				(_, value) -> value.getOptionsName(), GEM_TYPE_VALUES, GemType.DEFAULT,
				GemType::valueOf, GemType::name, SyncRelicSkeletonGemTypePayload::send);
	}
}
