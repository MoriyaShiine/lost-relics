package moriyashiine.lostrelics.common.tag;

import moriyashiine.lostrelics.common.LostRelics;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class LostRelicsItemTags {
	public static final TagKey<Item> RELICS = TagKey.create(Registries.ITEM, LostRelics.id("relics"));

	public static final TagKey<Item> JUNGLE_RELICS = TagKey.create(Registries.ITEM, LostRelics.id("jungle_relics"));
}
