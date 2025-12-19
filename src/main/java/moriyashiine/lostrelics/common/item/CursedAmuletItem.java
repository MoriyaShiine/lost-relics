/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.item;

import moriyashiine.lostrelics.common.LostRelics;
import moriyashiine.lostrelics.common.init.ModComponentTypes;
import moriyashiine.lostrelics.common.init.ModItems;
import moriyashiine.lostrelics.common.init.ModSoundEvents;
import moriyashiine.lostrelics.common.util.LostRelicsUtil;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
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
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

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
	public ActionResult use(World world, PlayerEntity user, Hand hand) {
		if (user.isSneaking()) {
			ItemStack stack = user.getStackInHand(hand);
			if (stack.contains(ModComponentTypes.SHOW_SKELETON)) {
				SLibUtils.playSound(user, ModSoundEvents.ITEM_RELIC_TOGGLE);
				stack.set(ModComponentTypes.SHOW_SKELETON, !stack.get(ModComponentTypes.SHOW_SKELETON));
				return ActionResult.SUCCESS;
			}
		}
		return super.use(world, user, hand);
	}

	@Override
	public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
		if (clickType == ClickType.RIGHT && stack.contains(ModComponentTypes.SHOW_SKELETON)) {
			if (player.getEntityWorld().isClient()) {
				player.playSound(ModSoundEvents.ITEM_RELIC_TOGGLE, 1, 1);
			}
			stack.set(ModComponentTypes.SHOW_SKELETON, !stack.get(ModComponentTypes.SHOW_SKELETON));
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
		textConsumer.accept(Text.empty());
		textConsumer.accept(Text.translatable("item.modifiers.armor").formatted(Formatting.GRAY));
		GOOD_MODIFIERS.forEach((attribute, modifier) -> textConsumer.accept(Text.translatable("attribute.modifier.plus." + modifier.operation().getId(), AttributeModifiersComponent.DECIMAL_FORMAT.format(modifier.value()), Text.translatable(attribute.value().getTranslationKey())).append("?").formatted(Formatting.LIGHT_PURPLE)));
	}

	public static boolean doNegativesApply(Entity entity) {
		if (entity instanceof LivingEntity living && !living.isInCreativeMode()) {
			return LostRelicsUtil.hasRelic(living, ModItems.CURSED_AMULET);
		}
		return false;
	}
}
