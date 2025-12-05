package com.astro.core.common;

import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.data.chemical.material.event.MaterialRegistryEvent;
import com.gregtechceu.gtceu.api.data.chemical.material.event.PostMaterialEvent;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import com.astro.core.AstroCore;
import com.astro.core.compat.gtceu.materials.AstroMaterialHandler;

public class AstroCommonEventHandler {

    @SuppressWarnings("removal")
    public static void init() {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        final IEventBus otherBus = MinecraftForge.EVENT_BUS;

        bus.addListener(AstroCommonEventHandler::onRegisterMaterialRegistry);
        bus.addListener(AstroCommonEventHandler::onPostRegisterMaterials);
    }

    private static void onRegisterMaterialRegistry(final MaterialRegistryEvent event) {
        AstroCore.MATERIAL_REGISTRY = GTCEuAPI.materialManager.createRegistry(AstroCore.MOD_ID);
    }

    private static void onPostRegisterMaterials(final PostMaterialEvent event) {
        AstroHelpers.isMaterialRegistrationFinished = true;
        AstroMaterialHandler.postInit();
    }
}
