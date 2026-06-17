/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.common.init;

import moriyashiine.lostrelics.common.LostRelics;
import moriyashiine.lostrelics.common.references.LostRelicsBlockItemIds;
import moriyashiine.lostrelics.common.references.LostRelicsItemIds;
import moriyashiine.lostrelics.common.world.item.*;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.*;

public class LostRelicsItems {
	public static CreativeModeTab TAB;

	public static final Item JUNGLE_ALTAR = registerBlockItem(LostRelicsBlockItemIds.JUNGLE_ALTAR, LostRelicsBlocks.JUNGLE_ALTAR);

	public static final Item CURSED_AMULET = registerItem(LostRelicsItemIds.CURSED_AMULET, CursedAmuletItem::new, relicSettings());
	public static final Item SMOKING_MIRROR = registerItem(LostRelicsItemIds.SMOKING_MIRROR, SmokingMirrorItem::new, relicSettings());
	public static final Item TRIPLE_TOOTHED_SNAKE = registerItem(LostRelicsItemIds.TRIPLE_TOOTHED_SNAKE, TripleToothedSnakeItem::new, relicSettings()
			.sword(LostRelicsToolMaterials.TRIPLE_TOOTHED_SNAKE, 5, -2.4F)
			.component(LostRelicsDataComponents.SNAKE_CHARGE, 0)
			.component(DataComponents.DAMAGE, null)
			.component(DataComponents.MAX_DAMAGE, null)
			.component(DataComponents.REPAIRABLE, null));
	public static final Item TAINTED_BLOOD_CRYSTAL = registerItem(LostRelicsItemIds.TAINTED_BLOOD_CRYSTAL, TaintedBloodCrystalItem::new, new Item.Properties().fireResistant().rarity(Rarity.UNCOMMON));
	public static final Item TURQUOISE_EYE = registerItem(LostRelicsItemIds.TURQUOISE_EYE, TurquoiseEyeItem::new, relicSettings());

	private static Item.Properties relicSettings() {
		return new Item.Properties().fireResistant().rarity(Rarity.RARE).stacksTo(1);
	}

	public static void init() {
		TAB = registerCreativeModeTab(FabricCreativeModeTab.builder().title(Component.translatable("itemGroup." + LostRelics.MOD_ID)).icon(CURSED_AMULET::getDefaultInstance).displayItems((_, output) -> {
			output.accept(JUNGLE_ALTAR);

			output.accept(CURSED_AMULET);
			output.accept(SMOKING_MIRROR);
			output.accept(TRIPLE_TOOTHED_SNAKE);
			output.accept(TAINTED_BLOOD_CRYSTAL);
			output.accept(TURQUOISE_EYE);
		}).build());
	}
}
