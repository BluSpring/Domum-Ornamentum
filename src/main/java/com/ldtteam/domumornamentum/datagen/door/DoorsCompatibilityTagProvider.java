package com.ldtteam.domumornamentum.datagen.door;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.fabric.TagProviderHelper;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import io.github.fabricators_of_create.porting_lib.data.PortingLibTagsProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class DoorsCompatibilityTagProvider extends PortingLibTagsProvider<Block>
{


    public DoorsCompatibilityTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, Registries.BLOCK, lookupProvider, existingFileHelper);
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Door Compatibility Tag Provider";
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        TagProviderHelper.add(this.tag(BlockTags.DOORS),
                        ModBlocks.getInstance().getDoor()
                );

        TagProviderHelper.add(this.tag(BlockTags.WOODEN_DOORS),
                        ModBlocks.getInstance().getDoor()
                );
    }
}
