package com.astro.core.mixin.botania;

import net.minecraft.world.level.block.Block;

import com.astro.core.common.data.AstroBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import vazkii.botania.common.block.block_entity.BotaniaBlockEntities;
import vazkii.botania.common.block.block_entity.RunicAltarBlockEntity;

import java.util.Arrays;

@Mixin(value = BotaniaBlockEntities.class, remap = false)
public class ApothecaryBlockEntityMixin {

    @ModifyArg(
               method = "<clinit>",
               at = @At(
                        value = "INVOKE",
                        target = "Lvazkii/botania/common/block/block_entity/BotaniaBlockEntities;type(Lnet/minecraft/resources/ResourceLocation;Ljava/util/function/BiFunction;[Lnet/minecraft/world/level/block/Block;)Lnet/minecraft/world/level/block/entity/BlockEntityType;",
                        ordinal = 0),
               index = 2)
    private static Block[] addAstroApothecaries(Block[] blocks) {
        Block[] newBlocks = Arrays.copyOf(blocks, blocks.length + 1);
        newBlocks[blocks.length] = AstroBlocks.ASTEROID_APOTHECARY.get();
        return newBlocks;
    }

    @Mixin(value = RunicAltarBlockEntity.class, remap = false)
    public static interface RunicAltarBlockEntityAccessor {

        @Accessor("mana")
        int astro$getMana();

        @Accessor("recipeKeepTicks")
        int astro$getRecipeKeepTicks();
    }
}
