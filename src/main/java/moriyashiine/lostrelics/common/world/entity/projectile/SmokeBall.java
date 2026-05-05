/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.common.world.entity.projectile;

import moriyashiine.lostrelics.common.init.ModEntityTypes;
import moriyashiine.strawberrylib.api.module.SLibClientUtils;
import moriyashiine.strawberrylib.api.objects.enums.ParticleAnchor;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class SmokeBall extends Entity {
	@Nullable
	private EntityReference<LivingEntity> owner, target;
	private float damage;

	public SmokeBall(EntityType<?> type, Level level) {
		super(type, level);
		noPhysics = true;
	}

	public SmokeBall(Level level, LivingEntity owner, LivingEntity target, float damage) {
		this(ModEntityTypes.SMOKE_BALL, level);
		setPos(owner.getEyePosition());
		this.owner = EntityReference.of(owner);
		this.target = EntityReference.of(target);
		this.damage = damage;
	}

	@Override
	protected void readAdditionalSaveData(ValueInput input) {
		owner = EntityReference.read(input, "Owner");
		target = EntityReference.read(input, "Target");
		damage = input.getFloatOr("Damage", 0);
	}

	@Override
	protected void addAdditionalSaveData(ValueOutput output) {
		EntityReference.store(owner, output, "Owner");
		EntityReference.store(target, output, "Target");
		output.putFloat("Damage", damage);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
	}

	@Override
	public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
		return false;
	}

	@Override
	public void tick() {
		super.tick();
		if (level() instanceof ServerLevel level) {
			LivingEntity owner = getOwner();
			LivingEntity target = getTarget();
			if (owner == null || owner.isRemoved() || level != owner.level() || target == null || target.isRemoved() || level != target.level() || !level.hasChunkAt(blockPosition())) {
				discard();
			} else {
				push(new Vec3(target.getX() - getX(), target.getEyeY() - getY(), target.getZ() - getZ()));
				setDeltaMovement(getDeltaMovement().normalize().scale(0.5));
				if (getBoundingBox().inflate(1).contains(target.getEyePosition()) && target.hurtServer(level, damageSources().indirectMagic(owner, owner), damage)) {
					discard();
				}
			}
		} else {
			SLibClientUtils.addParticles(this, ParticleTypes.SMOKE, 16, ParticleAnchor.BODY);
		}
		setPos(getX() + getDeltaMovement().x(), getY() + getDeltaMovement().y(), getZ() + getDeltaMovement().z());
	}

	private @Nullable LivingEntity getOwner() {
		return EntityReference.getLivingEntity(owner, level());
	}

	private @Nullable LivingEntity getTarget() {
		return EntityReference.getLivingEntity(target, level());
	}
}
