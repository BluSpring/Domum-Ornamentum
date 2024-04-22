package com.ldtteam.domumornamentum.datagen.wall.paper;

import com.ldtteam.domumornamentum.tag.ModTags;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import io.github.fabricators_of_create.porting_lib.tags.Tags;
import io.github.fabricators_of_create.porting_lib.tags.data.BlockTagProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class PaperwallComponentTagProvider extends BlockTagProvider
{
    public PaperwallComponentTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {

        this.tag(ModTags.PAPERWALL_FRAME)
          .addTags(
            BlockTags.PLANKS,
            ModTags.GLOBAL_DEFAULT
          );

        this.tag(ModTags.PAPERWALL_CENTER)
          .addTags(
            BlockTags.PLANKS,
            Tags.Blocks.STONE,
            ModTags.GLOBAL_DEFAULT
          );

    }

    @Override
    @NotNull
    public String getName()
    {
        return "Paperwall Tag Provider";
    }
}
