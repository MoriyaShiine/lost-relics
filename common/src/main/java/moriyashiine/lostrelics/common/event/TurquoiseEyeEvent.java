package moriyashiine.lostrelics.common.event;

import moriyashiine.lostrelics.common.init.LostRelicsItems;
import moriyashiine.lostrelics.common.util.LostRelicsUtil;
import moriyashiine.strawberrylib.api.event.ModifyCriticalStatusEvent;
import net.fabricmc.fabric.api.entity.event.v1.effect.EffectEventContext;
import net.fabricmc.fabric.api.entity.event.v1.effect.ServerMobEffectEvents;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class TurquoiseEyeEvent {
	public static void init() {
		ModifyCriticalStatusEvent.EVENT.register(new Attack());
		ServerMobEffectEvents.ALLOW_ADD.register(new EffectImmunity());
	}

	private static class Attack implements ModifyCriticalStatusEvent {
		@Override
		public TriState isCritical(Player attacker, Entity target, float attackCooldownProgress) {
			if (target instanceof LivingEntity living && living.getHealth() == living.getMaxHealth()) {
				ItemStack relic = LostRelicsUtil.getRelic(attacker, LostRelicsItems.TURQUOISE_EYE);
				if (LostRelicsUtil.isUsable(attacker, relic)) {
					if (!attacker.level().isClientSide()) {
						LostRelicsUtil.setCooldown(attacker, relic, 60);
						living.addEffect(new MobEffectInstance(MobEffects.MINING_FATIGUE, 100, 1));
						living.addEffect(new MobEffectInstance(MobEffects.WEAKNESS));
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

	private static class EffectImmunity implements ServerMobEffectEvents.AllowAdd {
		@Override
		public boolean allowAdd(MobEffectInstance effect, LivingEntity entity, EffectEventContext ctx) {
			return !(effect.getEffect() == MobEffects.INVISIBILITY && LostRelicsUtil.hasRelic(entity, LostRelicsItems.TURQUOISE_EYE));
		}
	}
}
