package com.ldtteam.domumornamentum.datagen.frames.light;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.FramedLightBlock;
import com.ldtteam.domumornamentum.datagen.MateriallyTexturedModelBuilder;
import com.ldtteam.domumornamentum.datagen.utils.ModelBuilderUtils;
import com.ldtteam.domumornamentum.mixin.DataGeneratorAccessor;
import com.ldtteam.domumornamentum.util.Constants;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import io.github.fabricators_of_create.porting_lib.models.generators.ModelFile;
import io.github.fabricators_of_create.porting_lib.models.generators.block.BlockStateProvider;
import net.minecraft.data.DataGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class FramedLightBlockStateProvider extends BlockStateProvider
{

    public FramedLightBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(((DataGeneratorAccessor) gen).getPackOutput(), Constants.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ModBlocks.getInstance().getFramedLights().forEach(this::registerStatesAndModelsFor);
    }

    private void registerStatesAndModelsFor(FramedLightBlock framedLightBlock) {
        final ModelFile blockModel = models().withExistingParent(
                        "block/framed_light/" + framedLightBlock.getRegistryName().getPath(),
                        modLoc("block/framed_light/" + Objects.requireNonNull(framedLightBlock.getRegistryName()).getPath() + "_spec"))
                .customLoader(MateriallyTexturedModelBuilder::new)
                .end();
        simpleBlock(framedLightBlock, blockModel);

        ModelBuilderUtils.applyDefaultItemTransforms(itemModels().getBuilder(framedLightBlock.getRegistryName().getPath())
                .parent(blockModel));
    }

    @NotNull
    @Override
    public String getName()
    {
        return "Framed Light BlockStates Provider";
    }
}
