/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.init;

import moriyashiine.lostrelics.common.LostRelics;
import moriyashiine.lostrelics.common.item.CursedAmuletItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.*;

public class ModItems {
	public static ItemGroup GROUP;

	public static final Item JUNGLE_ATLAR = registerBlockItem("jungle_altar", ModBlocks.JUNGLE_ALTAR);

	public static final Item CURSED_AMULET = registerItem("cursed_amulet", CursedAmuletItem::new, relicSettings().component(ModComponentTypes.SHOW_SKELETON, true));
	public static final Item SMOKING_MIRROR = registerItem("smoking_mirror", relicSettings());
	public static final Item TRIPLE_TOOTHED_SNAKE = registerItem("triple_toothed_snake", relicSettings());
	public static final Item TURQUOISE_EYE = registerItem("turquoise_eye", relicSettings());

	private static Item.Settings relicSettings() {
		return new Item.Settings().fireproof().rarity(Rarity.RARE).maxCount(1);
	}

	public static void init() {
		GROUP = registerItemGroup(FabricItemGroup.builder().displayName(Text.translatable("itemGroup." + LostRelics.MOD_ID)).icon(CURSED_AMULET::getDefaultStack).entries((displayContext, entries) -> {
			entries.add(JUNGLE_ATLAR);

			entries.add(CURSED_AMULET);
			entries.add(SMOKING_MIRROR);
			entries.add(TRIPLE_TOOTHED_SNAKE);
			entries.add(TURQUOISE_EYE);
		}).build());
	}
}
