/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.common.supporter;

import moriyashiine.lostrelics.common.LostRelics;
import moriyashiine.lostrelics.common.world.entity.GemType;
import moriyashiine.strawberrylib.api.module.SLibSupporterUtils;
import moriyashiine.strawberrylib.api.supporter.objects.SupporterDataType;

public class SupporterInit {
	public static final SupporterDataType<GemType> RELIC_SKELETON_GEM_TYPE = SLibSupporterUtils.registerDataType(LostRelics.id("relic_skeleton_gem_type"), GemType.CODEC, GemType.STREAM_CODEC, GemType.DEFAULT);

	public static void init() {
	}
}
