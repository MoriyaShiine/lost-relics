package moriyashiine.lostrelics.mixin.cursedamulet;

import moriyashiine.lostrelics.common.world.item.CursedAmuletItem;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.golem.IronGolem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(IronGolem.class)
public class IronGolemMixin {
	@ModifyArg(method = "registerGoals", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/goal/target/NearestAttackableTargetGoal;<init>(Lnet/minecraft/world/entity/Mob;Ljava/lang/Class;IZZLnet/minecraft/world/entity/ai/targeting/TargetingConditions$Selector;)V", ordinal = 0))
	private TargetingConditions.Selector lostrelics$cursedAmulet(TargetingConditions.Selector selector) {
		return (target, world) -> selector.test(target, world) || CursedAmuletItem.doNegativesApply(target);
	}
}
