package moriyashiine.lostrelics.mixin.smokingmirror;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.lostrelics.common.init.LostRelicsItems;
import moriyashiine.lostrelics.common.util.LostRelicsUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.ServerStatsCounter;
import net.minecraft.stats.Stat;
import net.minecraft.world.level.levelgen.PhantomSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PhantomSpawner.class)
public class PhantomSpawnerMixin {
	@WrapOperation(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/stats/ServerStatsCounter;getValue(Lnet/minecraft/stats/Stat;)I"))
	private int lostrelics$smokingMirror(ServerStatsCounter instance, Stat<?> stat, Operation<Integer> original, @Local(name = "player") ServerPlayer player) {
		int value = original.call(instance, stat);
		if (LostRelicsUtil.hasRelic(player, LostRelicsItems.SMOKING_MIRROR)) {
			return value + 72000;
		}
		return value;
	}
}
