package moriyashiine.lostrelics.common.world.entity.ai.goal;

import moriyashiine.lostrelics.common.world.entity.Doppelganger;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.pathfinder.PathType;
import org.jspecify.annotations.Nullable;

import java.util.EnumSet;

public class DoppelgangerFollowOwnerGoal extends Goal {
	private final Doppelganger doppelganger;
	@Nullable
	private LivingEntity owner;
	private final double speedModifier;
	private final PathNavigation navigation;
	private int timeToRecalcPath;
	private final float startDistance, stopDistance;
	private float oldWaterCost;

	public DoppelgangerFollowOwnerGoal(Doppelganger doppelganger, double speedModifier, float startDistance, float stopDistance) {
		this.doppelganger = doppelganger;
		this.speedModifier = speedModifier;
		navigation = doppelganger.getNavigation();
		this.startDistance = startDistance;
		this.stopDistance = stopDistance;
		setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}

	@Override
	public boolean canUse() {
		LivingEntity owner = doppelganger.getOwner();
		if (owner == null) {
			return false;
		} else if (doppelganger.cannotFollowOwner()) {
			return false;
		} else if (doppelganger.distanceToSqr(owner) < startDistance * startDistance) {
			return false;
		} else {
			this.owner = owner;
			return true;
		}
	}

	@Override
	public boolean canContinueToUse() {
		if (navigation.isDone()) {
			return false;
		} else {
			return !doppelganger.cannotFollowOwner() && !(doppelganger.distanceToSqr(owner) <= stopDistance * stopDistance);
		}
	}

	@Override
	public void start() {
		timeToRecalcPath = 0;
		oldWaterCost = doppelganger.getPathfindingMalus(PathType.WATER);
		doppelganger.setPathfindingMalus(PathType.WATER, 0);
	}

	@Override
	public void stop() {
		owner = null;
		navigation.stop();
		doppelganger.setPathfindingMalus(PathType.WATER, oldWaterCost);
	}

	@Override
	public void tick() {
		doppelganger.getLookControl().setLookAt(owner, 10, doppelganger.getMaxHeadXRot());
		if (--timeToRecalcPath <= 0) {
			timeToRecalcPath = adjustedTickDelay(10);
			navigation.moveTo(owner, speedModifier);
		}
	}
}
