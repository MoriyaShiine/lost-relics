/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */

package moriyashiine.lostrelics.common.references;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.key;

public class LostRelicsEntityTypeIds {
	public static final ResourceKey<EntityType<?>> DOPPELGANGER = key(Registries.ENTITY_TYPE, "doppelganger");
	public static final ResourceKey<EntityType<?>> SMOKE_BALL = key(Registries.ENTITY_TYPE, "smoke_ball");

	public static final ResourceKey<EntityType<?>> TAINTED_BLOOD_CRYSTAL = key(Registries.ENTITY_TYPE, "tainted_blood_crystal");
}
