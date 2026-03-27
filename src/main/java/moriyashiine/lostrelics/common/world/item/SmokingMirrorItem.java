/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.common.world.item;

import moriyashiine.lostrelics.common.init.ModSoundEvents;
import moriyashiine.lostrelics.common.util.LostRelicsUtil;
import moriyashiine.lostrelics.common.world.entity.monster.Doppelganger;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import moriyashiine.strawberrylib.api.objects.enums.ParticleAnchor;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SmokingMirrorItem extends EquippableRelicItem {
	public SmokingMirrorItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (LostRelicsUtil.isUsable(player, stack)) {
			if (!level.isClientSide()) {
				LostRelicsUtil.setCooldown(player, stack, 600);
				player.hurt(level.damageSources().magic(), player.getMaxHealth() / 2);
				boolean mirrorDemon = player.isShiftKeyDown();
				for (int i = 0; i < (mirrorDemon ? 1 : 4); i++) {
					Doppelganger doppelganger = new Doppelganger(level, player, mirrorDemon);
					final int minH = 1, maxH = 2;
					for (int j = 0; j < 32; j++) {
						int dX = player.getRandom().nextIntBetweenInclusive(minH, maxH) * (player.getRandom().nextBoolean() ? 1 : -1);
						int dY = player.getRandom().nextIntBetweenInclusive(-6, 6);
						int dZ = player.getRandom().nextIntBetweenInclusive(minH, maxH) * (player.getRandom().nextBoolean() ? 1 : -1);
						if (doppelganger.randomTeleport(player.getX() + dX, player.getY() + dY, player.getZ() + dZ, false)) {
							level.addFreshEntity(doppelganger);
							SLibUtils.addParticles(doppelganger, ParticleTypes.SMOKE, 128, ParticleAnchor.BODY);
							SLibUtils.playSound(doppelganger, ModSoundEvents.ENTITY_GENERIC_SPAWN);
							break;
						}
					}
				}
			}
			return InteractionResult.SUCCESS;
		}
		return super.use(level, player, hand);
	}
}
