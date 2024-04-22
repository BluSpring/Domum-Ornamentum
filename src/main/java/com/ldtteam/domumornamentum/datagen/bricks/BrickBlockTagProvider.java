package com.ldtteam.domumornamentum.datagen.bricks;

import com.ldtteam.domumornamentum.block.IModBlocks;
import com.ldtteam.domumornamentum.fabric.TagProviderHelper;
import com.ldtteam.domumornamentum.tag.ModTags;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import io.github.fabricators_of_create.porting_lib.tags.data.BlockTagProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class BrickBlockTagProvider extends BlockTagProvider
{
    public BrickBlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider);
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Brick Blocks Tag Provider";
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider holderLookupProvider) {
        var bricks = IModBlocks.getInstance().getBricks().toArray(Block[]::new);
        var extraBlocks = IModBlocks.getInstance().getExtraTopBlocks().toArray(Block[]::new);

        TagProviderHelper.add(this.tag(ModTags.BRICKS), bricks);
        TagProviderHelper.add(this.tag(ModTags.BRICKS), extraBlocks);
    }
}
