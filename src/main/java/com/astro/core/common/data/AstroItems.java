package com.astro.core.common.data;

import static com.astro.core.AstroCore.ASTRO_CREATIVE_TAB;
import static com.astro.core.common.registry.AstroRegistry.REGISTRATE;

@SuppressWarnings("all")
public class AstroItems {

    static {
        REGISTRATE.creativeModeTab(() -> ASTRO_CREATIVE_TAB);
    }

    public static void init() {}

    // public static ItemEntry<Item> FIREBOX_CASING = REGISTRATE
    // .item("manasteel_firebox_casing", Item::new)
    // .model((ctx, prov) -> {
    // prov.withExistingParent(ctx.getName(), prov.mcLoc("item/generated"))
    // .texture("layer0", prov.modLoc("item/" + ctx.getName()));
    // })
    // .register();

    // fuckass item class isn't working
    // public static ItemEntry<Item> SHAPE_EXTRUDER_SLEEVE = REGISTRATE
    // .item(AstroCore.id("sleeve_extruder_mold"), Item::new)
    // .lang("Extruder Mold (Sleeve)")
    // .onRegister(materialInfo(new ItemMaterialInfo(new MaterialStack(GTMaterials.Steel, GTValues.M * 4))))
    // .register();
    // public static ItemEntry<Item> SHAPE_MOLD_SLEEVE = REGISTRATE
    // .item(AstroCore.id("sleeve_casting_mold"), Item::new)
    // .lang("Casting Mold (Sleeve)")
    // .onRegister(materialInfo(new ItemMaterialInfo(new MaterialStack(GTMaterials.Steel, GTValues.M * 4))))
    // .register();
}
