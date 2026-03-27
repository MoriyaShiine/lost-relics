/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.common.world.level.block;

import com.mojang.serialization.MapCodec;
import moriyashiine.lostrelics.common.init.ModSoundEvents;
import moriyashiine.lostrelics.common.tag.ModItemTags;
import moriyashiine.lostrelics.common.world.level.block.entity.AltarBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

public class AltarBlock extends HorizontalDirectionalBlock implements EntityBlock {
	public static final MapCodec<AltarBlock> CODEC = simpleCodec(AltarBlock::new);

	private static final VoxelShape SHAPE = box(0.5, 0, 0.5, 15.5, 15, 15.5);

	public final TagKey<Item> relicTag;

	public AltarBlock(Properties properties, TagKey<Item> relicTag) {
		super(properties);
		this.relicTag = relicTag;
	}

	public AltarBlock(Properties properties) {
		this(properties, ModItemTags.RELICS);
	}

	@Override
	protected MapCodec<AltarBlock> codec() {
		return CODEC;
	}

	@Override
	public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
		return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection());
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState) {
		return new AltarBlockEntity(worldPosition, blockState);
	}

	@Override
	protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		if (level.getBlockEntity(pos) instanceof AltarBlockEntity altarBlockEntity) {
			if (altarBlockEntity.getStack().isEmpty()) {
				if (stack.is(relicTag)) {
					if (!level.isClientSide()) {
						level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1, 1);
						altarBlockEntity.setStack(stack.consumeAndReturn(1, player));
					}
					return InteractionResult.SUCCESS;
				}
			} else if (stack.is(relicTag)) {
				if (level instanceof ServerLevel serverLevel) {
					Item newRelic;
					do {
						newRelic = BuiltInRegistries.ITEM.getRandom(player.getRandom()).get().value();
					}
					while (stack.is(newRelic) || altarBlockEntity.getStack().is(newRelic) || !newRelic.getDefaultInstance().is(relicTag));
					altarBlockEntity.setStack(newRelic.getDefaultInstance());
					stack.consume(1, player);
					level.playSound(null, pos, ModSoundEvents.BLOCK_ALTAR_CONVERT, SoundSource.BLOCKS, 1, 1);
					float dX = Mth.nextFloat(level.getRandom(), -0.2F, 0.2F);
					float dY = Mth.nextFloat(level.getRandom(), -0.2F, 0.2F);
					float dZ = Mth.nextFloat(level.getRandom(), -0.2F, 0.2F);
					serverLevel.sendParticles(ParticleTypes.SMOKE, pos.getX() + 0.5, pos.getY() + 1.4, pos.getZ() + 0.5, 48, dX, dY, dZ, 0.15);
				}
				return InteractionResult.SUCCESS;
			} else if (stack.isEmpty()) {
				if (!level.isClientSide()) {
					level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1, 1);
					player.handleExtraItemsCreatedOnUse(altarBlockEntity.getStack().copy());
					altarBlockEntity.setStack(ItemStack.EMPTY);
				}
				return InteractionResult.SUCCESS;
			}
		}
		return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
}
