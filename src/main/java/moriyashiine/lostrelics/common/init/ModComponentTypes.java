/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.init;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerComponentType;

public class ModComponentTypes {
	public static final ComponentType<Boolean> SHOW_SKELETON = registerComponentType("show_skeleton", new ComponentType.Builder<Boolean>().codec(Codec.BOOL).packetCodec(PacketCodecs.BOOLEAN));
	public static final ComponentType<Integer> SNAKE_CHARGE = registerComponentType("snake_charge", new ComponentType.Builder<Integer>().codec(Codec.INT).packetCodec(PacketCodecs.INTEGER));
	public static final ComponentType<Boolean> TAINTED_POTION = registerComponentType("tainted_potion", new ComponentType.Builder<Boolean>().codec(Codec.BOOL).packetCodec(PacketCodecs.BOOLEAN));

	public static void init() {
	}
}
