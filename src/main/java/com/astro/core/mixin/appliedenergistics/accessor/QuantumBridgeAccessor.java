package com.astro.core.mixin.appliedenergistics.accessor;

import appeng.blockentity.qnb.QuantumBridgeBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = QuantumBridgeBlockEntity.class, remap = false)
public interface QuantumBridgeAccessor {

    @Accessor("singularitySeed")
    static int getSingularitySeed() {
        throw new AssertionError();
    }

    @Accessor("singularitySeed")
    static void setSingularitySeed(int seed) {
        throw new AssertionError();
    }
}
