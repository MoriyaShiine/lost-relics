/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.mixin.cursedamulet;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.lostrelics.common.item.CursedAmuletItem;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.PathAwareEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EscapeDangerGoal.class)
public class EscapeDangerGoalMixin {
	@Shadow
	@Final
	protected PathAwareEntity mob;

	@ModifyReturnValue(method = "isInDanger", at = @At("RETURN"))
	private boolean lostrelics$cursedAmulet(boolean original) {
		return original || (mob.age % 5 == 0 && !(mob instanceof Angerable) && mob.getEntityWorld().getClosestPlayer(mob.getX(), mob.getY(), mob.getZ(), 8, CursedAmuletItem::doNegativesApply) != null);
	}
}
