package com.astro.core.client.renderer.machine;

import com.gregtechceu.gtceu.api.block.property.GTBlockStateProperties;
import com.gregtechceu.gtceu.api.machine.feature.IRecipeLogicMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiController;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart;
import com.gregtechceu.gtceu.api.machine.multiblock.MultiblockControllerMachine;
import com.gregtechceu.gtceu.client.model.machine.IControllerModelRenderer;
import com.gregtechceu.gtceu.client.renderer.machine.DynamicRender;
import com.gregtechceu.gtceu.client.renderer.machine.DynamicRenderType;
import com.gregtechceu.gtceu.client.util.ModelUtils;

import com.astro.core.common.data.AstroBlocks;

import com.mojang.serialization.Codec;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.data.ModelData;

import com.mojang.blaze3d.vertex.PoseStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("all")
public class AEMultiblockPartRender extends DynamicRender<MultiblockControllerMachine, AEMultiblockPartRender>
        implements IControllerModelRenderer {

    public static final DynamicRenderType<MultiblockControllerMachine, AEMultiblockPartRender> TYPE =
            new DynamicRenderType<>(Codec.unit(AEMultiblockPartRender::new));

    private BakedModel activeModel;
    private BakedModel inactiveModel;

    @Override
    public DynamicRenderType<MultiblockControllerMachine, AEMultiblockPartRender> getType() {
        return TYPE;
    }

    @Override
    public void render(MultiblockControllerMachine machine, float partialTick, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight, int packedOverlay) {}

    @Override
    public boolean shouldRender(MultiblockControllerMachine machine, Vec3 cameraPos) {
        return false;
    }

    @Override
    public boolean isBlockEntityRenderer() {
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderPartModel(List<BakedQuad> quads, IMultiController controller, IMultiPart part,
                                Direction frontFacing, @Nullable Direction side, RandomSource rand,
                                @NotNull ModelData modelData, @Nullable RenderType renderType) {
        boolean active = controller instanceof IRecipeLogicMachine rlm && rlm.getRecipeLogic().isWorking();

        BlockState activeState = AstroBlocks.FUTURA_COMPUTER_CASING.get().defaultBlockState()
                .setValue(GTBlockStateProperties.ACTIVE, true);
        BlockState inactiveState = AstroBlocks.FUTURA_COMPUTER_CASING.get().defaultBlockState()
                .setValue(GTBlockStateProperties.ACTIVE, false);

        if (activeModel == null) activeModel = ModelUtils.getModelForState(activeState);
        if (inactiveModel == null) inactiveModel = ModelUtils.getModelForState(inactiveState);

        BlockState state = active ? activeState : inactiveState;
        BakedModel model = active ? activeModel : inactiveModel;
        if (model == null) return;

        MultiblockControllerMachine machine = controller.self();
        emitQuads(quads, model, machine.getLevel(), part.self().getPos(), state, side, rand, modelData, renderType);
    }

    private static void emitQuads(List<BakedQuad> quads, @Nullable BakedModel model,
                                  BlockAndTintGetter level, BlockPos pos, BlockState state,
                                  @Nullable Direction side, RandomSource rand,
                                  ModelData modelData, @Nullable RenderType renderType) {
        if (model == null) return;
        modelData = model.getModelData(level, pos, state, modelData);
        quads.addAll(model.getQuads(state, side, rand, modelData, renderType));
    }
}