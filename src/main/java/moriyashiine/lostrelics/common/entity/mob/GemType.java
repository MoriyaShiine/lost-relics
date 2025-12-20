/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.entity.mob;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import moriyashiine.lostrelics.common.LostRelics;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;

import java.util.function.IntFunction;

public enum GemType implements StringIdentifiable {
	DEFAULT("default", null),
	ALEXANDRITE("alexandrite", LostRelics.id("textures/entity/relic_skeleton/alexandrite.png")),
	AMETHYST("amethyst", LostRelics.id("textures/entity/relic_skeleton/amethyst.png")),
	AQUAMARINE("aquamarine", LostRelics.id("textures/entity/relic_skeleton/aquamarine.png")),
	DIAMOND("diamond", LostRelics.id("textures/entity/relic_skeleton/diamond.png")),
	EMERALD("emerald", LostRelics.id("textures/entity/relic_skeleton/emerald.png")),
	GOLD("gold", LostRelics.id("textures/entity/relic_skeleton/gold.png")),
	JADE("jade", LostRelics.id("textures/entity/relic_skeleton/jade.png")),
	TURQUOISE("turquoise", LostRelics.id("textures/entity/relic_skeleton/turquoise.png"));

	private static final IntFunction<GemType> INDEX_MAPPER = ValueLists.createIndexToValueFunction(GemType::ordinal, values(), ValueLists.OutOfBoundsHandling.ZERO);
	public static final Codec<GemType> CODEC = StringIdentifiable.createCodec(GemType::values);
	public static final PacketCodec<ByteBuf, GemType> PACKET_CODEC = PacketCodecs.indexed(INDEX_MAPPER, GemType::ordinal);

	private final String name;
	private final Identifier texture;

	GemType(String name, Identifier texture) {
		this.name = name;
		this.texture = texture;
	}

	public String getName() {
		return name;
	}

	public Identifier getTexture() {
		return texture;
	}

	public Text getOptionsName() {
		return Text.translatable("gemtype.lostrelics." + getName());
	}

	@Override
	public String asString() {
		return getName();
	}
}
