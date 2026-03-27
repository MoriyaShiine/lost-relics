/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.common.world.item;

import moriyashiine.lostrelics.common.init.ModComponentTypes;
import moriyashiine.lostrelics.common.init.ModSoundEvents;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;

import java.util.function.Consumer;

public class ToggleableRelicItem extends EquippableRelicItem {
	private final String toggleTranslationKey;

	public ToggleableRelicItem(Properties properties, String toggleTranslationKey) {
		super(properties.component(ModComponentTypes.RELIC_TOGGLE, true));
		this.toggleTranslationKey = toggleTranslationKey;
	}

	@Override
	public InteractionResult use(Level level, Player player, InteractionHand hand) {
		if (player.isShiftKeyDown()) {
			ItemStack stack = player.getItemInHand(hand);
			if (stack.has(ModComponentTypes.RELIC_TOGGLE)) {
				SLibUtils.playSound(player, ModSoundEvents.ITEM_RELIC_TOGGLE);
				stack.set(ModComponentTypes.RELIC_TOGGLE, !stack.get(ModComponentTypes.RELIC_TOGGLE));
				return InteractionResult.SUCCESS;
			}
		}
		return super.use(level, player, hand);
	}

	@Override
	public boolean overrideOtherStackedOnMe(ItemStack self, ItemStack other, Slot slot, ClickAction clickAction, Player player, SlotAccess carriedItem) {
		if (clickAction == ClickAction.SECONDARY && other.isEmpty() && self.has(ModComponentTypes.RELIC_TOGGLE)) {
			if (player.level().isClientSide()) {
				player.playSound(ModSoundEvents.ITEM_RELIC_TOGGLE, 1, 1);
			}
			self.set(ModComponentTypes.RELIC_TOGGLE, !self.get(ModComponentTypes.RELIC_TOGGLE));
			return true;
		}
		return super.overrideOtherStackedOnMe(self, other, slot, clickAction, player, carriedItem);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
		MutableComponent icon = Component.literal("× ");
		ChatFormatting formatting = ChatFormatting.DARK_RED;
		if (stack.getOrDefault(ModComponentTypes.RELIC_TOGGLE, false)) {
			icon = Component.literal("✔ ");
			formatting = ChatFormatting.DARK_GREEN;
		}
		builder.accept(icon.append(Component.translatable(toggleTranslationKey)).withStyle(formatting));
	}
}
