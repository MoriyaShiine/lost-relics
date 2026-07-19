package moriyashiine.lostrelics.common.tag;

import moriyashiine.lostrelics.common.LostRelics;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModBlockTags {
	public static final TagKey<Block> TREASURE = TagKey.create(Registries.BLOCK, LostRelics.id("treasure"));
	public static final TagKey<Block> UNIMPORTANT_TREASURE = TagKey.create(Registries.BLOCK, LostRelics.id("unimportant_treasure"));
}
