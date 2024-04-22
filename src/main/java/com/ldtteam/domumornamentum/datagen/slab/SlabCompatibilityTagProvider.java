package com.ldtteam.domumornamentum.datagen.slab;

import com.ldtteam.domumornamentum.block.ModBlocks;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import io.github.fabricators_of_create.porting_lib.tags.data.BlockTagProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class SlabCompatibilityTagProvider extends BlockTagProvider
{
    public SlabCompatibilityTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {

        this.tag(BlockTags.SLABS)
          .add(
            ModBlocks.getInstance().getSlab()
          );

        this.tag(BlockTags.WOODEN_SLABS)
          .add(
            ModBlocks.getInstance().getSlab()
          );
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Slab Compatibility Tag Provider";
    }
}
