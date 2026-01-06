/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.mixin.turquoiseeye;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.Monster;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@ModifyReturnValue(method = "getAttackDistanceScalingFactor", at = @At("RETURN"))
	private double lostrelics$turquoiseEye(double original, @Nullable Entity entity) {
		if (entity == null || entity instanceof Monster) {
			return original * 2;
		}
		return original;
	}
}
