/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.mixin.util;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moriyashiine.lostrelics.common.component.entity.PersistentCooldownComponent;
import moriyashiine.lostrelics.common.init.ModEntityComponents;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemCooldownManager.class)
public class ItemCooldownManagerMixin {
	@ModifyReturnValue(method = "getCooldownProgress", at = @At("RETURN"))
	private float lostrelics$persistentCooldown(float original, ItemStack stack, float tickProgress) {
		if (PersistentCooldownComponent.currentPlayer != null) {
			PersistentCooldownComponent persistentCooldownComponent = ModEntityComponents.PERSISTENT_COOLDOWN.get(PersistentCooldownComponent.currentPlayer);
			float progress = persistentCooldownComponent.getCooldownProgress(stack, tickProgress);
			return Math.max(original, progress);
		}
		return original;
	}
}
