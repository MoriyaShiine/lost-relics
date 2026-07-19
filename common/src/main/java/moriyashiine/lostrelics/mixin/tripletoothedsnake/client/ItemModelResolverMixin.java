package moriyashiine.lostrelics.mixin.tripletoothedsnake.client;

import moriyashiine.lostrelics.common.LostRelics;
import moriyashiine.lostrelics.common.init.LostRelicsItems;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ChargedProjectiles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemModelResolver.class)
public class ItemModelResolverMixin {
	@Unique
	private static final Identifier CROSSBOW_TAINTED_BLOOD_CRYSTAL = LostRelics.id("crossbow_tainted_blood_crystal");

	@ModifyVariable(method = "appendItemLayers", at = @At("STORE"), name = "modelId")
	private Identifier lostrelics$tripleToothedSnake(Identifier modelId, ItemStackRenderState output, ItemStack item) {
		if (item.is(Items.CROSSBOW) && item.getOrDefault(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.EMPTY).contains(LostRelicsItems.TAINTED_BLOOD_CRYSTAL)) {
			return CROSSBOW_TAINTED_BLOOD_CRYSTAL;
		}
		return modelId;
	}
}
