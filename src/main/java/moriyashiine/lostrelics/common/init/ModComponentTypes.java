/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.common.init;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerComponentType;

public class ModComponentTypes {
	public static final DataComponentType<Boolean> RELIC_TOGGLE = registerComponentType("relic_toggle", new DataComponentType.Builder<Boolean>().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));

	public static final DataComponentType<Integer> SNAKE_CHARGE = registerComponentType("snake_charge", new DataComponentType.Builder<Integer>().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT));
	public static final DataComponentType<Boolean> TAINTED_POTION = registerComponentType("tainted_potion", new DataComponentType.Builder<Boolean>().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));

	public static void init() {
	}
}
