/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.entity.mob.ai.goal;

import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.PathAwareEntity;

public class DoppelgangerMeleeAttackGoal extends MeleeAttackGoal {
	private final DoppelgangerRangedAttackGoal rangedAttackGoal;

	public DoppelgangerMeleeAttackGoal(PathAwareEntity mob, double speed, boolean pauseWhenMobIdle, DoppelgangerRangedAttackGoal rangedAttackGoal) {
		super(mob, speed, pauseWhenMobIdle);
		this.rangedAttackGoal = rangedAttackGoal;
	}

	@Override
	public boolean canStart() {
		return !rangedAttackGoal.canStart() && super.canStart();
	}
}
