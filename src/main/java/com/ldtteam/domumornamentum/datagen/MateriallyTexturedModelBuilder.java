package com.ldtteam.domumornamentum.datagen;

import com.ldtteam.domumornamentum.util.Constants;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import io.github.fabricators_of_create.porting_lib.models.generators.CustomLoaderBuilder;
import io.github.fabricators_of_create.porting_lib.models.generators.ModelBuilder;
import net.minecraft.resources.ResourceLocation;

public class MateriallyTexturedModelBuilder<T extends ModelBuilder<T>> extends CustomLoaderBuilder<T>
{
    public MateriallyTexturedModelBuilder(final T parent,
                                          final ExistingFileHelper existingFileHelper)
    {
        super(new ResourceLocation(Constants.MOD_ID, Constants.MATERIALLY_TEXTURED_MODEL_LOADER), parent, existingFileHelper);
    }
}
