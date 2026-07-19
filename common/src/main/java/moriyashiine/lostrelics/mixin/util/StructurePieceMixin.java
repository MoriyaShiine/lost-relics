package moriyashiine.lostrelics.mixin.util;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import moriyashiine.lostrelics.common.init.LostRelicsBlocks;
import moriyashiine.lostrelics.common.world.level.block.AltarBlock;
import moriyashiine.lostrelics.common.world.level.block.entity.AltarBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(StructurePiece.class)
public class StructurePieceMixin {
	@WrapOperation(method = "createChest(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/world/level/levelgen/structure/BoundingBox;Lnet/minecraft/util/RandomSource;Lnet/minecraft/core/BlockPos;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/world/level/block/state/BlockState;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/ServerLevelAccessor;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"))
	private boolean lostrelics$generateAltar(ServerLevelAccessor instance, BlockPos pos, BlockState blockState, int i, Operation<Boolean> original, @Local(argsOnly = true) RandomSource random, @Local(argsOnly = true) ResourceKey<LootTable> lootTable) {
		if (lootTable == BuiltInLootTables.JUNGLE_TEMPLE && isExposedHorizontally(instance, pos)) {
			AltarBlock altarBlock = (AltarBlock) LostRelicsBlocks.JUNGLE_ALTAR;
			boolean call = original.call(instance, pos, altarBlock.defaultBlockState(), i);
			tryPopulateAltar(instance, pos, random, altarBlock);
			return call;
		}
		return original.call(instance, pos, blockState, i);
	}

	@Unique
	private static boolean isExposedHorizontally(ServerLevelAccessor level, BlockPos pos) {
		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		for (Direction direction : Direction.values()) {
			if (direction.getAxis() != Direction.Axis.Y) {
				mutable.set(pos).move(direction);
				if (!level.getBlockState(mutable).getCollisionShape(level, mutable).isEmpty()) {
					return false;
				}
			}
		}
		return true;
	}

	@Unique
	private static void tryPopulateAltar(ServerLevelAccessor level, BlockPos pos, RandomSource random, AltarBlock block) {
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
