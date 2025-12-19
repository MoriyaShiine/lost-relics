/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.mixin.supporter.client;

import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.lostrelics.client.supporter.GemType;
import moriyashiine.lostrelics.client.supporter.SupporterOptions;
import moriyashiine.lostrelics.common.LostRelics;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.PrintWriter;

@Mixin(GameOptions.class)
public class GameOptionsMixin {
	@Unique
	private static final String GEM_TYPE_KEY = LostRelics.MOD_ID + ".gemType";

	@Shadow
	protected MinecraftClient client;

	@Inject(method = "load", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/KeyBinding;updateKeysByCode()V"))
	private void lostrelics$supporter(CallbackInfo ci, @Local(ordinal = 1) NbtCompound compound) {
		SupporterOptions.GEM_TYPE.setValue(GemType.valueOf(compound.getString(GEM_TYPE_KEY, GemType.DEFAULT.name())));
	}

	@Inject(method = "write", at = @At(value = "INVOKE", target = "Ljava/io/PrintWriter;close()V"))
	private void lostrelics$supporter(CallbackInfo ci, @Local PrintWriter printWriter) {
		write(printWriter, GEM_TYPE_KEY, SupporterOptions.GEM_TYPE.getValue().name());
	}

	@Unique
	private static void write(PrintWriter printWriter, String key, Object value) {
		printWriter.println(key + ":" + value);
	}
}
