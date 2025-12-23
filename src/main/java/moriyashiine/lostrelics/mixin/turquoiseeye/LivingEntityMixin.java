/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.mixin.turquoiseeye;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.lostrelics.common.init.ModItems;
import moriyashiine.lostrelics.common.util.LostRelicsUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.Monster;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@SuppressWarnings("ConstantValue")
	@ModifyReturnValue(method = "canHaveStatusEffect", at = @At("RETURN"))
	private boolean lostrelics$turquoiseEye(boolean original, StatusEffectInstance effect) {
		if (effect.getEffectType() == StatusEffects.INVISIBILITY && LostRelicsUtil.hasRelic((LivingEntity) (Object) this, ModItems.TURQUOISE_EYE)) {
			return false;
		}
		return original;
	}

	@ModifyReturnValue(method = "getAttackDistanceScalingFactor", at = @At("RETURN"))
	private double lostrelics$turquoiseEye(double original, @Nullable Entity entity) {
		if (entity == null || entity instanceof Monster) {
			return original * 2;
		}
		return original;
	}
}
