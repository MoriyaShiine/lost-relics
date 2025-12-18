/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.item;

import moriyashiine.lostrelics.common.LostRelics;
import moriyashiine.lostrelics.common.init.ModComponentTypes;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CursedAmuletItem extends Item {
	public static final Map<RegistryEntry<EntityAttribute>, EntityAttributeModifier> GOOD_MODIFIERS, BAD_MODIFIERS;

	static {
		GOOD_MODIFIERS = new HashMap<>();
		GOOD_MODIFIERS.put(EntityAttributes.ARMOR, new EntityAttributeModifier(LostRelics.id("cursed_amulet_good_armor"), 4, EntityAttributeModifier.Operation.ADD_VALUE));
		GOOD_MODIFIERS.put(EntityAttributes.ATTACK_DAMAGE, new EntityAttributeModifier(LostRelics.id("cursed_amulet_good_attack_damage"), 3, EntityAttributeModifier.Operation.ADD_VALUE));

		BAD_MODIFIERS = new HashMap<>();
		BAD_MODIFIERS.put(EntityAttributes.ARMOR, new EntityAttributeModifier(LostRelics.id("cursed_amulet_bad_armor"), -4, EntityAttributeModifier.Operation.ADD_VALUE));
		BAD_MODIFIERS.put(EntityAttributes.ATTACK_DAMAGE, new EntityAttributeModifier(LostRelics.id("cursed_amulet_bad_attack_damage"), -3, EntityAttributeModifier.Operation.ADD_VALUE));
	}

	public CursedAmuletItem(Settings settings) {
		super(settings);
	}

	@Override
	public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
		if (clickType == ClickType.RIGHT && stack.contains(ModComponentTypes.SHOW_SKELETON)) {
			stack.set(ModComponentTypes.SHOW_SKELETON, !stack.get(ModComponentTypes.SHOW_SKELETON));
			if (player.getEntityWorld().isClient()) {
				player.playSound(SoundEvents.UI_BUTTON_CLICK.value(), 1, 1);
			}
			return true;
		}
		return super.onClicked(stack, otherStack, slot, clickType, player, cursorStackReference);
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
		MutableText icon = Text.literal("× ");
		Formatting formatting = Formatting.DARK_RED;
		if (stack.getOrDefault(ModComponentTypes.SHOW_SKELETON, false)) {
			icon = Text.literal("✔ ");
			formatting = Formatting.DARK_GREEN;
		}
		textConsumer.accept(icon.append(Text.translatable("tooltip.lostrelics.show_skeleton")).formatted(formatting));
	}
}
