package com.astro.core.events;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import com.astro.core.AstroCore;
import earth.terrarium.adastra.common.registry.ModItems;

@Mod.EventBusSubscriber(modid = AstroCore.MOD_ID)
public class SpawnItemsHandler {

    @SubscribeEvent
    public static void onPlayerJoin(EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        if (player.getTags().contains("StartingItems")) return;

        player.getInventory().armor.set(3, new ItemStack(ModItems.SPACE_HELMET.get()));

        ItemStack suit = new ItemStack(ModItems.SPACE_SUIT.get());
        CompoundTag botariumData = new CompoundTag();
        ListTag storedFluids = new ListTag();
        CompoundTag fluid = new CompoundTag();
        fluid.putString("Fluid", "gtceu:oxygen");
        fluid.putLong("Amount", 2500L);
        storedFluids.add(fluid);
        botariumData.put("StoredFluids", storedFluids);
        botariumData.putInt("Damage", 0);
        suit.getOrCreateTag().put("BotariumData", botariumData);
        player.getInventory().armor.set(2, suit);

        player.getInventory().armor.set(1, new ItemStack(ModItems.SPACE_PANTS.get()));
        player.getInventory().armor.set(0, new ItemStack(ModItems.SPACE_BOOTS.get()));

        player.addTag("StartingItems");
    }
}
