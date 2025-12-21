/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.entity.mob.ai.goal;

import moriyashiine.lostrelics.common.entity.mob.DoppelgangerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import org.jspecify.annotations.Nullable;

import java.util.EnumSet;

public class DoppelgangerFollowOwnerGoal extends Goal {
	private final DoppelgangerEntity doppelganger;
	@Nullable
	private LivingEntity owner;
	private final double speed;
	private final EntityNavigation navigation;
	private int updateCountdownTicks;
	private final float minDistance, maxDistance;
	private float waterPenalty;

	public DoppelgangerFollowOwnerGoal(DoppelgangerEntity doppelganger, double speed, float minDistance, float maxDistance) {
		this.doppelganger = doppelganger;
		this.speed = speed;
		navigation = doppelganger.getNavigation();
		this.minDistance = minDistance;
		this.maxDistance = maxDistance;
		setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
	}

	@Override
	public boolean canStart() {
		LivingEntity owner = doppelganger.getOwner();
		if (owner == null) {
			return false;
		} else if (doppelganger.cannotFollowOwner()) {
			return false;
		} else if (doppelganger.squaredDistanceTo(owner) < minDistance * minDistance) {
			return false;
		} else {
			this.owner = owner;
			return true;
		}
	}

	@Override
	public boolean shouldContinue() {
		if (navigation.isIdle()) {
			return false;
		} else {
			return !doppelganger.cannotFollowOwner() && !(doppelganger.squaredDistanceTo(owner) <= maxDistance * maxDistance);
		}
	}

	@Override
	public void start() {
		updateCountdownTicks = 0;
		waterPenalty = doppelganger.getPathfindingPenalty(PathNodeType.WATER);
		doppelganger.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
	}

	@Override
	public void stop() {
		owner = null;
		navigation.stop();
		doppelganger.setPathfindingPenalty(PathNodeType.WATER, waterPenalty);
	}

	@Override
	public void tick() {
		doppelganger.getLookControl().lookAt(owner, 10.0F, doppelganger.getMaxLookPitchChange());
		if (--updateCountdownTicks <= 0) {
			updateCountdownTicks = getTickCount(10);
			navigation.startMovingTo(owner, speed);
		}
	}
}
