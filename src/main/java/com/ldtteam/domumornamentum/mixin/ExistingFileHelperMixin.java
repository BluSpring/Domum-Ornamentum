package com.ldtteam.domumornamentum.mixin;

import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// i got pissed.
@Mixin(value = ExistingFileHelper.class, remap = false)
public class ExistingFileHelperMixin {
    @Inject(method = "exists(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/server/packs/PackType;)Z", at = @At("HEAD"), cancellable = true)
    private void justForceTheFuckingThing(ResourceLocation loc, PackType packType, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }

    @Inject(method = "exists(Lnet/minecraft/resources/ResourceLocation;Lio/github/fabricators_of_create/porting_lib/data/ExistingFileHelper$IResourceType;)Z", at = @At("HEAD"), cancellable = true)
    private void justForceTheFuckingThing(ResourceLocation loc, ExistingFileHelper.IResourceType type, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }

    @Inject(method = "exists(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/server/packs/PackType;Ljava/lang/String;Ljava/lang/String;)Z", at = @At("HEAD"), cancellable = true)
    private void justForceTheFuckingThing(ResourceLocation loc, PackType packType, String pathSuffix, String pathPrefix, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
}
