package com.astro.core.common.data.materials;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlag;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconSet;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.*;
import com.gregtechceu.gtceu.common.data.GTMaterials;

import com.astro.core.AstroCore;

import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.*;

// Material modifications and creation
public class AstroMaterialModification {

    // Voltage helpers
    private static final long[] V = GTValues.V;
    private static final int[] VA = GTValues.VA;

    // Material modification: Name, Flags
    private static final Object[][] MATERIAL_MODIFIERS = {
            { "titanium", new MaterialFlag[] { GENERATE_DENSE } },
            { "neutronium", new MaterialFlag[] { GENERATE_DENSE } },
            { "iron", new MaterialFlag[] { GENERATE_FOIL } },
            { "potin", new MaterialFlag[] { GENERATE_FOIL, GENERATE_RING } },
            { "brass", new MaterialFlag[] { GENERATE_FOIL, GENERATE_RING } },
            { "invar", new MaterialFlag[] { GENERATE_RING, GENERATE_FOIL } },
            { "red_alloy", new MaterialFlag[] { GENERATE_RING } },
            { "zinc", new MaterialFlag[] { GENERATE_BOLT_SCREW } },
            { "nickel", new MaterialFlag[] { GENERATE_FOIL, GENERATE_RING, GENERATE_ROD, GENERATE_BOLT_SCREW } }
    };

    // Component dusts: Name, Color, Flags
    private static final Object[][] COMPONENT_DUSTS = {
            { "asteroid_stone", 0x964491, 0x70276b, MaterialIconSet.ROUGH,
                    new MaterialFlag[] { DISABLE_DECOMPOSITION } },
            { "livingrock", 0xc9c2b1, 0x948e7f, MaterialIconSet.ROUGH,
                    new MaterialFlag[] { DISABLE_DECOMPOSITION } },
            { "livingclay", 0xc9c2e7, 0x4eaeb5, MaterialIconSet.FINE,
                    new MaterialFlag[] { DISABLE_DECOMPOSITION } },
            { "acorn", 0xe0b677, 0x734d15, MaterialIconSet.FINE,
                    new MaterialFlag[] { DISABLE_DECOMPOSITION } }
    };

    // SuperConductors: Name, Color, BlastTemp/GasTier, Cable, Rotor
    private static final Object[][] SUPERCONDUCTORS = {
            { "spirited_uranium", "Spirited Uranium", 0xcb74cc, MaterialIconSet.RADIOACTIVE,
                    new Object[] { 3500, BlastProperty.GasTier.LOW, 7680, 1200 },
                    new Object[] { V[GTValues.EV], 24, 0, true },
                    new Object[] { 300, 190, 3, 18000 } },
            { "nitro_flux", "Nitro-Flux", 0x110c9c, MaterialIconSet.SHINY,
                    new Object[] { 4400, BlastProperty.GasTier.MID, 30720, 1400 },
                    new Object[] { V[GTValues.IV], 32, 0, true },
                    new Object[] { 450, 220, 3, 20000 } },
            { "juperiosaturlytide", "Juperio-Saturlytide", 0xf66999, MaterialIconSet.BRIGHT,
                    new Object[] { 5300, BlastProperty.GasTier.MID, 122880, 1600 },
                    new Object[] { V[GTValues.LuV], 48, 0, true },
                    new Object[] { 700, 260, 3, 24000 } },
            { "gaiaforged_naquadah", "Gaia-Forged Naquadah", 0x421218, MaterialIconSet.SHINY,
                    new Object[] { 7100, BlastProperty.GasTier.HIGH, 491520, 1800 },
                    new Object[] { V[GTValues.ZPM], 64, 0, true },
                    new Object[] { 1100, 380, 3, 32000 } },
            { "neptunium_molybdenum_selenide", "Neptunium Molybdenum Selenide", 0x088a5c, MaterialIconSet.RADIOACTIVE,
                    new Object[] { 10000, BlastProperty.GasTier.HIGHER, 1966080, 2000 },
                    new Object[] { V[GTValues.UV], 96, 0, true },
                    new Object[] { 2000, 550, 3, 48000 } }
    };

    // Periodic table elements to modify: Name, Properties to add
    private static final Object[][] PERIODIC_ELEMENTS = {
            { "neptunium", new String[] { "ingot", "fluid" } }
    };
    // Called during MaterialEvent to CREATE new materials

    public static void register() {
        createComponentDusts();
        createSuperConductors();
    }

    // Called during PostMaterialEvent to MODIFY existing materials

    public static void modify() {
        modifyPeriodicElements();
        addMaterialFlags();
    }

