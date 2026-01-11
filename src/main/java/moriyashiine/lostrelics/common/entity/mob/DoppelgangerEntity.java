/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.entity.mob;

import moriyashiine.lostrelics.common.entity.mob.ai.goal.*;
import moriyashiine.lostrelics.common.init.ModEntityTypes;
import moriyashiine.lostrelics.common.init.ModItems;
import moriyashiine.lostrelics.common.util.LostRelicsUtil;
import moriyashiine.strawberrylib.api.module.SLibClientUtils;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import moriyashiine.strawberrylib.api.objects.enums.ParticleAnchor;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class DoppelgangerEntity extends PathAwareEntity {
	private static final TrackedData<Optional<LazyEntityReference<LivingEntity>>> COPIED_ENTITY = DataTracker.registerData(DoppelgangerEntity.class, TrackedDataHandlerRegistry.LAZY_ENTITY_REFERENCE);

	@Nullable
	private LazyEntityReference<LivingEntity> owner;
	private boolean mirrorDemon = false;
	private int ticksExisted = 0;

	public DoppelgangerEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
		super(entityType, world);
	}

	public DoppelgangerEntity(World world, LivingEntity owner, boolean mirrorDemon) {
		this(ModEntityTypes.DOPPELGANGER, world);
		this.owner = LazyEntityReference.of(owner);
		this.mirrorDemon = mirrorDemon;
	}

	public static DefaultAttributeContainer.Builder createDoppelgangerAttributes() {
		return MobEntity.createMobAttributes().add(EntityAttributes.ATTACK_DAMAGE, 1).add(EntityAttributes.MOVEMENT_SPEED, 0.35);
	}

	@Override
	protected void readCustomData(ReadView view) {
		owner = LazyEntityReference.fromData(view, "Owner");
		dataTracker.set(COPIED_ENTITY, Optional.ofNullable(LazyEntityReference.fromData(view, "CopiedEntity")));
		mirrorDemon = view.getBoolean("MirrorDemon", false);
		ticksExisted = view.getInt("TicksExisted", 0);
	}

	@Override
	protected void writeCustomData(WriteView view) {
		LazyEntityReference.writeData(owner, view, "Owner");
		LazyEntityReference.writeData(getCopiedEntityReference(), view, "CopiedEntity");
		view.putBoolean("MirrorDemon", mirrorDemon);
		view.putInt("TicksExisted", ticksExisted);
	}

	@Override
	protected void initDataTracker(DataTracker.Builder builder) {
		super.initDataTracker(builder);
		builder.add(COPIED_ENTITY, Optional.empty());
	}

	@Override
	protected void initGoals() {
		goalSelector.add(0, new SwimGoal(this));
		DoppelgangerRangedAttackGoal rangedAttackGoal = new DoppelgangerRangedAttackGoal(this);
		goalSelector.add(1, rangedAttackGoal);
		goalSelector.add(2, new DoppelgangerMeleeAttackGoal(this, 1, false, rangedAttackGoal));
		goalSelector.add(3, new DoppelgangerFollowOwnerGoal(this, 1, 10, 2));
		goalSelector.add(4, new WanderAroundFarGoal(this, 1));
		goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 8));
		goalSelector.add(5, new LookAroundGoal(this));
		targetSelector.add(0, new DoppelgangerTrackOwnerAttackerGoal(this));
		targetSelector.add(1, new DoppelgangerAttackWithOwnerGoal(this));
	}

	@Override
	public boolean damage(ServerWorld world, DamageSource source, float amount) {
		if (mirrorDemon) {
			amount /= 2;
		}
		return super.damage(world, source, amount);
	}

	@Override
	public void tick() {
		super.tick();
		ticksExisted++;
		if (getEntityWorld().isClient()) {
			SLibClientUtils.addParticles(this, ParticleTypes.SMOKE, 1, ParticleAnchor.BODY);
		} else {
			@Nullable LivingEntity owner = getOwner();
			if (ticksExisted >= 600 || owner == null || owner.isRemoved() || getEntityWorld() != owner.getEntityWorld() || distanceTo(owner) > 32) {
				SLibUtils.addParticles(this, ParticleTypes.SMOKE, 128, ParticleAnchor.BODY);
				discard();
				return;
			} else if (owner instanceof PlayerEntity player) {
				ItemStack relicStack = LostRelicsUtil.getRelic(player, ModItems.SMOKING_MIRROR);
				if (!relicStack.isEmpty()) {
					LostRelicsUtil.setCooldown(player, relicStack, 600);
				}
			}
			@Nullable LivingEntity targetCopy = owner;
			if (mirrorDemon && getTarget() instanceof PlayerLikeEntity playerLike) {
				targetCopy = playerLike;
			}
			if (getCopiedEntity() != targetCopy) {
				setCopiedEntity(targetCopy);
			}
			@Nullable LivingEntity copiedEntity = getCopiedEntity();
			if (copiedEntity != null) {
				for (EquipmentSlot slot : EquipmentSlot.values()) {
					equipStack(slot, copiedEntity.getEquippedStack(slot).copy());
				}
			}
		}
	}

	@Override
	public void onDeath(DamageSource damageSource) {
		super.onDeath(damageSource);
		if (!getEntityWorld().isClient()) {
			SLibUtils.addParticles(this, ParticleTypes.SMOKE, 128, ParticleAnchor.BODY);
			discard();
		}
	}

	@Nullable
	@Override
	public Team getScoreboardTeam() {
		Team team = super.getScoreboardTeam();
		if (team != null) {
			return team;
		} else {
			LivingEntity owner = getOwner();
			if (owner != null) {
				return owner.getScoreboardTeam();
			}
			return null;
		}
	}

	@Override
	protected boolean isInSameTeam(Entity other) {
		LivingEntity owner = getOwner();
		if (other == owner) {
			return true;
		}
		if (owner != null) {
			return owner.isTeamPlayer(other.getScoreboardTeam());
		}
		return super.isInSameTeam(other);
	}

	@Override
	public boolean canBeHitByProjectile() {
		return false;
	}

	@Override
	public boolean canBeLeashed() {
		return false;
	}

	@Override
	public boolean canTarget(LivingEntity target) {
		if (target instanceof DoppelgangerEntity doppelganger && doppelganger.getOwner() == getOwner()) {
			return false;
		}
		return getOwner() != target && super.canTarget(target);
	}

	@Override
	protected boolean shouldDropLoot(ServerWorld world) {
		return false;
	}

	public final boolean cannotFollowOwner() {
		return hasVehicle() || mightBeLeashed() || getOwner() != null && getOwner().isSpectator();
	}

	@Nullable
	public LivingEntity getOwner() {
		return LazyEntityReference.getLivingEntity(owner, getEntityWorld());
	}

	@Nullable
	public LazyEntityReference<LivingEntity> getCopiedEntityReference() {
		return dataTracker.get(COPIED_ENTITY).orElse(null);
	}

	@Nullable
	public LivingEntity getCopiedEntity() {
		return LazyEntityReference.getLivingEntity(getCopiedEntityReference(), getEntityWorld());
	}

	public void setCopiedEntity(@Nullable LivingEntity entity) {
		dataTracker.set(COPIED_ENTITY, Optional.ofNullable(entity).map(LazyEntityReference::of));
	}
}
