/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.mixin.tripletoothedsnake;

import moriyashiine.lostrelics.common.init.ModItems;
import moriyashiine.lostrelics.common.util.LostRelicsUtil;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@Inject(method = "heal", at = @At("HEAD"), cancellable = true)
	private void lostrelics$tripleToothedSnake(float amount, CallbackInfo ci) {
		if (!LostRelicsUtil.isUsable((LivingEntity) (Object) this, ModItems.TRIPLE_TOOTHED_SNAKE.getDefaultStack())) {
			ci.cancel();
		}
	}
}
