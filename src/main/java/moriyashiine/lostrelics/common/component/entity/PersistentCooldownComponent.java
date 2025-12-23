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
import net.minecraft.util.math.MathHelper;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

import java.util.ArrayList;
import java.util.List;

public class PersistentCooldownComponent implements AutoSyncedComponent, CommonTickingComponent {
	public static PlayerEntity currentPlayer = null;

	private final List<CooldownEntry> cooldownEntries = new ArrayList<>();

	@Override
	public void tick() {
		for (int i = cooldownEntries.size() - 1; i >= 0; i--) {
			CooldownEntry cooldownEntry = cooldownEntries.get(i);
			if (++cooldownEntry.progress == cooldownEntry.cooldown) {
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

	public float getCooldownProgress(ItemStack stack, float tickProgress) {
		for (CooldownEntry entry : cooldownEntries) {
			if (ItemStack.areEqual(stack, entry.getStack())) {
				float delta = entry.cooldown - (entry.progress + tickProgress);
				return MathHelper.clamp(delta / entry.cooldown, 0, 1);
			}
		}
		return 0;
	}

	public void setCooldown(ItemStack stack, int cooldown) {
		for (CooldownEntry entry : cooldownEntries) {
			if (ItemStack.areEqual(stack, entry.stack)) {
				entry.progress = 0;
				entry.cooldown = cooldown;
				return;
			}
		}
		cooldownEntries.add(new CooldownEntry(stack, 0, cooldown));
	}

	private static class CooldownEntry {
		private static final Codec<CooldownEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
						ItemStack.CODEC.fieldOf("stack").forGetter(CooldownEntry::getStack),
						Codec.INT.fieldOf("progress").forGetter(CooldownEntry::getProgress),
						Codec.INT.fieldOf("cooldown").forGetter(CooldownEntry::getCooldown))
				.apply(instance, CooldownEntry::new));

		private final ItemStack stack;
		private int progress, cooldown;

		public CooldownEntry(ItemStack stack, int progress, int cooldown) {
			this.stack = stack;
			this.progress = progress;
			this.cooldown = cooldown;
		}

		public ItemStack getStack() {
			return stack;
		}

		public int getProgress() {
			return progress;
		}

		public int getCooldown() {
			return cooldown;
		}
	}
}
