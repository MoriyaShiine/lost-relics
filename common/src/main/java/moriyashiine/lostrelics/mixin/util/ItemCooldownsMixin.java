package moriyashiine.lostrelics.mixin.util;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.lostrelics.common.component.entity.PersistentCooldownComponent;
import moriyashiine.lostrelics.common.init.LostRelicsEntityComponents;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemCooldowns.class)
public class ItemCooldownsMixin {
	@ModifyReturnValue(method = "getCooldownPercent", at = @At("RETURN"))
	private float lostrelics$persistentCooldown(float original, ItemStack item, float a) {
		if (PersistentCooldownComponent.currentPlayer != null) {
			PersistentCooldownComponent persistentCooldown = LostRelicsEntityComponents.PERSISTENT_COOLDOWN.get(PersistentCooldownComponent.currentPlayer);
			float progress = persistentCooldown.getCooldownProgress(item, a);
			return Math.max(original, progress);
		}
		return original;
	}
}
