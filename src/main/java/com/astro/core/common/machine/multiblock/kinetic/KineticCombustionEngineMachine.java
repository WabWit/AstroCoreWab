package com.astro.core.common.machine.multiblock.kinetic;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;

import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;

import net.minecraft.MethodsReturnNonnullByDefault;

import com.astro.core.common.machine.hatches.KineticOutputHatch;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

@SuppressWarnings("all")
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class KineticCombustionEngineMachine extends WorkableMultiblockMachine {

    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
            KineticCombustionEngineMachine.class, WorkableMultiblockMachine.MANAGED_FIELD_HOLDER);

    public static final float OUTPUT_SU = 600_000f;
    public static final float CAPACITY_PER_RPM = OUTPUT_SU / 256f;

    public KineticCombustionEngineMachine(IMachineBlockEntity holder, Object... args) {
        super(holder);
        subscribeServerTick(this::kineticTick);
    }

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    // ======== Structure ========

    @Override
    public void onStructureInvalid() {
        super.onStructureInvalid();
        stopKineticOutput();
    }

    // ======== Tick ========

    private void kineticTick() {
        if (!isFormed() || !isWorkingEnabled()) {
            stopKineticOutput();
            return;
        }

        if (recipeLogic.isActive() && recipeLogic.getStatus() == RecipeLogic.Status.WORKING) {
            updateKineticOutput();
        } else {
            stopKineticOutput();
        }
    }

    // ======== Kinetic Output ========

    private void updateKineticOutput() {
        getKineticOutputHatches().forEach(hatch -> hatch.setOutputSU(OUTPUT_SU, CAPACITY_PER_RPM));
    }

    private void stopKineticOutput() {
        getKineticOutputHatches().forEach(hatch -> hatch.getKineticHolder().stopWorking());
    }

    private List<KineticOutputHatch> getKineticOutputHatches() {
        return getParts().stream()
                .filter(KineticOutputHatch.class::isInstance)
                .map(KineticOutputHatch.class::cast)
                .toList();
    }
}