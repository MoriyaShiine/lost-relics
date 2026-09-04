package moriyashiine.lostrelics.mixin.turquoiseeye;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.lostrelics.common.init.LostRelicsItems;
import moriyashiine.lostrelics.common.util.LostRelicsUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TargetGoal.class)
public class TargetGoalMixin {
	@Definition(id = "mustSee", field = "Lnet/minecraft/world/entity/ai/goal/target/TargetGoal;mustSee:Z")
	@Expression("this.mustSee")
	@ModifyExpressionValue(method = "canContinueToUse", at = @At(value = "MIXINEXTRAS:EXPRESSION"))
	private boolean lostrelics$turquoiseEye(boolean original, @Local(name = "target") LivingEntity target) {
		return original && !LostRelicsUtil.hasRelic(target, LostRelicsItems.TURQUOISE_EYE);
	}
}
