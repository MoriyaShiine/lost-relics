/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.event;

import moriyashiine.lostrelics.common.init.ModItems;
import moriyashiine.lostrelics.common.util.LostRelicsUtil;
import moriyashiine.strawberrylib.api.event.ModifyCriticalStatusEvent;
import net.fabricmc.fabric.api.entity.event.v1.effect.EffectEventContext;
import net.fabricmc.fabric.api.entity.event.v1.effect.ServerMobEffectEvents;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class TurquoiseEyeEvent {
	public static class Attack implements ModifyCriticalStatusEvent {
		@Override
		public TriState isCritical(PlayerEntity attacker, Entity target, float attackCooldownProgress) {
			if (target instanceof LivingEntity living && living.getHealth() == living.getMaxHealth()) {
				ItemStack relicStack = LostRelicsUtil.getRelic(attacker, ModItems.TURQUOISE_EYE);
				if (LostRelicsUtil.isUsable(attacker, relicStack)) {
					if (!attacker.getEntityWorld().isClient()) {
						LostRelicsUtil.setCooldown(attacker, relicStack, 60);
						living.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 100, 1));
						living.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS));
					}
					return TriState.TRUE;
				}
			}
			return TriState.DEFAULT;
		}

		@Override
		public int getPriority() {
			return 1001;
		}
	}

	public static class EffectImmunity implements ServerMobEffectEvents.AllowAdd {
		@SuppressWarnings("RedundantIfStatement")
		@Override
		public boolean allowAdd(StatusEffectInstance effect, LivingEntity entity, EffectEventContext ctx) {
			if (effect.getEffectType() == StatusEffects.INVISIBILITY && LostRelicsUtil.hasRelic(entity, ModItems.TURQUOISE_EYE)) {
				return false;
			}
			return true;
		}
	}
}
