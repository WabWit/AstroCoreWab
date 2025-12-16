package com.astro.core.common.data.materials;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlag;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.*;
import com.gregtechceu.gtceu.common.data.GTMaterials;

import com.astro.core.AstroCore;

import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.*;

// Material modifications and creation
public class MaterialModification {

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
            { "andesite_alloy", 0xa6a08f,
                    new MaterialFlag[] { DISABLE_DECOMPOSITION } },
            { "asteroid_stone", 0x70276b,
                    new MaterialFlag[] { DISABLE_DECOMPOSITION } },
            { "livingrock", 0xc9c2b1,
                    new MaterialFlag[] { DISABLE_DECOMPOSITION } },
            { "livingclay", 0xc9c2e7,
                    new MaterialFlag[] { DISABLE_DECOMPOSITION } },
            { "acorn", 0x734d15,
                    new MaterialFlag[] { DISABLE_DECOMPOSITION } }
    };

    // SuperConductors: Name, Color, BlastTemp/GasTier, Cable, Rotor
    private static final Object[][] SUPERCONDUCTORS = {
            { "blazing_etrium", 0x8ee8ed,
                    new Object[] { 1700, BlastProperty.GasTier.LOW },
                    new Object[] { V[GTValues.MV], 8, 0, true },
                    new Object[] { 190, 150, 3, 14000 } },
            { "niotic_calorite", 0xe4eb60,
                    new Object[] { 1700, BlastProperty.GasTier.LOW },
                    new Object[] { V[GTValues.HV], 16, 0, true },
                    new Object[] { 220, 170, 3, 16000 } },
            { "spirited_uranium", 0xcb74cc,
                    new Object[] { 3500, BlastProperty.GasTier.LOW },
                    new Object[] { V[GTValues.EV], 24, 0, true },
                    new Object[] { 300, 190, 3, 18000 } },
            { "nitro_flux", 0x110c9c,
                    new Object[] { 4400, BlastProperty.GasTier.MID },
                    new Object[] { V[GTValues.IV], 32, 0, true },
                    new Object[] { 450, 220, 3, 20000 } },
            { "juperiosaturlytide", 0xf66999,
                    new Object[] { 5300, BlastProperty.GasTier.MID },
                    new Object[] { V[GTValues.LuV], 48, 0, true },
                    new Object[] { 700, 260, 3, 24000 } },
            { "gaiaforged_naquadah", 0x421218,
                    new Object[] { 7100, BlastProperty.GasTier.HIGH },
                    new Object[] { V[GTValues.ZPM], 64, 0, true },
                    new Object[] { 1100, 380, 3, 32000 } },
            { "neptunium_molybdenum_selenide", 0x088a5c,
                    new Object[] { 10000, BlastProperty.GasTier.HIGHER },
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
            MaterialFlag[] flags = (MaterialFlag[]) dust[2];

            // Create materials without components for now
            new Material.Builder(AstroCore.id(name))
                    .dust()
                    .color(color)
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
                GENERATE_FINE_WIRE, GENERATE_ROTOR, GENERATE_ROUND, DISABLE_DECOMPOSITION
        };

        for (Object[] sc : SUPERCONDUCTORS) {
            String name = (String) sc[0];
            int color = (int) sc[1];
            Object[] blast = (Object[]) sc[2];
            Object[] cable = (Object[]) sc[3];
            Object[] rotor = (Object[]) sc[4];

            // Create materials without components
            Material material = new Material.Builder(AstroCore.id(name))
                    .ingot()
                    .fluid()
                    .color(color)
                    .blastTemp((int) blast[0], (BlastProperty.GasTier) blast[1])
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
