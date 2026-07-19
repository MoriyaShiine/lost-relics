package moriyashiine.lostrelics.common.world.entity.ai.goal;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class DoppelgangerMeleeAttackGoal extends MeleeAttackGoal {
	private final DoppelgangerRangedAttackGoal rangedAttackGoal;

	public DoppelgangerMeleeAttackGoal(PathfinderMob mob, double speedModifier, boolean followingTargetEvenIfNotSeen, DoppelgangerRangedAttackGoal rangedAttackGoal) {
		super(mob, speedModifier, followingTargetEvenIfNotSeen);
		this.rangedAttackGoal = rangedAttackGoal;
	}

	@Override
	public boolean canUse() {
		return !rangedAttackGoal.canUse() && super.canUse();
	}
}
