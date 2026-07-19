package moriyashiine.lostrelics.client.renderer.item.properties.numeric;

import com.mojang.serialization.MapCodec;
import moriyashiine.lostrelics.common.world.item.TripleToothedSnakeItem;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public class SnakeChargeProperty implements RangeSelectItemModelProperty {
	public static final MapCodec<SnakeChargeProperty> CODEC = MapCodec.unit(new SnakeChargeProperty());

	@Override
	public float get(ItemStack stack, @Nullable ClientLevel level, @Nullable ItemOwner owner, int seed) {
		return TripleToothedSnakeItem.getCharges(stack);
	}

	@Override
	public MapCodec<SnakeChargeProperty> type() {
		return CODEC;
	}
}
