/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.common.world.entity.projectile.arrow;

import moriyashiine.lostrelics.common.init.LostRelicsEntityTypes;
import moriyashiine.lostrelics.common.init.LostRelicsItems;
import moriyashiine.lostrelics.common.init.LostRelicsSoundEvents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TaintedBloodCrystal extends AbstractArrow {
	private static final ParticleOptions PARTICLE = new ItemParticleOption(ParticleTypes.ITEM, LostRelicsItems.TAINTED_BLOOD_CRYSTAL);

	private final List<MobEffectInstance> effects = new ArrayList<>();

	public TaintedBloodCrystal(EntityType<TaintedBloodCrystal> type, Level level) {
		super(type, level);
	}

	public TaintedBloodCrystal(Level level, double x, double y, double z, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
		super(LostRelicsEntityTypes.TAINTED_BLOOD_CRYSTAL, x, y, z, level, pickupItemStack, firedFromWeapon);
		pickupItemStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).getAllEffects().forEach(instance -> effects.add(new MobEffectInstance(instance)));
	}

	public TaintedBloodCrystal(Level level, LivingEntity owner, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
		super(LostRelicsEntityTypes.TAINTED_BLOOD_CRYSTAL, owner, level, pickupItemStack, firedFromWeapon);
		pickupItemStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).getAllEffects().forEach(instance -> effects.add(new MobEffectInstance(instance)));
	}

	@Override
	protected ItemStack getDefaultPickupItem() {
		return LostRelicsItems.TAINTED_BLOOD_CRYSTAL.getDefaultInstance();
	}

	@Override
	protected void readAdditionalSaveData(ValueInput input) {
		super.readAdditionalSaveData(input);
		effects.clear();
		effects.addAll(input.read("Effects", MobEffectInstance.CODEC.listOf()).orElse(List.of()));
	}

	@Override
	protected void addAdditionalSaveData(ValueOutput output) {
		super.addAdditionalSaveData(output);
		output.store("Effects", MobEffectInstance.CODEC.listOf(), effects);
	}

	@Override
	protected SoundEvent getDefaultHitGroundSoundEvent() {
		return LostRelicsSoundEvents.ENTITY_TAINTED_BLOOD_CRYSTAL_SHATTER;
	}

	@Override
	protected void doPostHurtEffects(LivingEntity mob) {
		if (level() instanceof ServerLevel level) {
			playSound(getDefaultHitGroundSoundEvent(), 1, 1.2F / (random.nextFloat() * 0.2F + 0.9F));
			Entity cause = getEffectSource();
			effects.forEach(instance -> mob.addEffect(instance, cause));
			addParticles(level);
		}
	}

	@Override
	protected void onHitBlock(BlockHitResult hitResult) {
		BlockState state = level().getBlockState(hitResult.getBlockPos());
		state.onProjectileHit(level(), state, hitResult, this);
		if (level() instanceof ServerLevel level) {
			playSound(getDefaultHitGroundSoundEvent(), 1, 1.2F / (random.nextFloat() * 0.2F + 0.9F));
			addParticles(level);
			discard();
		}
	}

	private void addParticles(ServerLevel level) {
		level.sendParticles(PARTICLE, getX(), getY(), getZ(), 8, getBbWidth() / 2, getBbHeight() / 2, getBbWidth() / 2, 0);
	}
}
