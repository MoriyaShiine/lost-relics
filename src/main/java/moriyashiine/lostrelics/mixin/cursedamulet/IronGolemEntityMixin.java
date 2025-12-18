/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.mixin.cursedamulet;

import moriyashiine.lostrelics.common.item.CursedAmuletItem;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.passive.IronGolemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(IronGolemEntity.class)
public class IronGolemEntityMixin {
	@ModifyArg(method = "initGoals", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/goal/ActiveTargetGoal;<init>(Lnet/minecraft/entity/mob/MobEntity;Ljava/lang/Class;IZZLnet/minecraft/entity/ai/TargetPredicate$EntityPredicate;)V", ordinal = 0))
	private TargetPredicate.EntityPredicate lostrelics$cursedAmulet(TargetPredicate.EntityPredicate value) {
		return (target, world) -> value.test(target, world) || CursedAmuletItem.doNegativesApply(target);
	}
}
