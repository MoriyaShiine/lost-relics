/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.init;

import moriyashiine.lostrelics.common.LostRelics;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.text.Text;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerBlockItem;
import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerItemGroup;

public class ModItems {
	public static ItemGroup GROUP;

	public static final Item JUNGLE_ATLAR = registerBlockItem("jungle_altar", ModBlocks.JUNGLE_ALTAR);

	public static void init() {
		GROUP = registerItemGroup(FabricItemGroup.builder().displayName(Text.translatable("itemGroup." + LostRelics.MOD_ID)).icon(JUNGLE_ATLAR::getDefaultStack).entries((displayContext, entries) -> {
			entries.add(JUNGLE_ATLAR);
		}).build());
	}
}
