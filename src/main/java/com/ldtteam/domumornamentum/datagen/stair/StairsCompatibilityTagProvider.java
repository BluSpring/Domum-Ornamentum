package com.ldtteam.domumornamentum.datagen.stair;

import com.ldtteam.domumornamentum.block.ModBlocks;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import io.github.fabricators_of_create.porting_lib.tags.data.BlockTagProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class StairsCompatibilityTagProvider extends BlockTagProvider
{
    public StairsCompatibilityTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(BlockTags.WOODEN_STAIRS)
          .add(
            ModBlocks.getInstance().getStair()
          );
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Stair Compatibility Tag Provider";
    }
}
