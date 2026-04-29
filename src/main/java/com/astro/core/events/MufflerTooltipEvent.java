package com.astro.core.events;

import net.minecraft.network.chat.Component;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import com.astro.core.AstroCore;

@SuppressWarnings("all")
@Mod.EventBusSubscriber(modid = AstroCore.MOD_ID)
public class MufflerTooltipEvent {

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        var stack = event.getItemStack();
        var block = net.minecraft.world.level.block.Block.byItem(stack.getItem());

        if (!(block instanceof com.gregtechceu.gtceu.api.block.MetaMachineBlock machineBlock)) return;
        var definition = machineBlock.getDefinition();
        if (definition == null) return;

        var id = definition.getId();
        if (!id.getPath().contains("muffler")) return;

        int tier = definition.getTier();
        int bonusTiers = Math.max(0, tier - 1);
        if (bonusTiers <= 0) return;

        double multiplier = Math.max(0.01D, 1.0D / Math.pow(1.02D, bonusTiers));
        int savingPercent = (int) Math.round((1.0D - multiplier) * 100);

        event.getToolTip().add(Component.translatable(
                "astrogreg.machine.muffler_hatch.efficiency_bonus",
                savingPercent));
    }
}