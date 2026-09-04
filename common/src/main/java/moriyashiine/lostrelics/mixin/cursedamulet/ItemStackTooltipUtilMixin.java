package moriyashiine.lostrelics.mixin.cursedamulet;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import eu.pb4.trinkets.impl.ItemStackTooltipUtil;
import moriyashiine.lostrelics.common.world.item.CursedAmuletItem;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.TooltipDisplay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemStackTooltipUtil.class)
public class ItemStackTooltipUtilMixin {
	@Unique
	private static boolean isCursedAmulet = false;

	@Inject(method = "getTooltip", at = @At("HEAD"))
	private static void lostrelics$cursedAmulet(ItemStack self, TooltipDisplay displayComponent, Player player, Consumer<Component> textConsumer, CallbackInfo ci) {
		isCursedAmulet = self.getItem() instanceof CursedAmuletItem;
	}

	@ModifyExpressionValue(method = "addAttributes", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;amount()D"))
	private static double lostrelics$cursedAmulet(double original, @Local(name = "attribute") Holder<Attribute> attribute) {
		if (isCursedAmulet) {
			AttributeModifier modifier = CursedAmuletItem.GOOD_MODIFIERS.get(attribute);
			if (modifier != null) {
				return modifier.amount();
			}
		}
		return original;
	}

	@ModifyExpressionValue(method = "addAttributes", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/MutableComponent;withStyle(Lnet/minecraft/ChatFormatting;)Lnet/minecraft/network/chat/MutableComponent;", ordinal = 0))
	private static MutableComponent lostrelics$cursedAmulet(MutableComponent original) {
		if (isCursedAmulet) {
			return original.append("?").withColor(TextColor.LIGHT_PURPLE);
		}
		return original;
	}
}
