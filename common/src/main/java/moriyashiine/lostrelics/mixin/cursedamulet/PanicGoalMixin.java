package moriyashiine.lostrelics.mixin.cursedamulet;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.lostrelics.common.world.item.CursedAmuletItem;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PanicGoal.class)
public class PanicGoalMixin {
	@Shadow
	@Final
	protected PathfinderMob mob;

	@ModifyReturnValue(method = "shouldPanic", at = @At("RETURN"))
	private boolean lostrelics$cursedAmulet(boolean original) {
		return original || (mob.tickCount % 5 == 0 && !(mob instanceof NeutralMob) && mob.level().getNearestPlayer(mob.getX(), mob.getY(), mob.getZ(), 8, CursedAmuletItem::doNegativesApply) != null);
	}
}
