/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.client.supporter;

import moriyashiine.lostrelics.common.LostRelics;
import net.minecraft.client.option.SimpleOption;

import java.util.Arrays;

public class SupporterOptions {
	private static final SimpleOption.PotentialValuesBasedCallbacks<GemType> GEM_TYPE_VALUES = new SimpleOption.PotentialValuesBasedCallbacks<>(
			Arrays.stream(GemType.values()).toList(),
			GemType.CODEC);

	public static final SimpleOption<GemType> GEM_TYPE = new SimpleOption<>("options." + LostRelics.MOD_ID + ".gemType", SimpleOption.emptyTooltip(), (optionText, value) -> value.getOptionsName(), GEM_TYPE_VALUES, GemType.DEFAULT, value -> {
	});
}
