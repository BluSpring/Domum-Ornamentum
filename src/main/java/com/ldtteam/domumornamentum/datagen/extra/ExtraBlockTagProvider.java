package com.ldtteam.domumornamentum.datagen.extra;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.fabric.TagProviderHelper;
import com.ldtteam.domumornamentum.tag.ModTags;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import io.github.fabricators_of_create.porting_lib.data.PortingLibTagsProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ExtraBlockTagProvider extends PortingLibTagsProvider<Block>
{
    public ExtraBlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, Registries.BLOCK, lookupProvider, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        for (final Block block : ModBlocks.getInstance().getExtraTopBlocks())
        {
            TagProviderHelper.add(this.tag(ModTags.EXTRA_BLOCKS), block);
        }
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Extra Blocks Tag Provider";
    }
}
