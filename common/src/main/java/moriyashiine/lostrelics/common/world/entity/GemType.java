package moriyashiine.lostrelics.common.world.entity;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import moriyashiine.lostrelics.common.LostRelics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;

import java.util.function.IntFunction;

public enum GemType implements StringRepresentable {
	DEFAULT("default", null),
	ALEXANDRITE("alexandrite", LostRelics.id("textures/entity/relic_skeleton/alexandrite.png")),
	AMETHYST("amethyst", LostRelics.id("textures/entity/relic_skeleton/amethyst.png")),
	AQUAMARINE("aquamarine", LostRelics.id("textures/entity/relic_skeleton/aquamarine.png")),
	DIAMOND("diamond", LostRelics.id("textures/entity/relic_skeleton/diamond.png")),
	EMERALD("emerald", LostRelics.id("textures/entity/relic_skeleton/emerald.png")),
	GOLD("gold", LostRelics.id("textures/entity/relic_skeleton/gold.png")),
	JADE("jade", LostRelics.id("textures/entity/relic_skeleton/jade.png")),
	TURQUOISE("turquoise", LostRelics.id("textures/entity/relic_skeleton/turquoise.png"));

	private static final IntFunction<GemType> INDEX_MAPPER = ByIdMap.continuous(GemType::ordinal, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
	public static final Codec<GemType> CODEC = StringRepresentable.fromEnum(GemType::values);
	public static final StreamCodec<ByteBuf, GemType> STREAM_CODEC = ByteBufCodecs.idMapper(INDEX_MAPPER, GemType::ordinal);

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

	public Component getOptionsName() {
		return Component.translatable("gemtype.lost_relics." + getName());
	}

	@Override
	public String getSerializedName() {
		return getName();
	}
}
