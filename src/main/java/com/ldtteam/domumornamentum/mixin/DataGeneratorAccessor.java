package com.ldtteam.domumornamentum.mixin;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(DataGenerator.class)
public interface DataGeneratorAccessor {
    @Accessor("vanillaPackOutput")
    PackOutput getPackOutput();
}
