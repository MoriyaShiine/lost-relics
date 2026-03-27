/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.data.provider;

import moriyashiine.lostrelics.common.tag.ModMobEffectTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;

import java.util.concurrent.CompletableFuture;

public class ModMobEffectTagsProvider extends FabricTagsProvider.FabricIntrinsicHolderTagsProvider<MobEffect> {
	public ModMobEffectTagsProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, Registries.MOB_EFFECT, registriesFuture, statusEffect -> ResourceKey.create(Registries.MOB_EFFECT, BuiltInRegistries.MOB_EFFECT.getKey(statusEffect)));
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		valueLookupBuilder(ModMobEffectTags.BYPASSES_CURSED_AMULET).add(
				MobEffects.INSTANT_DAMAGE.value(),
				MobEffects.WITHER.value());

		valueLookupBuilder(ModMobEffectTags.CANNOT_BE_SIPHONED)
				.addOptionalTag(TagKey.create(Registries.MOB_EFFECT, Identifier.fromNamespaceAndPath("nycto", "infection")));
	}
}
