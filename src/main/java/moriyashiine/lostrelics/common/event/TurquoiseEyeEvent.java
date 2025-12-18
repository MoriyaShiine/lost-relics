/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.event;

import moriyashiine.lostrelics.common.init.ModItems;
import moriyashiine.lostrelics.common.util.LostRelicsUtil;
import moriyashiine.strawberrylib.api.event.ModifyDamageTakenEvent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;

public class TurquoiseEyeEvent implements ModifyDamageTakenEvent {
	@Override
	public float modify(Phase phase, float amount, ServerWorld world, DamageSource source, LivingEntity victim) {
		if (phase == Phase.FINAL && victim.getHealth() == victim.getMaxHealth() && source.getAttacker() instanceof LivingEntity attacker) {
			ItemStack turquoiseEyeStack = LostRelicsUtil.getRelic(attacker, ModItems.TURQUOISE_EYE);
			if (LostRelicsUtil.isUsable(attacker, turquoiseEyeStack)) {
				if (attacker instanceof PlayerEntity player) {
					LostRelicsUtil.setCooldown(player, turquoiseEyeStack, 600);
				}
				victim.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 100, 1));
				victim.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS));
				return 1.5F;
			}
		}
		return 1;
	}
}
