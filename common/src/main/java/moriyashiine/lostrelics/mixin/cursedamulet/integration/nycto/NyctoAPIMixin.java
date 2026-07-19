package moriyashiine.lostrelics.mixin.cursedamulet.integration.nycto;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.lostrelics.common.init.LostRelicsItems;
import moriyashiine.lostrelics.common.util.LostRelicsUtil;
import moriyashiine.nycto.api.NyctoAPI;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(NyctoAPI.class)
public class NyctoAPIMixin {
	@ModifyReturnValue(method = "hasBlood", at = @At("RETURN"))
	private static boolean lostrelics$cursedAmulet(boolean original, Entity entity) {
		if (original && entity instanceof LivingEntity living && LostRelicsUtil.hasRelic(living, LostRelicsItems.CURSED_AMULET)) {
			return false;
		}
		return original;
	}
}
