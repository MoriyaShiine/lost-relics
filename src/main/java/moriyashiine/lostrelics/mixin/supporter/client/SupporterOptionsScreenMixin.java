/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.mixin.supporter.client;

import moriyashiine.lostrelics.client.supporter.SupporterOptions;
import moriyashiine.strawberrylib.impl.client.supporter.gui.screen.option.SupporterOptionsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = SupporterOptionsScreen.class, remap = false)
public abstract class SupporterOptionsScreenMixin extends GameOptionsScreen {
	public SupporterOptionsScreenMixin(Screen parent, GameOptions gameOptions, Text title) {
		super(parent, gameOptions, title);
	}

	@Inject(method = "addOptions", at = @At("TAIL"))
	private void lostrelics$supporter(CallbackInfo ci) {
		body.addSingleOptionEntry(SupporterOptions.GEM_TYPE);
	}
}
