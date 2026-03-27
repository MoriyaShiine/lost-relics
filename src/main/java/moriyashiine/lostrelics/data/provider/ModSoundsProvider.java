/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.data.provider;

import moriyashiine.lostrelics.common.LostRelics;
import moriyashiine.lostrelics.common.init.ModSoundEvents;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricSoundsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvents;

import java.util.concurrent.CompletableFuture;

import static net.fabricmc.fabric.api.client.datagen.v1.builder.SoundTypeBuilder.RegistrationBuilder.ofEvent;
import static net.fabricmc.fabric.api.client.datagen.v1.builder.SoundTypeBuilder.of;

public class ModSoundsProvider extends FabricSoundsProvider {
	public ModSoundsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(HolderLookup.Provider registries, SoundExporter exporter) {
		exporter.add(ModSoundEvents.BLOCK_ALTAR_CONVERT, of().subtitle("subtitles.lostrelics.block.altar.convert")
				.sound(ofEvent(SoundEvents.FIRE_EXTINGUISH)));

		exporter.add(ModSoundEvents.ENTITY_GENERIC_SPAWN, of().subtitle("subtitles.lostrelics.entity.generic.spawn")
				.sound(ofEvent(SoundEvents.WITHER_SHOOT).volume(0.2F)));
		exporter.add(ModSoundEvents.ENTITY_GENERIC_TRANSFORM, of().subtitle("subtitles.lostrelics.entity.generic.transform")
				.sound(ofEvent(SoundEvents.FIRE_EXTINGUISH)));
		exporter.add(ModSoundEvents.ENTITY_TAINTED_BLOOD_CRYSTAL_SHATTER, of().subtitle("subtitles.lostrelics.entity.tainted_blood_crystal.shatter")
				.sound(ofEvent(SoundEvents.GLASS_BREAK)));

		exporter.add(ModSoundEvents.ITEM_RELIC_TOGGLE, of().subtitle("subtitles.lostrelics.item.relic.toggle")
				.sound(ofEvent(SoundEvents.EXPERIENCE_ORB_PICKUP)));
	}

	@Override
	public String getName() {
		return LostRelics.MOD_ID + "_sounds";
	}
}
