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

public class DoppelgangerTrackOwnerAttackerGoal extends TrackTargetGoal {
	private final DoppelgangerEntity doppelganger;
	private LivingEntity attacker;
	private int lastAttackedTime;

	public DoppelgangerTrackOwnerAttackerGoal(DoppelgangerEntity doppelganger) {
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
			attacker = owner.getAttacker();
			int time = owner.getLastAttackedTime();
			return time != lastAttackedTime && canTrack(attacker, TargetPredicate.DEFAULT);
		}
	}

	@Override
	public void start() {
		mob.setTarget(attacker);
		LivingEntity owner = doppelganger.getOwner();
		if (owner != null) {
			lastAttackedTime = owner.getLastAttackedTime();
		}
		super.start();
	}
}
