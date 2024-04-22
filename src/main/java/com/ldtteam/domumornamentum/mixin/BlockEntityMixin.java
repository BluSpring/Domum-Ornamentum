package com.ldtteam.domumornamentum.mixin;

import com.ldtteam.domumornamentum.fabric.BlockEntityExtension;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntity.class)
public class BlockEntityMixin implements BlockEntityExtension {
    @Inject(method = "setRemoved", at = @At("TAIL"))
    private void domum$updateModelData(CallbackInfo ci) {

    }
}
