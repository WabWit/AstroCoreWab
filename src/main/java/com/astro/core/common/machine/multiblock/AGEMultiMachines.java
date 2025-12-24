package com.astro.core.common.machine.multiblock;

import com.astro.core.common.data.AstroRecipeTypes;
import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.common.data.models.GTMachineModels;
import com.gregtechceu.gtceu.common.machine.multiblock.part.FluidHatchPartMachine;
import com.gregtechceu.gtceu.common.machine.multiblock.part.ItemBusPartMachine;

import static com.astro.core.common.registry.AstroRegistry.REGISTRATE;
import static com.gregtechceu.gtceu.api.pattern.Predicates.blocks;
import static com.gregtechceu.gtceu.common.data.GTBlocks.CASING_PTFE_INERT;
import static com.gregtechceu.gtceu.common.data.GTBlocks.CASING_STAINLESS_CLEAN;

@SuppressWarnings("all")
public class AGEMultiMachines {

//    public static final MultiblockMachineDefinition FILTRATION_PLANT = REGISTRATE
//            .multiblock("filtration_plant", WorkableElectricMultiblockMachine::new)
//            .langValue("Filtration Plant")
//            .rotationState(RotationState.NON_Y_AXIS)
//            .recipeTypes(AstroRecipeTypes.DEIONIZATION_RECIPES, GTRecipeTypes.DISTILLERY_RECIPES)
//            .recipeModifiers(GTRecipeModifiers.OC_NON_PERFECT_SUBTICK, GTRecipeModifiers.BATCH_MODE, GTRecipeModifiers.PARALLEL_HATCH)
//            .appearanceBlock(GTBlocks.CASING_PTFE_INERT)
//            .partAppearance((controller, part, side) -> {
//                if(part instanceof ItemBusPartMachine itemBus && itemBus.getInventory().canCapOutput()) {
//                    return CASING_STAINLESS_CLEAN.getDefaultState();
//                }
//                if(part instanceof FluidHatchPartMachine check && cast.getIO.supports(IO.IN)) {
//                    return CASING_STAINLESS_CLEAN.getDefaultState();
//                }
//                return CASING_PTFE_INERT.getDefaultState();
//            })
//            .pattern(definition -> {
//                var pcasing = blocks(GTBlocks.CASING_PTFE_INERT.get());
//                var scasing = blocks(CASING_STAINLESS_CLEAN.get());
//                return FactoryBlockPattern.start()
//                        .aisle("SSSFFF", "SGSCCC", "SGSCCC", "SSSCCC", "###CCC")
//                        .aisle("SSSFFF", "GHGPPC", "GHGCPC", "SSSCPC", "###CCC")
//                        .aisle("SSSFFF", "SGSC@C", "SGSCCC", "SSSCCC", "###CCC")
//                        .where('@', Predicates.controller(blocks(definition.getBlock())))
//                        .where('#', Predicates.any())
//                        .where('S', scasing
//                                .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS).setExactLimit(1))
//                                .or(Predicates.abilities(PartAbility.IMPORT_ITEMS).setMinGlobalLimited(1).setMaxGlobalLimited(2)))
//                        .where('C', pcasing
//                                .or(Predicates.abilities(PartAbility.EXPORT_FLUIDS)).setMinGlobalLimited(1).setMaxGlobalLimited(2)
//                                .or(Predicates.abilities(PartAbility.EXPORT_ITEMS)).setMaxGlobalLimited(1)
//                                .or(Predicates.abilities(PartAbility.MAINTENANCE)).setExactLimit(1)
//                                .or(Predicates.abilities(PartAbility.INPUT_ENERGY)).setExactLimit(1))
//                        .where('P', Predicates.blocks(GTBlocks.CASING_TUNGSTENSTEEL_PIPE.get()))
//                        .where('F', Predicates.blocks(GTBlocks.FIREBOX_TUNGSTENSTEEL.get()))
//                        .where('G', Predicates.blocks(GTBlocks.CASING_LAMINATED_GLASS.get()))
//                        .where('H', Predicates.blocks(GTBlocks.HERMETIC_CASING_IV.get()))
//                .build();
//            })
//            .model(GTMachineModels.createWorkableCasingMachineModel(
//                    GTCEu.id("block/casings/solid/machine_casing_inert_ptfe"),
//                    GTCEu.id("block/multiblock/central_monitor")))
//            .register();


    public static void init() {}
}
