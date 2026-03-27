/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.common.world.entity.monster;

import moriyashiine.lostrelics.common.init.ModEntityTypes;
import moriyashiine.lostrelics.common.init.ModItems;
import moriyashiine.lostrelics.common.util.LostRelicsUtil;
import moriyashiine.lostrelics.common.world.entity.monster.ai.goal.*;
import moriyashiine.strawberrylib.api.module.SLibClientUtils;
import moriyashiine.strawberrylib.api.module.SLibUtils;
import moriyashiine.strawberrylib.api.objects.enums.ParticleAnchor;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.scores.PlayerTeam;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public class Doppelganger extends PathfinderMob {
	private static final EntityDataAccessor<Optional<EntityReference<LivingEntity>>> COPIED_ENTITY = SynchedEntityData.defineId(Doppelganger.class, EntityDataSerializers.OPTIONAL_LIVING_ENTITY_REFERENCE);

	@Nullable
	private EntityReference<LivingEntity> owner;
	private boolean mirrorDemon = false;
	private int ticksExisted = 0;

	public Doppelganger(EntityType<? extends PathfinderMob> type, Level level) {
		super(type, level);
	}

	public Doppelganger(Level level, LivingEntity owner, boolean mirrorDemon) {
		this(ModEntityTypes.DOPPELGANGER, level);
		this.owner = EntityReference.of(owner);
		this.mirrorDemon = mirrorDemon;
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.ATTACK_DAMAGE, 1).add(Attributes.MOVEMENT_SPEED, 0.35);
	}

	@Override
	protected void readAdditionalSaveData(ValueInput input) {
		owner = EntityReference.read(input, "Owner");
		entityData.set(COPIED_ENTITY, Optional.ofNullable(EntityReference.read(input, "CopiedEntity")));
		mirrorDemon = input.getBooleanOr("MirrorDemon", false);
		ticksExisted = input.getIntOr("TicksExisted", 0);
	}

	@Override
	protected void addAdditionalSaveData(ValueOutput output) {
		EntityReference.store(owner, output, "Owner");
		EntityReference.store(getCopiedEntityReference(), output, "CopiedEntity");
		output.putBoolean("MirrorDemon", mirrorDemon);
		output.putInt("TicksExisted", ticksExisted);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(COPIED_ENTITY, Optional.empty());
	}

	@Override
	protected void registerGoals() {
		goalSelector.addGoal(0, new FloatGoal(this));
		DoppelgangerRangedAttackGoal rangedAttackGoal = new DoppelgangerRangedAttackGoal(this);
		goalSelector.addGoal(1, rangedAttackGoal);
		goalSelector.addGoal(2, new DoppelgangerMeleeAttackGoal(this, 1, false, rangedAttackGoal));
		goalSelector.addGoal(3, new DoppelgangerFollowOwnerGoal(this, 1, 10, 2));
		goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1));
		goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8));
		goalSelector.addGoal(5, new RandomLookAroundGoal(this));
		targetSelector.addGoal(0, new DoppelgangerOwnerHurtByTargetGoal(this));
		targetSelector.addGoal(1, new DoppelgangerOwnerHurtTargetGoal(this));
	}

	@Override
	public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
		if (mirrorDemon) {
			damage /= 2;
		}
		return super.hurtServer(level, source, damage);
	}

	@Override
	public void tick() {
		super.tick();
		ticksExisted++;
		if (level().isClientSide()) {
			SLibClientUtils.addParticles(this, ParticleTypes.SMOKE, 1, ParticleAnchor.BODY);
		} else {
			@Nullable LivingEntity owner = getOwner();
			if (ticksExisted >= 600 || owner == null || owner.isRemoved() || level() != owner.level() || distanceTo(owner) > 32) {
				SLibUtils.addParticles(this, ParticleTypes.SMOKE, 128, ParticleAnchor.BODY);
				discard();
				return;
			} else if (owner instanceof Player player) {
				ItemStack relicStack = LostRelicsUtil.getRelic(player, ModItems.SMOKING_MIRROR);
				if (!relicStack.isEmpty()) {
					LostRelicsUtil.setCooldown(player, relicStack, 600);
				}
			}
			@Nullable LivingEntity targetCopy = owner;
			if (mirrorDemon && getTarget() instanceof Avatar avatar) {
				targetCopy = avatar;
			}
			if (getCopiedEntity() != targetCopy) {
				setCopiedEntity(targetCopy);
			}
			@Nullable LivingEntity copiedEntity = getCopiedEntity();
			if (copiedEntity != null) {
				for (EquipmentSlot slot : EquipmentSlot.values()) {
					setItemSlot(slot, copiedEntity.getItemBySlot(slot).copy());
				}
			}
		}
	}

	@Override
	public void die(DamageSource source) {
		super.die(source);
		if (!level().isClientSide()) {
			SLibUtils.addParticles(this, ParticleTypes.SMOKE, 128, ParticleAnchor.BODY);
			discard();
		}
	}

	@Override
	public @Nullable PlayerTeam getTeam() {
		PlayerTeam team = super.getTeam();
		if (team != null) {
			return team;
		} else {
			LivingEntity owner = getOwner();
			if (owner != null) {
				return owner.getTeam();
			}
			return null;
		}
	}

	@Override
	protected boolean considersEntityAsAlly(Entity other) {
		LivingEntity owner = getOwner();
		if (other == owner) {
			return true;
		}
		if (owner != null) {
			return owner.isAlliedTo(other.getTeam());
		}
		return super.considersEntityAsAlly(other);
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
	public boolean canAttack(LivingEntity target) {
		if (target instanceof Doppelganger doppelganger && doppelganger.getOwner() == getOwner()) {
			return false;
		}
		return getOwner() != target && super.canAttack(target);
	}

	@Override
	protected boolean shouldDropLoot(ServerLevel level) {
		return false;
	}

	public final boolean cannotFollowOwner() {
		return isPassenger() || mayBeLeashed() || getOwner() != null && getOwner().isSpectator();
	}

	@Nullable
	public LivingEntity getOwner() {
		return EntityReference.getLivingEntity(owner, level());
	}

	@Nullable
	public EntityReference<LivingEntity> getCopiedEntityReference() {
		return entityData.get(COPIED_ENTITY).orElse(null);
	}

	@Nullable
	public LivingEntity getCopiedEntity() {
		return EntityReference.getLivingEntity(getCopiedEntityReference(), level());
	}

	public void setCopiedEntity(@Nullable LivingEntity entity) {
		entityData.set(COPIED_ENTITY, Optional.ofNullable(entity).map(EntityReference::of));
	}
}
