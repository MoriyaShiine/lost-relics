/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.mixin.util;

import com.llamalad7.mixinextras.sugar.Local;
import com.swacky.ohmega.api.AccessoryHelper;
import moriyashiine.lostrelics.common.item.EquippableRelicItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = AccessoryHelper.class, remap = false)
public class AccessoryHelperMixin {
	@Inject(method = "tryEquip", at = @At(value = "INVOKE", target = "Lcom/swacky/ohmega/api/AccessoryHelper;getBoundAccessory(Lnet/minecraft/item/Item;)Lcom/swacky/ohmega/api/IAccessory;"), cancellable = true)
	private static void lostrelics$preventEasyEquip(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir, @Local Item item) {
		if (item instanceof EquippableRelicItem) {
			cir.setReturnValue(ActionResult.PASS);
		}
	}
}
