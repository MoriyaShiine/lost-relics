/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.common.event;

import moriyashiine.lostrelics.common.init.LostRelicsItems;
import moriyashiine.lostrelics.common.util.LostRelicsUtil;
import moriyashiine.lostrelics.common.world.item.CursedAmuletItem;
import moriyashiine.strawberrylib.api.event.ModifyDamageTakenEvent;
import moriyashiine.strawberrylib.api.event.PreventHostileTargetingEvent;
import net.fabricmc.fabric.api.entity.event.v1.effect.EffectEventContext;
import net.fabricmc.fabric.api.entity.event.v1.effect.ServerMobEffectEvents;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalEntityTypeTags;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class CursedAmuletEvent {
	public static void init() {
		ServerMobEffectEvents.ALLOW_ADD.register(new EffectImmunity());
		ModifyDamageTakenEvent.MULTIPLY_TOTAL.register(new FireWeakness());
		PreventHostileTargetingEvent.EVENT.register(new UndeadNeutrality());
	}

	private static class EffectImmunity implements ServerMobEffectEvents.AllowAdd {
		@Override
		public boolean allowAdd(MobEffectInstance effect, LivingEntity entity, EffectEventContext ctx) {
			return !(CursedAmuletItem.isEffectPreventable(effect.getEffect()) && LostRelicsUtil.hasRelic(entity, LostRelicsItems.CURSED_AMULET));
		}
	}

	private static class FireWeakness implements ModifyDamageTakenEvent {
		private static final ResourceKey<DamageType> SUN = ResourceKey.create(Registries.DAMAGE_TYPE, Identifier.fromNamespaceAndPath("nycto", "sun"));

		@Override
		public float modify(Phase phase, LivingEntity victim, ServerLevel level, DamageSource source) {
			if (phase == Phase.FINAL && LostRelicsUtil.hasRelic(victim, LostRelicsItems.CURSED_AMULET)) {
				if (source.is(DamageTypeTags.IS_FIRE) || source.is(SUN)) {
					return 1.5F;
				}
			}
			return 1;
		}
	}

	private static class UndeadNeutrality implements PreventHostileTargetingEvent {
		@Override
		public TriState preventsTargeting(LivingEntity attacker, LivingEntity target) {
			if (!attacker.is(ConventionalEntityTypeTags.BOSSES) && attacker.is(EntityTypeTags.UNDEAD) && LostRelicsUtil.hasRelic(target, LostRelicsItems.CURSED_AMULET)) {
				return TriState.TRUE;
			}
			return TriState.DEFAULT;
		}
	}
}
