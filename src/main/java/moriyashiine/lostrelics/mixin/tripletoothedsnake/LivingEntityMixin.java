/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.mixin.tripletoothedsnake;

import moriyashiine.lostrelics.common.init.ModItems;
import moriyashiine.lostrelics.common.util.LostRelicsUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@Inject(method = "heal", at = @At("HEAD"), cancellable = true)
	private void lostrelics$tripleToothedSnake(float amount, CallbackInfo ci) {
		LivingEntity living = (LivingEntity) (Object) this;
		ItemStack tripleToothedSnakeStack = LostRelicsUtil.getRelic(living, ModItems.TRIPLE_TOOTHED_SNAKE);
		if (!tripleToothedSnakeStack.isEmpty() && !LostRelicsUtil.isUsable(living, tripleToothedSnakeStack)) {
			ci.cancel();
		}
	}
}
