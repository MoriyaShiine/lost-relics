/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.event.relic;

import moriyashiine.lostrelics.common.init.ModItems;
import moriyashiine.lostrelics.common.item.CursedAmuletItem;
import moriyashiine.lostrelics.common.util.LostRelicsUtil;
import moriyashiine.strawberrylib.api.event.ModifyDamageTakenEvent;
import moriyashiine.strawberrylib.api.event.PreventHostileTargetingEvent;
import net.fabricmc.fabric.api.entity.event.v1.effect.EffectEventContext;
import net.fabricmc.fabric.api.entity.event.v1.effect.ServerMobEffectEvents;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalEntityTypeTags;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;

public class CursedAmuletEvent {
	public static class EffectImmunity implements ServerMobEffectEvents.AllowAdd {
		@Override
		public boolean allowAdd(StatusEffectInstance effect, LivingEntity entity, EffectEventContext ctx) {
			return !(CursedAmuletItem.isEffectPreventable(effect.getEffectType()) && LostRelicsUtil.hasRelic(entity, ModItems.CURSED_AMULET));
		}
	}

	public static class FireWeakness implements ModifyDamageTakenEvent {
		private static final RegistryKey<DamageType> SUN = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of("nycto", "sun"));

		@Override
		public float modify(Phase phase, float amount, ServerWorld world, DamageSource source, LivingEntity victim) {
			if (phase == Phase.FINAL && LostRelicsUtil.hasRelic(victim, ModItems.CURSED_AMULET)) {
				if (source.isIn(DamageTypeTags.IS_FIRE) || source.isOf(SUN)) {
					return 1.5F;
				}
			}
			return 1;
		}
	}

	public static class UndeadNeutrality implements PreventHostileTargetingEvent {
		@Override
		public TriState preventsTargeting(LivingEntity attacker, LivingEntity target) {
			if (!attacker.getType().isIn(ConventionalEntityTypeTags.BOSSES) && attacker.getType().isIn(EntityTypeTags.UNDEAD) && LostRelicsUtil.hasRelic(target, ModItems.CURSED_AMULET)) {
				return TriState.TRUE;
			}
			return TriState.DEFAULT;
		}
	}
}
