/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.event;

import moriyashiine.lostrelics.common.entity.projectile.SmokeBallEntity;
import moriyashiine.lostrelics.common.init.ModItems;
import moriyashiine.lostrelics.common.init.ModSoundEvents;
import moriyashiine.lostrelics.common.util.LostRelicsUtil;
import moriyashiine.strawberrylib.api.event.ModifyDamageTakenEvent;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;

public class SmokingMirrorEvent implements ModifyDamageTakenEvent {
	@Override
	public float modify(Phase phase, float amount, ServerWorld world, DamageSource source, LivingEntity victim) {
		if (phase == Phase.BASE && amount > 0 && source.getAttacker() instanceof LivingEntity attacker && SLibUtils.shouldHurt(attacker, victim)) {
			ItemStack relicStack = LostRelicsUtil.getRelic(victim, ModItems.SMOKING_MIRROR);
			if (LostRelicsUtil.isUsable(victim, relicStack)) {
				if (victim instanceof PlayerEntity player) {
					LostRelicsUtil.setCooldown(player, relicStack, 60);
				}
				SLibUtils.playSound(victim, ModSoundEvents.ENTITY_GENERIC_SPAWN);
				world.spawnEntity(new SmokeBallEntity(world, victim, attacker, amount * 0.5F));
				return 0.5F;
			}
		}
		return 1;
	}
}
