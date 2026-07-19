package moriyashiine.lostrelics.mixin.cursedamulet;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.lostrelics.common.world.item.CursedAmuletItem;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Villager.class)
public class VillagerMixin {
	@ModifyReturnValue(method = "getPlayerReputation", at = @At("RETURN"))
	private int lostrelics$cursedAmulet(int original, Player player) {
		if (CursedAmuletItem.doNegativesApply(player)) {
			return original - 128;
		}
		return original;
	}
}
