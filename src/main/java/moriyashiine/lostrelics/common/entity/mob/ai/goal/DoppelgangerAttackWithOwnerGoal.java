/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.entity.mob.ai.goal;

import moriyashiine.lostrelics.common.entity.mob.DoppelgangerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;

import java.util.EnumSet;

public class DoppelgangerAttackWithOwnerGoal extends TrackTargetGoal {
	private final DoppelgangerEntity doppelganger;
	private LivingEntity attacking;
	private int lastAttackTime;

	public DoppelgangerAttackWithOwnerGoal(DoppelgangerEntity doppelganger) {
		super(doppelganger, false);
		this.doppelganger = doppelganger;
		setControls(EnumSet.of(Goal.Control.TARGET));
	}

	@Override
	public boolean canStart() {
		LivingEntity owner = doppelganger.getOwner();
		if (owner == null) {
			return false;
		} else {
			attacking = owner.getAttacking();
			int time = owner.getLastAttackTime();
			return time != lastAttackTime && canTrack(attacking, TargetPredicate.DEFAULT);
		}
	}

	@Override
	public void start() {
		mob.setTarget(attacking);
		LivingEntity owner = doppelganger.getOwner();
		if (owner != null) {
			lastAttackTime = owner.getLastAttackTime();
		}
		super.start();
	}
}
