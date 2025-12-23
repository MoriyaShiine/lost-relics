/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.item;

import moriyashiine.lostrelics.common.LostRelics;
import moriyashiine.lostrelics.common.init.ModItems;
import moriyashiine.lostrelics.common.util.LostRelicsUtil;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CursedAmuletItem extends ToggleableRelicItem {
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
		super(settings, "tooltip.lostrelics.show_skeleton");
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
		super.appendTooltip(stack, context, displayComponent, textConsumer, type);
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
