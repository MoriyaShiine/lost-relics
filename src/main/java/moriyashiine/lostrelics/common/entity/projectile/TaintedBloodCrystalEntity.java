/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.entity.projectile;

import moriyashiine.lostrelics.common.init.ModEntityTypes;
import moriyashiine.lostrelics.common.init.ModItems;
import moriyashiine.lostrelics.common.init.ModSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TaintedBloodCrystalEntity extends PersistentProjectileEntity {
	private static final ParticleEffect PARTICLE = new ItemStackParticleEffect(ParticleTypes.ITEM, new ItemStack(ModItems.TAINTED_BLOOD_CRYSTAL));

	private final List<StatusEffectInstance> effects = new ArrayList<>();

	public TaintedBloodCrystalEntity(EntityType<TaintedBloodCrystalEntity> entityType, World world) {
		super(entityType, world);
	}

	public TaintedBloodCrystalEntity(World world, double x, double y, double z, ItemStack stack, @Nullable ItemStack shotFrom) {
		super(ModEntityTypes.TAINTED_BLOOD_CRYSTAL, x, y, z, world, stack, shotFrom);
		stack.getOrDefault(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT).getEffects().forEach(instance -> effects.add(new StatusEffectInstance(instance)));
	}

	public TaintedBloodCrystalEntity(World world, LivingEntity owner, ItemStack stack, @Nullable ItemStack shotFrom) {
		super(ModEntityTypes.TAINTED_BLOOD_CRYSTAL, owner, world, stack, shotFrom);
		stack.getOrDefault(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT).getEffects().forEach(instance -> effects.add(new StatusEffectInstance(instance)));
	}

	@Override
	protected ItemStack getDefaultItemStack() {
		return ModItems.TAINTED_BLOOD_CRYSTAL.getDefaultStack();
	}

	@Override
	protected void readCustomData(ReadView view) {
		super.readCustomData(view);
		effects.clear();
		effects.addAll(view.read("Effects", StatusEffectInstance.CODEC.listOf()).orElse(List.of()));
	}

	@Override
	protected void writeCustomData(WriteView view) {
		super.writeCustomData(view);
		view.put("Effects", StatusEffectInstance.CODEC.listOf(), effects);
	}

	@Override
	protected SoundEvent getHitSound() {
		return ModSoundEvents.ENTITY_TAINTED_BLOOD_CRYSTAL_SHATTER;
	}

	@Override
	protected void onHit(LivingEntity target) {
		if (!getEntityWorld().isClient()) {
			playSound(getHitSound(), 1, 1.2F / (random.nextFloat() * 0.2F + 0.9F));
			Entity cause = getEffectCause();
			effects.forEach(instance -> target.addStatusEffect(instance, cause));
			addParticles();
		}
	}

	@Override
	protected void onBlockHit(BlockHitResult blockHitResult) {
		BlockState state = getEntityWorld().getBlockState(blockHitResult.getBlockPos());
		state.onProjectileHit(getEntityWorld(), state, blockHitResult, this);
		if (!getEntityWorld().isClient()) {
			playSound(getHitSound(), 1, 1.2F / (random.nextFloat() * 0.2F + 0.9F));
			addParticles();
			discard();
		}
	}

	private void addParticles() {
		((ServerWorld) getEntityWorld()).spawnParticles(PARTICLE, getX(), getY(), getZ(), 8, getWidth() / 2, getHeight() / 2, getWidth() / 2, 0);
	}
}
