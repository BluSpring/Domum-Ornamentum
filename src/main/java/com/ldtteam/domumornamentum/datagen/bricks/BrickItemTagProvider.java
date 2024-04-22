package com.ldtteam.domumornamentum.datagen.bricks;

import com.ldtteam.domumornamentum.tag.ModTags;
import io.github.fabricators_of_create.porting_lib.tags.data.ItemTagProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class BrickItemTagProvider extends ItemTagProvider
{
    public BrickItemTagProvider(FabricDataOutput packOutput, CompletableFuture<HolderLookup.Provider> providerCompletableFuture, BlockTagProvider blockTagsProvider) {
        super(packOutput, providerCompletableFuture, blockTagsProvider);
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Brick Item Tag Provider";
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        copy(ModTags.BRICKS, ModTags.BRICK_ITEMS);
    }
}
