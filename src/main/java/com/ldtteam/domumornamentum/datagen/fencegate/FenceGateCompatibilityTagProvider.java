package com.ldtteam.domumornamentum.datagen.fencegate;

import com.ldtteam.domumornamentum.block.ModBlocks;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import io.github.fabricators_of_create.porting_lib.tags.data.BlockTagProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class FenceGateCompatibilityTagProvider extends BlockTagProvider
{
    public FenceGateCompatibilityTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {

        this.tag(BlockTags.FENCE_GATES)
          .add(
            ModBlocks.getInstance().getFenceGate()
          );
    }

    @Override
    @NotNull
    public String getName()
    {
        return "FenceGate Compatibility Tag Provider";
    }
}
