package moriyashiine.lostrelics.datagen.provider;

import moriyashiine.lostrelics.common.tag.LostRelicsMobEffectTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;

import java.util.concurrent.CompletableFuture;

public class LostRelicsMobEffectTagsProvider extends FabricTagsProvider<MobEffect> {
	public LostRelicsMobEffectTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, Registries.MOB_EFFECT, registriesFuture);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		add(LostRelicsMobEffectTags.BYPASSES_CURSED_AMULET,
				MobEffects.INSTANT_DAMAGE,
				MobEffects.WITHER);

		builder(LostRelicsMobEffectTags.CANNOT_BE_SIPHONED)
				.addOptionalTag(TagKey.create(Registries.MOB_EFFECT, Identifier.fromNamespaceAndPath("nycto", "infection")));
	}

	@SafeVarargs
	private void add(TagKey<MobEffect> tagKey, Holder<MobEffect>... effects) {
		TagAppender<ResourceKey<MobEffect>, MobEffect> builder = builder(tagKey);
		for (Holder<MobEffect> effect : effects) {
			builder.add(effect.unwrapKey().orElseThrow());
		}
	}
}
