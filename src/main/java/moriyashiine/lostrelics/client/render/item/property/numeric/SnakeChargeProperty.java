/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.client.render.item.property.numeric;

import com.mojang.serialization.MapCodec;
import moriyashiine.lostrelics.common.item.TripleToothedSnakeItem;
import net.minecraft.client.render.item.property.numeric.NumericProperty;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HeldItemContext;
import org.jspecify.annotations.Nullable;

public class SnakeChargeProperty implements NumericProperty {
	public static final MapCodec<SnakeChargeProperty> CODEC = MapCodec.unit(new SnakeChargeProperty());

	@Override
	public float getValue(ItemStack stack, @Nullable ClientWorld world, @Nullable HeldItemContext context, int seed) {
		return TripleToothedSnakeItem.getCharges(stack);
	}

	@Override
	public MapCodec<? extends NumericProperty> getCodec() {
		return CODEC;
	}
}
