package com.ldtteam.domumornamentum.client.model.loader;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.ldtteam.domumornamentum.client.model.geometry.MateriallyTexturedGeometry;
import com.ldtteam.domumornamentum.util.Constants;
import io.github.fabricators_of_create.porting_lib.models.geometry.IGeometryLoader;
import io.github.fabricators_of_create.porting_lib.models.geometry.RegisterGeometryLoadersCallback;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class MateriallyTexturedModelLoader implements IGeometryLoader<MateriallyTexturedGeometry>
{
    public static void init() {
        RegisterGeometryLoadersCallback.EVENT.register(loaders -> {
            onModelRegistry(loaders);
        });
    }

    public static void onModelRegistry(Map<ResourceLocation, IGeometryLoader<?>> loaders)
    {
        loaders.put(new ResourceLocation(Constants.MOD_ID, Constants.MATERIALLY_TEXTURED_MODEL_LOADER), new MateriallyTexturedModelLoader());
    }

    @Override
    public MateriallyTexturedGeometry read(JsonObject jsonObject, JsonDeserializationContext deserializationContext) throws JsonParseException {
        final String parent = jsonObject.get("parent").getAsString();
        final ResourceLocation parentLocation = new ResourceLocation(parent);

        return new MateriallyTexturedGeometry(parentLocation);
    }
}
