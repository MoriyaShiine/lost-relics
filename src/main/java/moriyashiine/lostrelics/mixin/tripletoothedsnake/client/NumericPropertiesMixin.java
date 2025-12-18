/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.mixin.tripletoothedsnake.client;

import com.mojang.serialization.MapCodec;
import moriyashiine.lostrelics.client.render.item.property.numeric.SnakeChargeProperty;
import moriyashiine.lostrelics.common.LostRelics;
import net.minecraft.client.render.item.property.numeric.NumericProperties;
import net.minecraft.client.render.item.property.numeric.NumericProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NumericProperties.class)
public class NumericPropertiesMixin {
	@Shadow
	@Final
	public static Codecs.IdMapper<Identifier, MapCodec<? extends NumericProperty>> ID_MAPPER;

	@Inject(method = "bootstrap", at = @At("TAIL"))
	private static void lostrelics$tripleToothedSnake(CallbackInfo ci) {
		ID_MAPPER.put(LostRelics.id("snake_charge"), SnakeChargeProperty.CODEC);
	}
}
