package com.ldtteam.domumornamentum.mixin;

import com.ldtteam.domumornamentum.fabric.model.BakedModelExtension;
import net.minecraft.client.resources.model.BakedModel;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BakedModel.class)
public interface BakedModelMixin extends BakedModelExtension {
}
