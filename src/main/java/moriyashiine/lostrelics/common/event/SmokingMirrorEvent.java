/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.common.event;

import com.swacky.ohmega.api.common.item.AccessoryHelper;
import moriyashiine.lostrelics.common.init.LostRelicsItems;
import moriyashiine.lostrelics.common.init.LostRelicsSoundEvents;
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
	public static void init() {
		ModifyDamageTakenEvent.MULTIPLY_BASE.register(new ReduceDamage());
		AfterDamageIncludingDeathEvent.EVENT.register(new ReflectDamage());
	}

	private static class ReduceDamage implements ModifyDamageTakenEvent {
		@Override
		public float modify(Phase phase, LivingEntity victim, ServerLevel level, DamageSource source) {
			if (phase == Phase.BASE && source.getEntity() instanceof LivingEntity attacker && SLibUtils.shouldHurt(attacker, victim)) {
				ItemStack relic = AccessoryHelper.getStack(victim, LostRelicsItems.SMOKING_MIRROR);
				if (LostRelicsUtil.isUsable(victim, relic)) {
					return 0.5F;
				}
			}
			return 1;
		}
	}

	private static class ReflectDamage implements AfterDamageIncludingDeathEvent {
		@Override
		public void afterDamage(LivingEntity victim, DamageSource source, float originalDamage, float modifiedDamage, boolean blocked) {
			if (modifiedDamage > 0 && source.getEntity() instanceof LivingEntity attacker && SLibUtils.shouldHurt(attacker, victim)) {
				ItemStack relic = AccessoryHelper.getStack(victim, LostRelicsItems.SMOKING_MIRROR);
				if (LostRelicsUtil.isUsable(victim, relic)) {
					if (victim instanceof Player player) {
						LostRelicsUtil.setCooldown(player, relic, 60);
					}
					SLibUtils.playSound(victim, LostRelicsSoundEvents.ENTITY_GENERIC_SPAWN);
					victim.level().addFreshEntity(new SmokeBall(victim.level(), victim, attacker, modifiedDamage));
				}
			}
		}
	}
}
