/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.mixin.turquoiseeye;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@ModifyReturnValue(method = "getVisibilityPercent", at = @At("RETURN"))
	private double lostrelics$turquoiseEye(double original, @Nullable Entity targetingEntity) {
		if (targetingEntity == null || targetingEntity instanceof Enemy) {
			return original * 2;
		}
		return original;
	}
}
