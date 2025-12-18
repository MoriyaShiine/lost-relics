/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.init;

import moriyashiine.lostrelics.common.LostRelics;
import moriyashiine.lostrelics.common.item.CursedAmuletItem;
import moriyashiine.lostrelics.common.item.TaintedBloodCrystalItem;
import moriyashiine.lostrelics.common.item.TripleToothedSnakeItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.*;

public class ModItems {
	public static ItemGroup GROUP;

	public static final Item JUNGLE_ALTAR = registerBlockItem("jungle_altar", ModBlocks.JUNGLE_ALTAR);

	public static final Item CURSED_AMULET = registerItem("cursed_amulet", CursedAmuletItem::new, relicSettings()
			.component(ModComponentTypes.SHOW_SKELETON, true));
	public static final Item SMOKING_MIRROR = registerItem("smoking_mirror", relicSettings());
	public static final Item TRIPLE_TOOTHED_SNAKE = registerItem("triple_toothed_snake", TripleToothedSnakeItem::new, relicSettings()
			.sword(ModToolMaterials.TRIPLE_TOOTHED_SNAKE, 5, -2.4F)
			.component(ModComponentTypes.SNAKE_CHARGE, 0)
			.component(DataComponentTypes.DAMAGE, null)
			.component(DataComponentTypes.MAX_DAMAGE, null)
			.component(DataComponentTypes.REPAIRABLE, null));
	public static final Item TAINTED_BLOOD_CRYSTAL = registerItem("tainted_blood_crystal", TaintedBloodCrystalItem::new, new Item.Settings().fireproof().rarity(Rarity.UNCOMMON));
	public static final Item TURQUOISE_EYE = registerItem("turquoise_eye", relicSettings());

	private static Item.Settings relicSettings() {
		return new Item.Settings().fireproof().rarity(Rarity.RARE).maxCount(1);
	}

	public static void init() {
		GROUP = registerItemGroup(FabricItemGroup.builder().displayName(Text.translatable("itemGroup." + LostRelics.MOD_ID)).icon(CURSED_AMULET::getDefaultStack).entries((displayContext, entries) -> {
			entries.add(JUNGLE_ALTAR);

			entries.add(CURSED_AMULET);
			entries.add(SMOKING_MIRROR);
			entries.add(TRIPLE_TOOTHED_SNAKE);
			entries.add(TAINTED_BLOOD_CRYSTAL);
			entries.add(TURQUOISE_EYE);
		}).build());
	}
}
