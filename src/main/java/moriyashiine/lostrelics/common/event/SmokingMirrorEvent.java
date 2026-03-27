/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.common.event;

import moriyashiine.lostrelics.common.init.ModItems;
import moriyashiine.lostrelics.common.init.ModSoundEvents;
import moriyashiine.lostrelics.common.util.LostRelicsUtil;
import moriyashiine.lostrelics.common.world.entity.projectile.SmokeBall;
import moriyashiine.strawberrylib.api.event.AfterDamageIncludingDeathEvent;
import moriyashiine.strawberrylib.api.event.ModifyDamageTakenEvent;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class SmokingMirrorEvent {
	public static class ReduceDamage implements ModifyDamageTakenEvent {
		@Override
		public float modify(Phase phase, LivingEntity victim, ServerLevel level, DamageSource source) {
			if (phase == Phase.BASE && source.getEntity() instanceof LivingEntity attacker && SLibUtils.shouldHurt(attacker, victim)) {
				ItemStack relicStack = LostRelicsUtil.getRelic(victim, ModItems.SMOKING_MIRROR);
				if (LostRelicsUtil.isUsable(victim, relicStack)) {
					return 0.5F;
				}
			}
			return 1;
		}
	}

	public static class ReflectDamage implements AfterDamageIncludingDeathEvent {
		@Override
		public void afterDamage(LivingEntity victim, DamageSource source, float originalDamage, float modifiedDamage, boolean blocked) {
			if (modifiedDamage > 0 && source.getEntity() instanceof LivingEntity attacker && SLibUtils.shouldHurt(attacker, victim)) {
				ItemStack relicStack = LostRelicsUtil.getRelic(victim, ModItems.SMOKING_MIRROR);
				if (LostRelicsUtil.isUsable(victim, relicStack)) {
					if (victim instanceof Player player) {
						LostRelicsUtil.setCooldown(player, relicStack, 60);
					}
					SLibUtils.playSound(victim, ModSoundEvents.ENTITY_GENERIC_SPAWN);
					victim.level().addFreshEntity(new SmokeBall(victim.level(), victim, attacker, modifiedDamage));
				}
			}
		}
	}
}
