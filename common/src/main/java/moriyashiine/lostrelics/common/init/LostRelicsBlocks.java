package moriyashiine.lostrelics.common.init;

import moriyashiine.lostrelics.common.references.LostRelicsBlockItemIds;
import moriyashiine.lostrelics.common.tag.ModItemTags;
import moriyashiine.lostrelics.common.world.level.block.AltarBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerBlock;
import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerBlockType;
import static net.minecraft.world.level.block.state.BlockBehaviour.Properties.ofFullCopy;

public class LostRelicsBlocks {
	public static final Block JUNGLE_ALTAR = registerBlock(LostRelicsBlockItemIds.JUNGLE_ALTAR, settings -> new AltarBlock(settings, ModItemTags.JUNGLE_RELICS), ofFullCopy(Blocks.OBSIDIAN));

	public static void init() {
		registerBlockType("altar", AltarBlock.CODEC);
	}
}
