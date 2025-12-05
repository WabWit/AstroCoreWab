package com.astro.core.compat.gtceu.materials;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;

import com.astro.core.common.AstroHelpers;
import earth.terrarium.adastra.common.registry.ModItems;

import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;

public class AstroMaterialHandler {

    public static void postInit() {
        Material desh = AstroHelpers.getMaterial("desh");
        if (desh != null) {
            rawOre.setIgnored(desh, ModItems.RAW_DESH);
            rawOreBlock.setIgnored(desh, ModItems.RAW_DESH_BLOCK);
            block.setIgnored(desh, ModItems.DESH_BLOCK);
            ingot.setIgnored(desh, ModItems.DESH_INGOT);
            nugget.setIgnored(desh, ModItems.DESH_NUGGET);
        }

        Material ostrum = AstroHelpers.getMaterial("ostrum");
        if (ostrum != null) {
            rawOre.setIgnored(ostrum, ModItems.RAW_OSTRUM);
            rawOreBlock.setIgnored(ostrum, ModItems.RAW_OSTRUM_BLOCK);
            block.setIgnored(ostrum, ModItems.OSTRUM_BLOCK);
            ingot.setIgnored(ostrum, ModItems.OSTRUM_INGOT);
            nugget.setIgnored(ostrum, ModItems.OSTRUM_NUGGET);
        }

        Material calorite = AstroHelpers.getMaterial("calorite");
        if (calorite != null) {
            rawOre.setIgnored(calorite, ModItems.RAW_CALORITE);
            rawOreBlock.setIgnored(calorite, ModItems.RAW_CALORITE_BLOCK);
            block.setIgnored(calorite, ModItems.CALORITE_BLOCK);
            ingot.setIgnored(calorite, ModItems.CALORITE_INGOT);
            nugget.setIgnored(calorite, ModItems.CALORITE_NUGGET);
        }
    }
}
