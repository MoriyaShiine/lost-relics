/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.block.entity;

import moriyashiine.lostrelics.common.init.ModBlockEntityTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jspecify.annotations.Nullable;

public class AltarBlockEntity extends BlockEntity {
	private ItemStack stack = ItemStack.EMPTY;

	public AltarBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntityTypes.ALTAR, pos, state);
	}

	@Override
	protected void readData(ReadView view) {
		super.readData(view);
		stack = view.read("Stack", ItemStack.CODEC).orElse(ItemStack.EMPTY);
	}

	@Override
	protected void writeData(WriteView view) {
		super.writeData(view);
		if (!stack.isEmpty()) {
			view.put("Stack", ItemStack.CODEC, stack);
		}
	}

	@Override
	public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
		return createNbt(registries);
	}

	@Override
	public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.create(this);
	}

	@Override
	public void onBlockReplaced(BlockPos pos, BlockState oldState) {
		if (world != null) {
			ItemScatterer.spawn(world, pos, DefaultedList.ofSize(1, stack));
		}
	}

	public ItemStack getStack() {
		return stack;
	}

	public void setStack(ItemStack stack) {
		this.stack = stack;
		markDirty();
		if (world != null) {
			world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_LISTENERS);
		}
	}
}
