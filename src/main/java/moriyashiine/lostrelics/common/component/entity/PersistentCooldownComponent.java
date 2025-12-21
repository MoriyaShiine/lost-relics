/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.component.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

import java.util.ArrayList;
import java.util.List;

public class PersistentCooldownComponent implements AutoSyncedComponent, ServerTickingComponent {
	private final PlayerEntity obj;
	private final List<CooldownEntry> cooldownEntries = new ArrayList<>();
	private boolean sentCooldowns = false;

	public PersistentCooldownComponent(PlayerEntity obj) {
		this.obj = obj;
	}

	@Override
	public void serverTick() {
		if (!sentCooldowns) {
			sentCooldowns = true;
			cooldownEntries.forEach(cooldownEntry -> obj.getItemCooldownManager().set(cooldownEntry.getStack(), cooldownEntry.getCooldown()));
		}
		for (int i = cooldownEntries.size() - 1; i >= 0; i--) {
			CooldownEntry cooldownEntry = cooldownEntries.get(i);
			if (--cooldownEntry.cooldown == 0) {
				cooldownEntries.remove(i);
			}
		}
	}

	@Override
	public void readData(ReadView readView) {
		cooldownEntries.clear();
		cooldownEntries.addAll(readView.read("CooldownEntries", CooldownEntry.CODEC.listOf()).orElse(List.of()));
	}

	@Override
	public void writeData(WriteView writeView) {
		writeView.put("CooldownEntries", CooldownEntry.CODEC.listOf(), cooldownEntries);
	}

	public void setCooldown(ItemStack stack, int cooldown) {
		for (CooldownEntry entry : cooldownEntries) {
			if (ItemStack.areEqual(stack, entry.stack)) {
				entry.cooldown = cooldown;
				return;
			}
		}
		cooldownEntries.add(new CooldownEntry(stack, cooldown));
	}

	private static class CooldownEntry {
		private static final Codec<CooldownEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
						ItemStack.CODEC.fieldOf("stack").forGetter(CooldownEntry::getStack),
						Codec.INT.fieldOf("cooldown").forGetter(CooldownEntry::getCooldown))
				.apply(instance, CooldownEntry::new));

		private final ItemStack stack;
		private int cooldown;

		public CooldownEntry(ItemStack stack, int cooldown) {
			this.stack = stack;
			this.cooldown = cooldown;
		}

		public ItemStack getStack() {
			return stack;
		}

		public int getCooldown() {
			return cooldown;
		}
	}
}