    private static void modifyPeriodicElements() {
        for (Object[] element : PERIODIC_ELEMENTS) {
            String name = (String) element[0];
            String[] types = (String[]) element[1];

            Material material = getMaterialByName(name);

            if (material == null) {
                continue;
            }

            // Add properties
            for (String type : types) {
                switch (type) {
                    case "ingot":
                        if (!material.hasProperty(PropertyKey.INGOT)) {
                            material.setProperty(PropertyKey.INGOT, new IngotProperty());
                        }
                        break;
                    case "dust":
                        if (!material.hasProperty(PropertyKey.DUST)) {
                            material.setProperty(PropertyKey.DUST, new DustProperty());
                        }
                        break;
                    case "fluid":
                        if (!material.hasProperty(PropertyKey.FLUID)) {
                            FluidProperty fluidProperty = new FluidProperty();
                            // FluidProperty requires at least one fluid type to be registered
                            fluidProperty.getStorage().enqueueRegistration(
                                    com.gregtechceu.gtceu.api.fluids.store.FluidStorageKeys.LIQUID,
                                    new com.gregtechceu.gtceu.api.fluids.FluidBuilder());
                            material.setProperty(PropertyKey.FLUID, fluidProperty);
                        }
                        break;
                }
            }
        }
    }

    private static void addMaterialFlags() {
        for (Object[] modifier : MATERIAL_MODIFIERS) {
            String name = (String) modifier[0];
            MaterialFlag[] flags = (MaterialFlag[]) modifier[1];

            Material material = getMaterialByName(name);

            if (material != null) {
                material.addFlags(flags);
            }
        }
    }

    private static void createComponentDusts() {
        for (Object[] dust : COMPONENT_DUSTS) {
            String name = (String) dust[0];
            int color = (int) dust[1];
            int color2 = (int) dust[2];
            MaterialIconSet icon = (MaterialIconSet) dust[3];
            MaterialFlag[] flags = (MaterialFlag[]) dust[4];

            // Create materials without components for now
            new Material.Builder(AstroCore.id(name))
                    .dust()
                    .color(color)
                    .secondaryColor(color2)
                    .iconSet(icon)
                    .flags(flags)
                    .buildAndRegister();
        }
    }

    private static void createSuperConductors() {
        // Standard flags (with DISABLE_DECOMPOSITION)
        MaterialFlag[] superConductorFlags = new MaterialFlag[] {
                GENERATE_PLATE, GENERATE_ROD, GENERATE_BOLT_SCREW, GENERATE_FRAME,
                GENERATE_GEAR, GENERATE_LONG_ROD, GENERATE_FOIL, GENERATE_RING,
                GENERATE_SPRING, GENERATE_SPRING_SMALL, GENERATE_SMALL_GEAR,
                GENERATE_FINE_WIRE, GENERATE_ROTOR, DISABLE_DECOMPOSITION
        };

        for (Object[] sc : SUPERCONDUCTORS) {
            String id = (String) sc[0];
            String name = (String) sc[1];
            int color = (int) sc[2];
            MaterialIconSet icon = (MaterialIconSet) sc[3];
            Object[] blast = (Object[]) sc[4];
            Object[] cable = (Object[]) sc[5];
            Object[] rotor = (Object[]) sc[6];

            // Create materials without components
            Material material = new Material.Builder(AstroCore.id(id))
                    .langValue(name)
                    .ingot()
                    .fluid()
                    .color(color)
                    .iconSet(icon)
                    .blastTemp((int) blast[0], (BlastProperty.GasTier) blast[1], (int) blast[2], (int) blast[3])
                    .cableProperties((long) cable[0], (int) cable[1], (int) cable[2], (boolean) cable[3])
                    .rotorStats(
                            ((Number) rotor[0]).intValue(),
                            ((Number) rotor[1]).intValue(),
                            ((Number) rotor[2]).intValue(),
                            ((Number) rotor[3]).intValue())
                    .flags(superConductorFlags)
                    .buildAndRegister();
        }
    }

    // Helper method to get material by name

    private static Material getMaterialByName(String name) {
        return switch (name.toLowerCase()) {
            case "titanium" -> GTMaterials.Titanium;
            case "neutronium" -> GTMaterials.Neutronium;
            case "iron" -> GTMaterials.Iron;
            case "potin" -> GTMaterials.Potin;
            case "brass" -> GTMaterials.Brass;
            case "invar" -> GTMaterials.Invar;
            case "red_alloy" -> GTMaterials.RedAlloy;
            case "zinc" -> GTMaterials.Zinc;
            case "nickel" -> GTMaterials.Nickel;
            case "neptunium" -> GTMaterials.Neptunium;
            default -> null;
        };
    }
}
