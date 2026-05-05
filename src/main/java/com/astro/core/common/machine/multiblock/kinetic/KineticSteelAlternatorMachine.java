package com.astro.core.common.machine.multiblock.kinetic;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;
import com.gregtechceu.gtceu.utils.FormattingUtil;

import com.lowdragmc.lowdraglib.gui.modular.ModularUI;
import com.lowdragmc.lowdraglib.gui.texture.IGuiTexture;
import com.lowdragmc.lowdraglib.gui.widget.ComponentPanelWidget;
import com.lowdragmc.lowdraglib.gui.widget.DraggableScrollableWidgetGroup;
import com.lowdragmc.lowdraglib.gui.widget.LabelWidget;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;

import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import com.astro.core.common.machine.part.KineticInputHatch;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

@SuppressWarnings("all")
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class KineticSteelAlternatorMachine extends KineticAlternatorMachine {

    protected static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
            KineticSteelAlternatorMachine.class, KineticAlternatorMachine.MANAGED_FIELD_HOLDER);

    public static final int EU_PER_PARALLEL = 40;
    public static final int MAX_PARALLELS = 8;
    public static final float SU_PER_PARALLEL = KineticInputHatch.SU_PER_PARALLEL * 4;
    public static final int LUBRICANT_MB_PER_HOUR = 1_000;

    private int runningTimer = 0;
    private boolean noLubricant = false;
    private boolean hadLubricantLastCheck = true;

    public KineticSteelAlternatorMachine(IMachineBlockEntity holder, Object... args) {
        super(holder, args);
    }

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    @Override
    public int getAvailableParallels() {
        float su = getAvailableSU();
        int parallels = (int) (su / SU_PER_PARALLEL);
        return Math.max(0, Math.min(targetParallel, parallels));
    }

    @Override
    protected void alternatorTick() {
        if (!isFormed() || !isWorkingEnabled()) {
            setIdleStatus();
            return;
        }

        int parallels = getAvailableParallels();

        if (parallels <= 0) {
            setIdleStatus();
            return;
        }

        runningTimer++;
        if (runningTimer >= 72) {
            runningTimer = 0;
            var lubRecipe = GTRecipeBuilder.ofRaw()
                    .inputFluids(GTMaterials.Lubricant.getFluid(1))
                    .buildRawRecipe();
            hadLubricantLastCheck = RecipeHelper.handleRecipeIO(this, lubRecipe, IO.IN,
                    recipeLogic.getChanceCaches()).isSuccess();
        }

        if (!hadLubricantLastCheck) {
            noLubricant = true;
            setIdleStatus();
            return;
        }
        noLubricant = false;

        pushEnergyToHatches(parallels * EU_PER_PARALLEL);
        recipeLogic.setStatus(RecipeLogic.Status.WORKING);
    }

    @Override
    protected float getAvailableSU() {
        for (var part : getParts()) {
            if (part instanceof KineticInputHatch hatch) {
                return Math.abs(hatch.getKineticHolder().getSpeed()) * SU_PER_PARALLEL / REQUIRED_RPM;
            }
        }
        return 0f;
    }

    @Override
    public void addDisplayText(List<Component> textList) {
        if (!isFormed()) {
            textList.add(Component.translatable("gtceu.multiblock.invalid_structure")
                    .withStyle(ChatFormatting.RED));
            return;
        }

        int parallels = getAvailableParallels();
        int currentEUt = recipeLogic.isWorking() ? parallels * EU_PER_PARALLEL : 0;
        int availableSU = (int) getAvailableSU();
        int maxSU = (int) (clampParallel(targetParallel) * SU_PER_PARALLEL);
        int maxEUt = MAX_PARALLELS * EU_PER_PARALLEL;
        int tier = getOutputTier();

        textList.add(Component.translatable("gtceu.multiblock.max_energy_per_tick",
                FormattingUtil.formatNumbers(maxEUt), GTValues.VNF[tier])
                .withStyle(ChatFormatting.GRAY));

        textList.add(Component.translatable("gtceu.multiblock.max_recipe_tier",
                GTValues.VNF[GTValues.ULV])
                .withStyle(ChatFormatting.GRAY));

        textList.add(Component.translatable("astrogreg.machine.kinetic_machine.su_input",
                availableSU, maxSU)
                .withStyle(ChatFormatting.AQUA));

        textList.add(Component.translatable("gtceu.multiblock.turbine.energy_per_tick",
                FormattingUtil.formatNumbers(currentEUt), FormattingUtil.formatNumbers(maxEUt)));

        textList.add(Component.translatable("astrogreg.machine.steam_blast_furnace.parallels",
                clampParallel(targetParallel), parallels)
                .append(ComponentPanelWidget.withButton(Component.literal(" [-] "), "parallelSub"))
                .append(ComponentPanelWidget.withButton(Component.literal("[+]"), "parallelAdd")));

        if (!isWorkingEnabled()) {
            textList.add(Component.translatable("gtceu.multiblock.work_paused")
                    .withStyle(ChatFormatting.YELLOW));
        } else if (noLubricant) {
            textList.add(Component.translatable("astrogreg.machine.kinetic_steel_alternator.no_lubricant")
                    .withStyle(ChatFormatting.RED));
        } else if (parallels > 0) {
            textList.add(Component.translatable("gtceu.multiblock.running")
                    .withStyle(ChatFormatting.GREEN));
        } else {
            textList.add(Component.translatable("astrogreg.machine.kinetic_machine.no_su")
                    .withStyle(ChatFormatting.RED));
        }
    }

    @Override
    public IGuiTexture getScreenTexture() {
        return com.gregtechceu.gtceu.api.gui.GuiTextures.DISPLAY;
    }

    @Override
    public ModularUI createUI(Player player) {
        var screen = new DraggableScrollableWidgetGroup(7, 4, 188, 121)
                .setBackground(getScreenTexture());
        screen.addWidget(new LabelWidget(4, 5,
                self().getBlockState().getBlock().getDescriptionId()));
        screen.addWidget(new ComponentPanelWidget(4, 17, this::addDisplayText)
                .setMaxWidthLimit(180)
                .clickHandler(this::handleDisplayClick));
        return new ModularUI(202, 216, this, player)
                .background(com.gregtechceu.gtceu.api.gui.GuiTextures.BACKGROUND)
                .widget(screen)
                .widget(com.gregtechceu.gtceu.api.gui.UITemplate.bindPlayerInventory(
                        player.getInventory(),
                        com.gregtechceu.gtceu.api.gui.GuiTextures.SLOT,
                        7, 134, true));
    }
}
