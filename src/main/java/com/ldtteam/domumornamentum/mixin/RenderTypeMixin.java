package com.ldtteam.domumornamentum.mixin;

import com.ldtteam.domumornamentum.fabric.rendering.RenderTypeExtension;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(RenderType.class)
public abstract class RenderTypeMixin implements RenderTypeExtension {
    @Shadow public static List<RenderType> chunkBufferLayers() {
        throw new IllegalStateException("AAAAA");
    }

    @Unique private int chunkLayerId = -1;

    @Override
    public int domum$getChunkRenderLayerId() {
        return chunkLayerId;
    }

    @Override
    public void domum$setChunkRenderLayerId(int id) {
        this.chunkLayerId = id;
    }

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void domum$setLayers(CallbackInfo ci) {
        int i = 0;
        for (var layer : chunkBufferLayers()) {
            ((RenderTypeExtension) layer).domum$setChunkRenderLayerId(i++);
        }
    }
}
