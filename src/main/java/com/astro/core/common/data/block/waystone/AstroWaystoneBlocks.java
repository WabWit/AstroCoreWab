package com.astro.core.common.data.block.waystone;

import net.blay09.mods.waystones.api.WaystoneStyle;
import net.blay09.mods.waystones.api.WaystoneStyles;
import net.blay09.mods.waystones.block.WaystoneBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import com.astro.core.AstroCore;

public class AstroWaystoneBlocks {

    public static final DeferredRegister<net.minecraft.world.level.block.Block> BLOCKS = DeferredRegister
            .create(ForgeRegistries.BLOCKS, AstroCore.MOD_ID);

    public static final DeferredRegister<net.minecraft.world.item.Item> ITEMS = DeferredRegister
            .create(ForgeRegistries.ITEMS, AstroCore.MOD_ID);

    public static final RegistryObject<WaystoneBlock> ASTEROID_WAYSTONE = BLOCKS.register("asteroid_waystone",
            () -> new WaystoneBlock(BlockBehaviour.Properties.of()
                    .sound(SoundType.STONE)
                    .strength(5f, 2000f)
                    .requiresCorrectToolForDrops()) {

                @Override
                public net.minecraft.world.level.block.entity.BlockEntity newBlockEntity(
                                                                                         net.minecraft.core.BlockPos pos,
                                                                                         net.minecraft.world.level.block.state.BlockState state) {
                    return new AstroWaystoneBlockEntity(pos, state);
                }
            });

    public static final RegistryObject<BlockItem> ASTEROID_WAYSTONE_ITEM = ITEMS.register("asteroid_waystone",
            () -> new BlockItem(ASTEROID_WAYSTONE.get(),
                    new net.minecraft.world.item.Item.Properties()));

    public static void registerStyle() {
        WaystoneStyles.register(new WaystoneStyle(AstroCore.id("asteroid_waystone")));
    }
}
