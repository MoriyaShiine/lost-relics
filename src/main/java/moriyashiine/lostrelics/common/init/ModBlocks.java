/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.init;

import moriyashiine.lostrelics.common.block.AltarBlock;
import moriyashiine.lostrelics.common.tag.ModItemTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerBlock;
import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerBlockType;
import static net.minecraft.block.AbstractBlock.Settings.copy;

public class ModBlocks {
	public static final Block JUNGLE_ALTAR = registerBlock("jungle_altar", settings -> new AltarBlock(settings, ModItemTags.JUNGLE_RELICS), copy(Blocks.OBSIDIAN));

	public static void init() {
		registerBlockType("altar", AltarBlock.CODEC);
	}
}
