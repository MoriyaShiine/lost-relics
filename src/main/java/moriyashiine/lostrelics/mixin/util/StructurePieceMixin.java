/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.mixin.util;

import moriyashiine.lostrelics.common.init.ModBlocks;
import moriyashiine.lostrelics.common.world.level.block.AltarBlock;
import moriyashiine.lostrelics.common.world.level.block.entity.AltarBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StructurePiece.class)
public class StructurePieceMixin {
	@Inject(method = "createChest(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/world/level/levelgen/structure/BoundingBox;Lnet/minecraft/util/RandomSource;Lnet/minecraft/core/BlockPos;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/world/level/block/state/BlockState;)Z", at = @At("HEAD"), cancellable = true)
	private void lostrelics$generateAltar(ServerLevelAccessor level, BoundingBox chunkBB, RandomSource random, BlockPos pos, ResourceKey<LootTable> lootTable, @Nullable BlockState blockState, CallbackInfoReturnable<Boolean> cir) {
		if (lootTable == BuiltInLootTables.JUNGLE_TEMPLE && random.nextBoolean()) {
			placeAltar(level, pos, random, (AltarBlock) ModBlocks.JUNGLE_ALTAR);
			cir.setReturnValue(true);
		}
	}

	@Unique
	private static void placeAltar(ServerLevelAccessor level, BlockPos pos, RandomSource random, AltarBlock block) {
		level.setBlock(pos, block.defaultBlockState(), Block.UPDATE_CLIENTS);
		if (level.getBlockEntity(pos) instanceof AltarBlockEntity altarBlockEntity) {
			Item relic;
			do {
				relic = BuiltInRegistries.ITEM.getRandom(random).get().value();
			}
			while (!relic.getDefaultInstance().is(block.relicTag));

			altarBlockEntity.setStack(relic.getDefaultInstance());
		}
	}
}
