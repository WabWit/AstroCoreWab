package com.astro.core.mixin.gtceu;

import com.gregtechceu.gtceu.api.capability.recipe.EURecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.machine.feature.IRecipeLogicMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMufflerMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.part.TieredPartMachine;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.api.recipe.content.ContentModifier;

import net.minecraftforge.items.IItemHandlerModifiable;

import appeng.blockentity.qnb.QuantumBridgeBlockEntity;
import appeng.core.definitions.AEItems;
import com.astro.core.mixin.appliedenergistics.accessor.QuantumBridgeAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Mixin(value = RecipeLogic.class, remap = false)
public abstract class RecipeLogicMixin {

    @Shadow(remap = false)
    @Final
    public IRecipeLogicMachine machine;

    @Shadow(remap = false)
    protected GTRecipe lastRecipe;

    @ModifyVariable(method = "setupRecipe", at = @At("HEAD"), argsOnly = true, remap = false)
    private GTRecipe astro$applyCompoundingMufflerBonus(GTRecipe recipe) {
        if (!(this.machine instanceof WorkableMultiblockMachine multiMachine)) {
            return recipe;
        }

        int bestMufflerTier = -1;
        for (var part : multiMachine.getParts()) {
            if (part instanceof IMufflerMachine && part instanceof TieredPartMachine tieredPart) {
                bestMufflerTier = Math.max(bestMufflerTier, tieredPart.getTier());
            }
        }

        int bonusTiers = Math.max(0, bestMufflerTier - 1);
        if (bonusTiers <= 0) {
            return recipe;
        }

        double multiplier = Math.max(0.01D, 1.0D / Math.pow(1.02D, bonusTiers));
        GTRecipe adjusted = recipe.copy();
        if (!astro$scaleEnergyContents(adjusted.inputs, multiplier) &&
                !astro$scaleEnergyContents(adjusted.tickInputs, multiplier)) {
            return recipe;
        }
        return adjusted;
    }

    private static boolean astro$scaleEnergyContents(Map<?, List<Content>> contents, double multiplier) {
        List<Content> euContents = (List<Content>) contents.get(EURecipeCapability.CAP);
        if (euContents == null || euContents.isEmpty()) {
            return false;
        }

        List<Content> adjusted = new ArrayList<>(euContents.size());
        ContentModifier modifier = ContentModifier.multiplier(multiplier);
        for (Content content : euContents) {
            adjusted.add(content.copyChanced(EURecipeCapability.CAP, modifier));
        }

        ((Map<Object, List<Content>>) contents).put(EURecipeCapability.CAP, adjusted);
        return true;
    }

    @Inject(method = "onRecipeFinish",
            at = @At(
                     value = "INVOKE",
                     target = "Lcom/gregtechceu/gtceu/api/machine/trait/RecipeLogic;handleRecipeIO(Lcom/gregtechceu/gtceu/api/recipe/GTRecipe;Lcom/gregtechceu/gtceu/api/capability/recipe/IO;)Lcom/gregtechceu/gtceu/api/recipe/ActionResult;",
                     ordinal = 0,
                     shift = At.Shift.AFTER),
            remap = false)
    private void astro$assignSingularityFrequency(CallbackInfo ci) {
        if (lastRecipe == null) return;

        var itemOutputs = lastRecipe.outputs.get(ItemRecipeCapability.CAP);
        if (itemOutputs == null) return;

        boolean hasQES = itemOutputs.stream().anyMatch(content -> {
            var stacks = ItemRecipeCapability.CAP.of(content.getContent()).getItems();
            return stacks.length > 0 && AEItems.QUANTUM_ENTANGLED_SINGULARITY.isSameAs(stacks[0]);
        });
        if (!hasQES) return;

        long frequency = new Date().getTime() * 100 + QuantumBridgeAccessor.getSingularitySeed() % 100;
        QuantumBridgeAccessor.setSingularitySeed(QuantumBridgeAccessor.getSingularitySeed() + 1);

        for (var handlerList : machine.getCapabilitiesFlat(IO.OUT, ItemRecipeCapability.CAP)) {
            if (!(handlerList instanceof IItemHandlerModifiable modifiable)) continue;
            for (int i = 0; i < modifiable.getSlots(); i++) {
                var stack = modifiable.getStackInSlot(i);
                if (!stack.isEmpty() && AEItems.QUANTUM_ENTANGLED_SINGULARITY.isSameAs(stack)) {
                    stack.getOrCreateTag().putLong(
                            QuantumBridgeBlockEntity.TAG_FREQUENCY,
                            frequency);
                }
            }
        }
    }
}
