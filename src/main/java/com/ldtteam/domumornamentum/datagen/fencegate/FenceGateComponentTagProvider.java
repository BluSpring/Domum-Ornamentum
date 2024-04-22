package com.ldtteam.domumornamentum.datagen.fencegate;

import com.ldtteam.domumornamentum.tag.ModTags;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import io.github.fabricators_of_create.porting_lib.tags.data.BlockTagProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class FenceGateComponentTagProvider extends BlockTagProvider
{
    public FenceGateComponentTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(ModTags.FENCE_GATE_MATERIALS)
          .addTags(
            ModTags.FENCE_MATERIALS
          );
    }

    @Override
    @NotNull
    public String getName()
    {
        return "FenceGate Tag Provider";
    }
}
