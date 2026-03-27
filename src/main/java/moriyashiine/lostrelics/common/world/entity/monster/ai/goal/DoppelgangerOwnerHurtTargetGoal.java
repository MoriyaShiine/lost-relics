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

public class DoppelgangerOwnerHurtTargetGoal extends TargetGoal {
	private final Doppelganger doppelganger;
	private LivingEntity ownerLastHurt;
	private int timestamp;

	public DoppelgangerOwnerHurtTargetGoal(Doppelganger doppelganger) {
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
			ownerLastHurt = owner.getLastHurtMob();
			int ts = owner.getLastHurtMobTimestamp();
			return ts != timestamp && canAttack(ownerLastHurt, TargetingConditions.DEFAULT);
		}
	}

	@Override
	public void start() {
		mob.setTarget(ownerLastHurt);
		LivingEntity owner = doppelganger.getOwner();
		if (owner != null) {
			timestamp = owner.getLastHurtMobTimestamp();
		}
		super.start();
	}
}
