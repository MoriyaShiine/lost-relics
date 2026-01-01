/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.item;

import moriyashiine.lostrelics.common.init.ModComponentTypes;
import moriyashiine.lostrelics.common.init.ModSoundEvents;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.function.Consumer;

public class ToggleableRelicItem extends EquippableRelicItem {
	private final String toggleTranslationKey;

	public ToggleableRelicItem(Settings settings, String toggleTranslationKey) {
		super(settings.component(ModComponentTypes.RELIC_TOGGLE, true));
		this.toggleTranslationKey = toggleTranslationKey;
	}

	@Override
	public ActionResult use(World world, PlayerEntity user, Hand hand) {
		if (user.isSneaking()) {
			ItemStack stack = user.getStackInHand(hand);
			if (stack.contains(ModComponentTypes.RELIC_TOGGLE)) {
				SLibUtils.playSound(user, ModSoundEvents.ITEM_RELIC_TOGGLE);
				stack.set(ModComponentTypes.RELIC_TOGGLE, !stack.get(ModComponentTypes.RELIC_TOGGLE));
				return ActionResult.SUCCESS;
			}
		}
		return super.use(world, user, hand);
	}

	@Override
	public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
		if (clickType == ClickType.RIGHT && otherStack.isEmpty() && stack.contains(ModComponentTypes.RELIC_TOGGLE)) {
			if (player.getEntityWorld().isClient()) {
				player.playSound(ModSoundEvents.ITEM_RELIC_TOGGLE, 1, 1);
			}
			stack.set(ModComponentTypes.RELIC_TOGGLE, !stack.get(ModComponentTypes.RELIC_TOGGLE));
			return true;
		}
		return super.onClicked(stack, otherStack, slot, clickType, player, cursorStackReference);
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
		MutableText icon = Text.literal("× ");
		Formatting formatting = Formatting.DARK_RED;
		if (stack.getOrDefault(ModComponentTypes.RELIC_TOGGLE, false)) {
			icon = Text.literal("✔ ");
			formatting = Formatting.DARK_GREEN;
		}
		textConsumer.accept(icon.append(Text.translatable(toggleTranslationKey)).formatted(formatting));
	}
}
