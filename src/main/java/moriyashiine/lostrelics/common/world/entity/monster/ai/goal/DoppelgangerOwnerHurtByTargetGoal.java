/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.common.world.entity.monster.ai.goal;

import moriyashiine.lostrelics.common.world.entity.monster.Doppelganger;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

import java.util.EnumSet;

public class DoppelgangerOwnerHurtByTargetGoal extends TargetGoal {
	private final Doppelganger doppelganger;
	private LivingEntity ownerLastHurtBy;
	private int timestamp;

	public DoppelgangerOwnerHurtByTargetGoal(Doppelganger doppelganger) {
		super(doppelganger, false);
		this.doppelganger = doppelganger;
		setFlags(EnumSet.of(Goal.Flag.TARGET));
	}

	@Override
	public boolean canUse() {
		LivingEntity owner = doppelganger.getOwner();
		if (owner == null) {
			return false;
		} else {
			ownerLastHurtBy = owner.getLastHurtByMob();
			int ts = owner.getLastHurtByMobTimestamp();
			return ts != timestamp && canAttack(ownerLastHurtBy, TargetingConditions.DEFAULT);
		}
	}

	@Override
	public void start() {
		mob.setTarget(ownerLastHurtBy);
		LivingEntity owner = doppelganger.getOwner();
		if (owner != null) {
			timestamp = owner.getLastHurtByMobTimestamp();
		}
		super.start();
	}
}
