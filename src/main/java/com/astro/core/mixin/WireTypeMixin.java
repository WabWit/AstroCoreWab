package com.astro.core.mixin;

import com.mrh0.createaddition.energy.WireType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("all")
@Mixin(value = WireType.class, remap = false)
public class WireTypeMixin {
    @Inject(method = "getDrop", at = @At("HEAD"), cancellable = true)
    public void getDrop(CallbackInfoReturnable<ItemStack> cir) {
        WireType self = (WireType) (Object) this;
        switch (self) {
            case COPPER -> cir.setReturnValue(new ItemStack(
                    ForgeRegistries.ITEMS.getValue(new ResourceLocation("gtceu", "fine_copper_wire")), 4));
            case GOLD -> cir.setReturnValue(new ItemStack(
                    ForgeRegistries.ITEMS.getValue(new ResourceLocation("gtceu", "fine_gold_wire")), 4));
            case ELECTRUM -> cir.setReturnValue(new ItemStack(
                    ForgeRegistries.ITEMS.getValue(new ResourceLocation("gtceu", "fine_electrum_wire")), 4));
        }
    }
}
