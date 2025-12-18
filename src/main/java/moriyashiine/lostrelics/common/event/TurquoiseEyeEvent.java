/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.event;

import moriyashiine.lostrelics.common.init.ModItems;
import moriyashiine.lostrelics.common.util.LostRelicsUtil;
import moriyashiine.strawberrylib.api.event.ModifyCriticalStatusEvent;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class TurquoiseEyeEvent implements ModifyCriticalStatusEvent {
	@Override
	public TriState isCritical(PlayerEntity attacker, Entity target, float attackCooldownProgress) {
		if (target instanceof LivingEntity living && living.getHealth() == living.getMaxHealth()) {
			ItemStack turquoiseEyeStack = LostRelicsUtil.getRelic(attacker, ModItems.TURQUOISE_EYE);
			if (LostRelicsUtil.isUsable(attacker, turquoiseEyeStack)) {
				LostRelicsUtil.setCooldown(attacker, turquoiseEyeStack, 600);
				living.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 100, 1));
				living.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS));
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
