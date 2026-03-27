/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.common.init;

import moriyashiine.lostrelics.common.tag.ModItemTags;
import moriyashiine.lostrelics.common.world.level.block.AltarBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerBlock;
import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerBlockType;
import static net.minecraft.world.level.block.state.BlockBehaviour.Properties.ofFullCopy;

public class ModBlocks {
	public static final Block JUNGLE_ALTAR = registerBlock("jungle_altar", settings -> new AltarBlock(settings, ModItemTags.JUNGLE_RELICS), ofFullCopy(Blocks.OBSIDIAN));

	public static void init() {
		registerBlockType("altar", AltarBlock.CODEC);
	}
}
