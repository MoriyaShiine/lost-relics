/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.event;

import moriyashiine.lostrelics.common.init.ModComponentTypes;
import moriyashiine.lostrelics.common.init.ModEntityTypes;
import moriyashiine.lostrelics.common.init.ModItems;
import moriyashiine.lostrelics.common.init.ModSoundEvents;
import moriyashiine.lostrelics.common.item.CursedAmuletItem;
import moriyashiine.lostrelics.common.util.LostRelicsUtil;
import moriyashiine.strawberrylib.api.event.ModifyDamageTakenEvent;
import moriyashiine.strawberrylib.api.event.PreventHostileTargetingEvent;
import moriyashiine.strawberrylib.api.event.TickEntityEvent;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import moriyashiine.strawberrylib.api.objects.enums.ParticleAnchor;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalEntityTypeTags;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;

public class CursedAmuletEvent {
	// todo move this to accessory (un)equip
	public static class Tick implements TickEntityEvent {
		@Override
		public void tick(ServerWorld world, Entity entity) {
			if (entity instanceof PlayerEntity player) {
				ItemStack relicStack = LostRelicsUtil.getRelic(player, ModItems.CURSED_AMULET);
				boolean hasRelicSkeleton = SLibUtils.hasModelReplacementType(player, ModEntityTypes.RELIC_SKELETON);
				if (!relicStack.isEmpty() && relicStack.getOrDefault(ModComponentTypes.SHOW_SKELETON, false)) {
					if (!hasRelicSkeleton) {
						SLibUtils.addModelReplacementType(player, ModEntityTypes.RELIC_SKELETON);
						SLibUtils.playSound(player, ModSoundEvents.ENTITY_GENERIC_TRANSFORM);
						SLibUtils.addParticles(player, ParticleTypes.SMOKE, 48, ParticleAnchor.BODY);
					}
				} else if (hasRelicSkeleton) {
					SLibUtils.removeModelReplacementType(player, ModEntityTypes.RELIC_SKELETON);
					SLibUtils.playSound(player, ModSoundEvents.ENTITY_GENERIC_TRANSFORM);
					SLibUtils.addParticles(player, ParticleTypes.SMOKE, 48, ParticleAnchor.BODY);
				}
				boolean apply = !relicStack.isEmpty() && !player.isCreative() && player.isPartOfGame();
				boolean applyNegative = world.isDay() && world.isSkyVisible(entity.getBlockPos());
				CursedAmuletItem.GOOD_MODIFIERS.forEach((attribute, modifier) -> SLibUtils.conditionallyApplyAttributeModifier(player, attribute, modifier, apply && !applyNegative));
				CursedAmuletItem.BAD_MODIFIERS.forEach((attribute, modifier) -> SLibUtils.conditionallyApplyAttributeModifier(player, attribute, modifier, apply && applyNegative));
			}
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
