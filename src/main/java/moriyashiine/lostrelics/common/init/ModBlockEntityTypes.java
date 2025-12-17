/*
 * Copyright (c) MoriyaShiine. All Rights Reserved.
 */
package moriyashiine.lostrelics.common.init;

import moriyashiine.lostrelics.common.block.entity.AltarBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;

import static moriyashiine.strawberrylib.api.module.SLibRegistries.registerBlockEntityType;

public class ModBlockEntityTypes {
	public static final BlockEntityType<AltarBlockEntity> ALTAR = registerBlockEntityType("altar", FabricBlockEntityTypeBuilder.create(AltarBlockEntity::new, ModBlocks.JUNGLE_ALTAR));

	public static void init() {
	}
}
