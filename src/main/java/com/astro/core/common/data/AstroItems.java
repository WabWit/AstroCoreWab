package com.astro.core.common.data;

import com.google.common.base.Preconditions;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.ItemMaterialInfo;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.MaterialStack;
import com.gregtechceu.gtceu.api.item.ComponentItem;
import com.gregtechceu.gtceu.api.item.component.ThermalFluidStats;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.item.ItemFluidContainer;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import net.minecraft.world.item.Item;

import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraftforge.fluids.FluidType;

import static com.astro.core.AstroCore.ASTRO_CREATIVE_TAB;
import static com.gregtechceu.gtceu.common.data.GTItems.*;
import static com.gregtechceu.gtceu.common.registry.GTRegistration.REGISTRATE;
import static com.gregtechceu.gtceu.utils.FormattingUtil.toEnglishName;

public class AstroItems {

    static {
        REGISTRATE.creativeModeTab(() -> ASTRO_CREATIVE_TAB);
    }

    public static ItemEntry<Item> createBasicItem = REGISTRATE
            .item("manasteel_firebox_casing", Item::new)
            .model((ctx, prov) -> {
                prov.withExistingParent(ctx.getName(), prov.mcLoc("item/generated"))
                        .texture("layer0", prov.modLoc("item/" + ctx.getName()));
            })
            .register();

//    public static ItemEntry<ComponentItem> FLUID_CELL_LARGE_RHODIUM_PLATED_PALLADIUM = createFluidCell(GTMaterials.RhodiumPlatedPalladium, 2048, 8, 32);
//    public static ItemEntry<ComponentItem> FLUID_CELL_LARGE_NAQUADAH_ALLOY = createFluidCell(GTMaterials.NaquadahAlloy, 4096, 10, 32);
//    public static ItemEntry<ComponentItem> FLUID_CELL_LARGE_DARMSTADTIUM = createFluidCell(GTMaterials.Darmstadtium, 8192, 10, 32);
//    public static ItemEntry<ComponentItem> FLUID_CELL_LARGE_NEUTRONIUM = createFluidCell(GTMaterials.Neutronium, 16384, 10, 16);
//
//    public static ItemEntry<ComponentItem> createFluidCell(Material mat, int capacity, int matSize, int stackSize) {
//        var prop = mat.getProperty(PropertyKey.FLUID_PIPE);
//        Preconditions.checkArgument(prop != null,
//                "Material { %s } does not have Fluid Pipe properties, but is being used to create a Fluid Cell",
//                mat.getName());
//        return REGISTRATE
//                .item("%s_fluid_cell".formatted(mat.getName()), ComponentItem::create)
//                .lang("%s " + toEnglishName(mat.getName()) + " Cell")
//                .color(() -> GTItems::cellColor)
//                .setData(ProviderType.ITEM_MODEL, NonNullBiConsumer.noop())
//                .properties(p -> p.stacksTo(stackSize))
//                .onRegister(attach(cellName(),
//                        ThermalFluidStats.create(FluidType.BUCKET_VOLUME * capacity, prop, true),
//                        new ItemFluidContainer()))
//                .onRegister(materialInfo(new ItemMaterialInfo(new MaterialStack(mat, GTValues.M * matSize))))
//                .register();
//    }

    public static void init() {}
}
