/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.mixin.util;

import moriyashiine.lostrelics.common.component.entity.PersistentCooldownComponent;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
	@Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/ItemCooldownManager;update()V"))
	private void lostrelics$persistentCooldown(CallbackInfo ci) {
		PersistentCooldownComponent.currentPlayer = (PlayerEntity) (Object) this;
	}
}
