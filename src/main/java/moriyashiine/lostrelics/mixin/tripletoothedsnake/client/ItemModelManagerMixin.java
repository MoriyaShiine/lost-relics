/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.mixin.tripletoothedsnake.client;

import moriyashiine.lostrelics.common.LostRelics;
import moriyashiine.lostrelics.common.init.ModItems;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ChargedProjectilesComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemModelManager.class)
public class ItemModelManagerMixin {
	@Unique
	private static final Identifier CROSSBOW_TAINTED_BLOOD_CRYSTAL = LostRelics.id("crossbow_tainted_blood_crystal");

	@ModifyVariable(method = "update", at = @At("STORE"))
	private Identifier lostrelics$tripleToothedSnake(Identifier value, ItemRenderState renderState, ItemStack stack) {
		if (stack.isOf(Items.CROSSBOW) && stack.getOrDefault(DataComponentTypes.CHARGED_PROJECTILES, ChargedProjectilesComponent.DEFAULT).contains(ModItems.TAINTED_BLOOD_CRYSTAL)) {
			return CROSSBOW_TAINTED_BLOOD_CRYSTAL;
		}
		return value;
	}
}
