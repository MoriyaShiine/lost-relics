/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.init;

import moriyashiine.lostrelics.common.LostRelics;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.text.Text;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.*;

public class ModItems {
	public static ItemGroup GROUP;

	public static final Item JUNGLE_ATLAR = registerBlockItem("jungle_altar", ModBlocks.JUNGLE_ALTAR);

	public static final Item CURSED_AMULET = registerItem("cursed_amulet", new Item.Settings().maxCount(1));
	public static final Item SMOKING_MIRROR = registerItem("smoking_mirror", new Item.Settings().maxCount(1));
	public static final Item TRIPLE_TOOTHED_SNAKE = registerItem("triple_toothed_snake", new Item.Settings().maxCount(1));
	public static final Item TURQUOISE_EYE = registerItem("turquoise_eye", new Item.Settings().maxCount(1));

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
