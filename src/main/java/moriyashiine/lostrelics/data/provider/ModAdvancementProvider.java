/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.data.provider;

import moriyashiine.lostrelics.common.LostRelics;
import moriyashiine.lostrelics.common.init.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
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
		AdvancementEntry findJunglePyramid = Advancement.Builder.create()
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

		Advancement.Builder.create()
				.parent(findJunglePyramid)
				.display(ModItems.CURSED_AMULET,
						Text.translatable("advancements.lostrelics.adventure.cursed_amulet.title"),
						Text.translatable("advancements.lostrelics.adventure.cursed_amulet.description"),
						null,
						AdvancementFrame.TASK,
						true,
						true,
						false)
				.criterion("has_relic", InventoryChangedCriterion.Conditions.items(ModItems.CURSED_AMULET))
				.build(consumer, LostRelics.id("adventure/cursed_amulet").toString());
		Advancement.Builder.create()
				.parent(findJunglePyramid)
				.display(ModItems.SMOKING_MIRROR,
						Text.translatable("advancements.lostrelics.adventure.smoking_mirror.title"),
						Text.translatable("advancements.lostrelics.adventure.smoking_mirror.description"),
						null,
						AdvancementFrame.TASK,
						true,
						true,
						false)
				.criterion("has_relic", InventoryChangedCriterion.Conditions.items(ModItems.SMOKING_MIRROR))
				.build(consumer, LostRelics.id("adventure/smoking_mirror").toString());
		Advancement.Builder.create()
				.parent(findJunglePyramid)
				.display(ModItems.TRIPLE_TOOTHED_SNAKE,
						Text.translatable("advancements.lostrelics.adventure.triple_toothed_snake.title"),
						Text.translatable("advancements.lostrelics.adventure.triple_toothed_snake.description"),
						null,
						AdvancementFrame.TASK,
						true,
						true,
						false)
				.criterion("has_relic", InventoryChangedCriterion.Conditions.items(ModItems.TRIPLE_TOOTHED_SNAKE))
				.build(consumer, LostRelics.id("adventure/triple_toothed_snake").toString());
		Advancement.Builder.create()
				.parent(findJunglePyramid)
				.display(ModItems.TURQUOISE_EYE,
						Text.translatable("advancements.lostrelics.adventure.turquoise_eye.title"),
						Text.translatable("advancements.lostrelics.adventure.turquoise_eye.description"),
						null,
						AdvancementFrame.TASK,
						true,
						true,
						false)
				.criterion("has_relic", InventoryChangedCriterion.Conditions.items(ModItems.TURQUOISE_EYE))
				.build(consumer, LostRelics.id("adventure/turquoise_eye").toString());
	}
}
