/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.mixin.util;

import moriyashiine.lostrelics.common.component.entity.PersistentCooldownComponent;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin {
	@Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemCooldowns;tick()V"))
	private void lostrelics$persistentCooldown(CallbackInfo ci) {
		PersistentCooldownComponent.currentPlayer = (Player) (Object) this;
	}
}
