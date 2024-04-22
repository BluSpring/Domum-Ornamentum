package com.ldtteam.domumornamentum.mixin;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(ItemBlockRenderTypes.class)
public interface ItemBlockRenderTypesAccessor {
    @Accessor
    static Map<Block, RenderType> getTYPE_BY_BLOCK() {
        throw new UnsupportedOperationException();
    }

    @Accessor
    static Map<Fluid, RenderType> getTYPE_BY_FLUID() {
        throw new UnsupportedOperationException();
    }

    @Accessor
    static boolean isRenderCutout() {
        throw new UnsupportedOperationException();
    }
}
