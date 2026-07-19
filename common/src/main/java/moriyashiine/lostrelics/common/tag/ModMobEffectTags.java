package moriyashiine.lostrelics.common.tag;

import moriyashiine.lostrelics.common.LostRelics;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;

public class ModMobEffectTags {
	public static final TagKey<MobEffect> BYPASSES_CURSED_AMULET = TagKey.create(Registries.MOB_EFFECT, LostRelics.id("bypasses_cursed_amulet"));

	public static final TagKey<MobEffect> CANNOT_BE_SIPHONED = TagKey.create(Registries.MOB_EFFECT, LostRelics.id("cannot_be_siphoned"));
}
