package com.astro.core.common.data.tag;

import com.astro.core.AstroCore;
import com.astro.core.common.data.materials.AstroMaterials;
import com.drd.ad_extendra.common.registry.ModBlocks;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.oreTagPrefix;

@SuppressWarnings("unused")
public class AstroTagPrefix {

    public static final TagPrefix orePlutoStone = oreTagPrefix("pluto_stone", BlockTags.MINEABLE_WITH_PICKAXE)
            .langValue("Pluto Stone %s Ore")
            .registerOre(
                    () -> ModBlocks.PLUTO_STONE.get().defaultBlockState(),
                    () -> AstroMaterials.PLUTO_STONE,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_ORANGE)
                            .requiresCorrectToolForDrops()
                            .strength(3.0F, 3.0F),
                    AstroCore.id("block/stones/pluto_stone"),
                    true, false, true);

    public static void init() {}

}
