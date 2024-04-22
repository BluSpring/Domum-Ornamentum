package com.ldtteam.domumornamentum.datagen.extra;

import com.ldtteam.domumornamentum.tag.ModTags;
import com.ldtteam.domumornamentum.util.Constants;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import io.github.fabricators_of_create.porting_lib.data.PortingLibItemTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ExtraItemTagProvider extends PortingLibItemTagsProvider
{

    public ExtraItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> providerCompletableFuture, CompletableFuture<TagLookup<Block>> blockTagsProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, providerCompletableFuture, blockTagsProvider, Constants.MOD_ID);
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Extra Item Tag Provider";
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        copy(ModTags.EXTRA_BLOCKS, ModTags.EXTRA_BLOCK_ITEMS);
    }
}
