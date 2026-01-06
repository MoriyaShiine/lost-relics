/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.mixin.cursedamulet;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.lostrelics.common.init.ModItems;
import moriyashiine.lostrelics.common.util.LostRelicsUtil;
import net.minecraft.component.type.DeathProtectionComponent;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@Unique
	private boolean hasCursedAmulet() {
		return LostRelicsUtil.hasRelic((LivingEntity) (Object) this, ModItems.CURSED_AMULET);
	}

	@ModifyReturnValue(method = "canBreatheInWater", at = @At("RETURN"))
	private boolean lostrelics$cursedAmulet$breatheUnderwater(boolean original) {
		return original || hasCursedAmulet();
	}

	@ModifyExpressionValue(method = "canHaveStatusEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityType;isIn(Lnet/minecraft/registry/tag/TagKey;)Z", ordinal = 2))
	private boolean lostrelics$cursedAmulet$ignorePoisonAndRegen(boolean original) {
		return original || hasCursedAmulet();
	}

	@ModifyReturnValue(method = "hasInvertedHealingAndHarm", at = @At("RETURN"))
	private boolean lostrelics$cursedAmulet$invertedHealingAndHarm(boolean original) {
		return original || hasCursedAmulet();
	}

	@SuppressWarnings("ConstantValue")
	@ModifyReturnValue(method = "tryUseDeathProtector", at = @At(value = "RETURN", ordinal = 1))
	private boolean lostrelics$cursedAmulet$preventDeath(boolean original) {
		if (!original && (Object) this instanceof PlayerEntity player && player.getRandom().nextFloat() < 1 / 3F) {
			ItemStack relicStack = LostRelicsUtil.getRelic(player, ModItems.CURSED_AMULET);
			if (LostRelicsUtil.isUsable(player, relicStack)) {
				LostRelicsUtil.setCooldown(player, relicStack, 6000);
				if (player instanceof ServerPlayerEntity serverPlayer) {
					serverPlayer.incrementStat(Stats.USED.getOrCreateStat(ModItems.CURSED_AMULET));
				}
				player.setHealth(1);
				DeathProtectionComponent.TOTEM_OF_UNDYING.applyDeathEffects(relicStack, player);
				player.getEntityWorld().sendEntityStatus(player, EntityStatuses.USE_TOTEM_OF_UNDYING);
				return true;
			}
		}
		return original;
	}
}
