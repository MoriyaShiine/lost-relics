/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.block;

import com.mojang.serialization.MapCodec;
import moriyashiine.lostrelics.common.block.entity.AltarBlockEntity;
import moriyashiine.lostrelics.common.init.ModSoundEvents;
import moriyashiine.lostrelics.common.tag.ModItemTags;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jspecify.annotations.Nullable;

public class AltarBlock extends HorizontalFacingBlock implements BlockEntityProvider {
	public static final MapCodec<AltarBlock> CODEC = createCodec(AltarBlock::new);

	private static final VoxelShape SHAPE = createCuboidShape(0.5, 0, 0.5, 15.5, 15, 15.5);

	private final TagKey<Item> relicTag;

	public AltarBlock(Settings settings, TagKey<Item> relicTag) {
		super(settings);
		this.relicTag = relicTag;
	}

	public AltarBlock(Settings settings) {
		this(settings, ModItemTags.RELICS);
	}

	@Override
	protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
		return CODEC;
	}

	@Override
	public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
		return super.getPlacementState(ctx).with(FACING, ctx.getHorizontalPlayerFacing());
	}

	@Override
	protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPE;
	}

	@Override
	public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new AltarBlockEntity(pos, state);
	}

	@Override
	protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (world.getBlockEntity(pos) instanceof AltarBlockEntity altarBlockEntity) {
			if (altarBlockEntity.getStack().isEmpty()) {
				if (stack.isIn(relicTag)) {
					if (!world.isClient()) {
						world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1, 1);
						altarBlockEntity.setStack(stack.splitUnlessCreative(1, player));
					}
					return ActionResult.SUCCESS;
				}
			} else if (stack.isIn(relicTag)) {
				if (!world.isClient()) {
					Item newRelic;
					do {
						newRelic = Registries.ITEM.getRandom(player.getRandom()).get().value();
					}
					while (stack.isOf(newRelic) || altarBlockEntity.getStack().isOf(newRelic) || !newRelic.getDefaultStack().isIn(relicTag));
					altarBlockEntity.setStack(newRelic.getDefaultStack());
					stack.decrementUnlessCreative(1, player);
					world.playSound(null, pos, ModSoundEvents.BLOCK_ALTAR_CONVERT, SoundCategory.BLOCKS, 1, 1);
					float dX = MathHelper.nextFloat(world.getRandom(), -0.2F, 0.2F);
					float dY = MathHelper.nextFloat(world.getRandom(), -0.2F, 0.2F);
					float dZ = MathHelper.nextFloat(world.getRandom(), -0.2F, 0.2F);
					((ServerWorld) world).spawnParticles(ParticleTypes.SMOKE, pos.getX() + 0.5, pos.getY() + 1.4, pos.getZ() + 0.5, 48, dX, dY, dZ, 0.15);
				}
				return ActionResult.SUCCESS;
			} else if (stack.isEmpty()) {
				if (!world.isClient()) {
					world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1, 1);
					player.giveOrDropStack(altarBlockEntity.getStack().copy());
					altarBlockEntity.setStack(ItemStack.EMPTY);
				}
				return ActionResult.SUCCESS;
			}
		}
		return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
}
