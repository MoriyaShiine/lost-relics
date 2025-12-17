/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.data.provider;

import moriyashiine.lostrelics.common.LostRelics;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.TickCriterion;
import net.minecraft.item.Items;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.structure.StructureKeys;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementProvider extends FabricAdvancementProvider {
	public ModAdvancementProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
		super(output, registryLookup);
	}

	@Override
	public void generateAdvancement(RegistryWrapper.WrapperLookup registryLookup, Consumer<AdvancementEntry> consumer) {
		Advancement.Builder.create()
				.parent(Identifier.tryParse("adventure/root"))
				.display(Items.MOSSY_COBBLESTONE,
						Text.translatable("advancements.lostrelics.adventure.find_jungle_pyramid.title"),
						Text.translatable("advancements.lostrelics.adventure.find_jungle_pyramid.description"),
						null,
						AdvancementFrame.TASK,
						true,
						true,
						false)
				.criterion("jungle_pyramid", TickCriterion.Conditions.createLocation(LocationPredicate.Builder.createStructure(registryLookup.getOrThrow(RegistryKeys.STRUCTURE).getOrThrow(StructureKeys.JUNGLE_PYRAMID))))
				.build(consumer, LostRelics.id("adventure/find_jungle_pyramid").toString());
	}
}
