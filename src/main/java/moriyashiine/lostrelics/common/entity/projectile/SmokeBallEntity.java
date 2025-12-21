/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.entity.projectile;

import moriyashiine.lostrelics.common.init.ModEntityTypes;
import moriyashiine.strawberrylib.api.module.SLibClientUtils;
import moriyashiine.strawberrylib.api.objects.enums.ParticleAnchor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LazyEntityReference;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class SmokeBallEntity extends Entity {
	@Nullable
	private LazyEntityReference<LivingEntity> owner, target;
	private float damage;

	public SmokeBallEntity(EntityType<?> type, World world) {
		super(type, world);
		noClip = true;
	}

	public SmokeBallEntity(World world, LivingEntity owner, LivingEntity target, float damage) {
		this(ModEntityTypes.SMOKE_BALL, world);
		setPosition(owner.getEyePos());
		this.owner = LazyEntityReference.of(owner);
		this.target = LazyEntityReference.of(target);
		this.damage = damage;
	}

	@Override
	protected void readCustomData(ReadView view) {
		owner = LazyEntityReference.fromData(view, "Owner");
		target = LazyEntityReference.fromData(view, "Target");
		damage = view.getFloat("Damage", 0);
	}

	@Override
	protected void writeCustomData(WriteView view) {
		LazyEntityReference.writeData(owner, view, "Owner");
		LazyEntityReference.writeData(target, view, "Target");
		view.putFloat("Damage", damage);
	}

	@Override
	protected void initDataTracker(DataTracker.Builder builder) {
	}

	@Override
	public boolean damage(ServerWorld world, DamageSource source, float amount) {
		return false;
	}

	@Override
	public void tick() {
		super.tick();
		if (getEntityWorld() instanceof ServerWorld world) {
			@Nullable LivingEntity owner = getOwner();
			@Nullable LivingEntity target = getTarget();
			if (owner == null || owner.isRemoved() || world != owner.getEntityWorld() || target == null || target.isRemoved() || world != target.getEntityWorld() || !world.isChunkLoaded(getBlockPos())) {
				discard();
			} else {
				addVelocity(new Vec3d(target.getX() - getX(), target.getEyeY() - getY(), target.getZ() - getZ()));
				setVelocity(getVelocity().normalize().multiply(0.5));
				if (getBoundingBox().expand(1).contains(target.getEyePos()) && target.damage(world, getDamageSources().indirectMagic(owner, owner), damage)) {
					discard();
				}
			}
		} else {
			SLibClientUtils.addParticles(this, ParticleTypes.SMOKE, 16, ParticleAnchor.BODY);
		}
		setPosition(getX() + getVelocity().getX(), getY() + getVelocity().getY(), getZ() + getVelocity().getZ());
	}

	@Nullable
	private LivingEntity getOwner() {
		return LazyEntityReference.getLivingEntity(owner, getEntityWorld());
	}

	@Nullable
	private LivingEntity getTarget() {
		return LazyEntityReference.getLivingEntity(target, getEntityWorld());
	}
}
