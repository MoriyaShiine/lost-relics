package moriyashiine.lostrelics.mixin.turquoiseeye;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.lostrelics.common.init.LostRelicsItems;
import moriyashiine.lostrelics.common.util.LostRelicsUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TargetingConditions.class)
public class TargetingConditionsMixin {
	@ModifyExpressionValue(method = "test", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/sensing/Sensing;hasLineOfSight(Lnet/minecraft/world/entity/Entity;)Z"))
	private boolean lostrelics$turquoiseEye(boolean original, @Local(argsOnly = true, ordinal = 1) LivingEntity target) {
		return original || LostRelicsUtil.hasRelic(target, LostRelicsItems.TURQUOISE_EYE);
	}
}
