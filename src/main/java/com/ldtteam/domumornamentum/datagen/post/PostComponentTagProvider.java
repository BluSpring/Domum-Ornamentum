package com.ldtteam.domumornamentum.datagen.post;

import com.ldtteam.domumornamentum.tag.ModTags;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import io.github.fabricators_of_create.porting_lib.tags.data.BlockTagProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class PostComponentTagProvider extends BlockTagProvider
{
    public PostComponentTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {


        /*
          Exactly as others.  FUTURE, would like to allow the cutter to make slabs with vanilla materials, so those can also be placed sideways
         */
        this.tag(ModTags.POST_MATERIALS)

            .addTags(
                    ModTags.GLOBAL_DEFAULT,
                    BlockTags.PLANKS
            );
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Post Tag Provider";
    }
}
