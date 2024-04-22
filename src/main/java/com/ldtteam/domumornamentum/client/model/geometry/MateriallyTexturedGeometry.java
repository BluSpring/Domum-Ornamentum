package com.ldtteam.domumornamentum.client.model.geometry;

import com.ldtteam.domumornamentum.client.model.baked.MateriallyTexturedBakedModel;
import io.github.fabricators_of_create.porting_lib.models.geometry.IUnbakedGeometry;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public class MateriallyTexturedGeometry implements IUnbakedGeometry<MateriallyTexturedGeometry>
{
    private final ResourceLocation innerModelLocation;

    public MateriallyTexturedGeometry(final ResourceLocation innerModelLocation)
    {
        this.innerModelLocation = innerModelLocation;
    }

    @Override
    public BakedModel bake(BlockModel context, ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides, ResourceLocation modelLocation, boolean isGui3d) {
        final UnbakedModel innerModel = baker.getModel(this.innerModelLocation);

        if (!(innerModel instanceof BlockModel)) {
            throw new IllegalStateException("BlockModel parent has to be a block model.");
        }

        final BakedModel innerBakedModel = innerModel.bake(
            baker, spriteGetter, modelState, modelLocation
        );

        return new MateriallyTexturedBakedModel(innerBakedModel);
    }
}
