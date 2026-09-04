package moriyashiine.lostrelics.mixin.cursedamulet;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.lostrelics.common.init.LostRelicsItems;
import moriyashiine.lostrelics.common.util.LostRelicsUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DeathProtection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@Unique
	private boolean hasCursedAmulet() {
		return LostRelicsUtil.hasRelic((LivingEntity) (Object) this, LostRelicsItems.CURSED_AMULET);
	}

	@ModifyReturnValue(method = "canBreatheUnderwater", at = @At("RETURN"))
	private boolean lostrelics$cursedAmulet$breatheUnderwater(boolean original) {
		return original || hasCursedAmulet();
	}

	@ModifyExpressionValue(method = "canBeAffected", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;is(Lnet/minecraft/tags/TagKey;)Z", ordinal = 2))
	private boolean lostrelics$cursedAmulet$ignorePoisonAndRegen(boolean original) {
		return original || hasCursedAmulet();
	}

	@ModifyReturnValue(method = "isInvertedHealAndHarm", at = @At("RETURN"))
	private boolean lostrelics$cursedAmulet$invertedHealingAndHarm(boolean original) {
		return original || hasCursedAmulet();
	}

	@SuppressWarnings("ConstantValue")
	@ModifyReturnValue(method = "checkTotemDeathProtection", at = @At(value = "RETURN", ordinal = 1))
	private boolean lostrelics$cursedAmulet$preventDeath(boolean original) {
		if (!original && (Object) this instanceof Player player && player.getRandom().nextFloat() < 1 / 3F) {
			ItemStack relic = LostRelicsUtil.getRelic(player, LostRelicsItems.CURSED_AMULET);
			if (LostRelicsUtil.isUsable(player, relic)) {
				LostRelicsUtil.setCooldown(player, relic, 6000);
				if (player instanceof ServerPlayer serverPlayer) {
					serverPlayer.awardStat(Stats.ITEM_USED.get(LostRelicsItems.CURSED_AMULET));
				}
				player.setHealth(1);
				DeathProtection.TOTEM_OF_UNDYING.applyEffects(relic, player);
				player.level().broadcastEntityEvent(player, EntityEvent.PROTECTED_FROM_DEATH);
				return true;
			}
		}
		return original;
	}
}
