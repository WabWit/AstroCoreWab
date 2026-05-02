package com.astro.core.common.data.block.waystone;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import com.astro.core.AstroCore;

public class AstroWaystoneBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister
            .create(ForgeRegistries.BLOCK_ENTITY_TYPES, AstroCore.MOD_ID);

    public static final RegistryObject<BlockEntityType<AstroWaystoneBlockEntity>> ASTEROID_WAYSTONE = BLOCK_ENTITY_TYPES
            .register("asteroid_waystone", () -> BlockEntityType.Builder
                    .of(AstroWaystoneBlockEntity::new,
                            AstroWaystoneBlocks.ASTEROID_WAYSTONE.get())
                    .build(null));
}
