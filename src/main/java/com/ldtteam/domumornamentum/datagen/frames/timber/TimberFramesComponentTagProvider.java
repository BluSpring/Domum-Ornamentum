package com.ldtteam.domumornamentum.datagen.frames.timber;

import com.ldtteam.domumornamentum.tag.ModTags;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import io.github.fabricators_of_create.porting_lib.tags.Tags;
import io.github.fabricators_of_create.porting_lib.tags.data.BlockTagProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TimberFramesComponentTagProvider extends BlockTagProvider
{
    public TimberFramesComponentTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(ModTags.TIMBERFRAMES_FRAME)
          .add(
            Blocks.BRICKS,
            Blocks.DEEPSLATE,
            Blocks.DEEPSLATE_BRICKS,
            Blocks.COBBLED_DEEPSLATE,
            Blocks.POLISHED_DEEPSLATE,
            Blocks.POLISHED_BLACKSTONE
          )
          .addTags(
            ModTags.GLOBAL_DEFAULT,
            BlockTags.PLANKS,
            Tags.Blocks.OBSIDIAN,
            Tags.Blocks.STONE
          );

        this.tag(ModTags.TIMBERFRAMES_CENTER)
          .add(
            Blocks.BRICKS,
            Blocks.DEEPSLATE,
            Blocks.DEEPSLATE_BRICKS,
            Blocks.COBBLED_DEEPSLATE,
            Blocks.POLISHED_DEEPSLATE,
            Blocks.POLISHED_BLACKSTONE
          )
          .addTags(
            ModTags.GLOBAL_DEFAULT,
            BlockTags.PLANKS,
            Tags.Blocks.COBBLESTONE,
            Tags.Blocks.STONE,
            Tags.Blocks.END_STONES,
            Tags.Blocks.NETHERRACK,
            Tags.Blocks.OBSIDIAN,
            Tags.Blocks.SANDSTONE,
            BlockTags.DIRT
          );

    }

    @Override
    @NotNull
    public String getName()
    {
        return "Timber Frames Tag Provider";
    }
}
