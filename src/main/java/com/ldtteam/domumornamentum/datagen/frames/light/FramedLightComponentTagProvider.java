package com.ldtteam.domumornamentum.datagen.frames.light;

import com.ldtteam.domumornamentum.tag.ModTags;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import io.github.fabricators_of_create.porting_lib.tags.data.BlockTagProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class FramedLightComponentTagProvider extends BlockTagProvider
{
    public FramedLightComponentTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(ModTags.FRAMED_LIGHT_CENTER)
          .add(
            Blocks.GLOWSTONE,
            Blocks.SEA_LANTERN,
            Blocks.OCHRE_FROGLIGHT,
            Blocks.PEARLESCENT_FROGLIGHT,
            Blocks.VERDANT_FROGLIGHT,
            Blocks.SHROOMLIGHT
          );
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Framed Light Tag Provider";
    }
}
