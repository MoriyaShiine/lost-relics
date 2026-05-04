/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.datagen.provider;

import moriyashiine.lostrelics.common.LostRelics;
import moriyashiine.lostrelics.common.init.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.LocationPredicate;
import net.minecraft.advancements.criterion.PlayerTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementProvider extends FabricAdvancementProvider {
	public ModAdvancementProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	public void generateAdvancement(HolderLookup.Provider registries, Consumer<AdvancementHolder> consumer) {
		AdvancementHolder findJunglePyramid = Advancement.Builder.advancement()
				.parent(Identifier.tryParse("adventure/root"))
				.display(Items.MOSSY_COBBLESTONE,
						Component.translatable("advancements.lostrelics.adventure.find_jungle_pyramid.title"),
						Component.translatable("advancements.lostrelics.adventure.find_jungle_pyramid.description"),
						null,
						AdvancementType.TASK,
						true,
						true,
						false)
				.addCriterion("jungle_pyramid", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inStructure(registries.lookupOrThrow(Registries.STRUCTURE).getOrThrow(BuiltinStructures.JUNGLE_TEMPLE))))
				.save(consumer, LostRelics.id("adventure/find_jungle_pyramid").toString());

		Advancement.Builder.advancement()
				.parent(findJunglePyramid)
				.display(ModItems.CURSED_AMULET,
						Component.translatable("advancements.lostrelics.adventure.cursed_amulet.title"),
						Component.translatable("advancements.lostrelics.adventure.cursed_amulet.description"),
						null,
						AdvancementType.TASK,
						true,
						true,
						false)
				.addCriterion("has_relic", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CURSED_AMULET))
				.save(consumer, LostRelics.id("adventure/cursed_amulet").toString());
		Advancement.Builder.advancement()
				.parent(findJunglePyramid)
				.display(ModItems.SMOKING_MIRROR,
						Component.translatable("advancements.lostrelics.adventure.smoking_mirror.title"),
						Component.translatable("advancements.lostrelics.adventure.smoking_mirror.description"),
						null,
						AdvancementType.TASK,
						true,
						true,
						false)
				.addCriterion("has_relic", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.SMOKING_MIRROR))
				.save(consumer, LostRelics.id("adventure/smoking_mirror").toString());
		Advancement.Builder.advancement()
				.parent(findJunglePyramid)
				.display(ModItems.TRIPLE_TOOTHED_SNAKE,
						Component.translatable("advancements.lostrelics.adventure.triple_toothed_snake.title"),
						Component.translatable("advancements.lostrelics.adventure.triple_toothed_snake.description"),
						null,
						AdvancementType.TASK,
						true,
						true,
						false)
				.addCriterion("has_relic", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.TRIPLE_TOOTHED_SNAKE))
				.save(consumer, LostRelics.id("adventure/triple_toothed_snake").toString());
		Advancement.Builder.advancement()
				.parent(findJunglePyramid)
				.display(ModItems.TURQUOISE_EYE,
						Component.translatable("advancements.lostrelics.adventure.turquoise_eye.title"),
						Component.translatable("advancements.lostrelics.adventure.turquoise_eye.description"),
						null,
						AdvancementType.TASK,
						true,
						true,
						false)
				.addCriterion("has_relic", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.TURQUOISE_EYE))
				.save(consumer, LostRelics.id("adventure/turquoise_eye").toString());
	}
}
