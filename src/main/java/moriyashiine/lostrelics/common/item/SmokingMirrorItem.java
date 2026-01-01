/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.item;

import moriyashiine.lostrelics.common.entity.mob.DoppelgangerEntity;
import moriyashiine.lostrelics.common.init.ModSoundEvents;
import moriyashiine.lostrelics.common.util.LostRelicsUtil;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import moriyashiine.strawberrylib.api.objects.enums.ParticleAnchor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class SmokingMirrorItem extends EquippableRelicItem {
	public SmokingMirrorItem(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult use(World world, PlayerEntity user, Hand hand) {
		ItemStack stack = user.getStackInHand(hand);
		if (LostRelicsUtil.isUsable(user, stack)) {
			if (!world.isClient()) {
				LostRelicsUtil.setCooldown(user, stack, 600);
				user.serverDamage(world.getDamageSources().magic(), user.getMaxHealth() / 2);
				boolean mirrorDemon = user.isSneaking();
				for (int i = 0; i < (mirrorDemon ? 1 : 4); i++) {
					DoppelgangerEntity doppelganger = new DoppelgangerEntity(world, user, mirrorDemon);
					final int minH = 1, maxH = 2;
					for (int j = 0; j < 32; j++) {
						int dX = user.getRandom().nextBetween(minH, maxH) * (user.getRandom().nextBoolean() ? 1 : -1);
						int dY = user.getRandom().nextBetween(-6, 6);
						int dZ = user.getRandom().nextBetween(minH, maxH) * (user.getRandom().nextBoolean() ? 1 : -1);
						if (doppelganger.teleport(user.getX() + dX, user.getY() + dY, user.getZ() + dZ, false)) {
							world.spawnEntity(doppelganger);
							SLibUtils.addParticles(doppelganger, ParticleTypes.SMOKE, 128, ParticleAnchor.BODY);
							SLibUtils.playSound(doppelganger, ModSoundEvents.ENTITY_GENERIC_SPAWN);
							break;
						}
					}
				}
			}
			return ActionResult.SUCCESS;
		}
		return super.use(world, user, hand);
	}
}
