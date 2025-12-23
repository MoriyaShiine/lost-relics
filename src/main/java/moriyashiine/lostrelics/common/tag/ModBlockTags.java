/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.tag;

import moriyashiine.lostrelics.common.LostRelics;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class ModBlockTags {
	public static final TagKey<Block> TREASURE = TagKey.of(RegistryKeys.BLOCK, LostRelics.id("treasure"));
	public static final TagKey<Block> UNIMPORTANT_TREASURE = TagKey.of(RegistryKeys.BLOCK, LostRelics.id("unimportant_treasure"));
}
