package com.ldtteam.domumornamentum.datagen.allbrick;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.AllBrickBlock;
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

public class AllBrickBlockStateProvider extends BlockStateProvider
{

    public AllBrickBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(((DataGeneratorAccessor) gen).getPackOutput(), Constants.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ModBlocks.getInstance().getAllBrickBlocks().forEach(this::registerStatesAndModelsFor);
    }

    private void registerStatesAndModelsFor(AllBrickBlock allBrickBlock) {
        final ModelFile blockModel = models().withExistingParent(
                        "block/allbrick/" + allBrickBlock.getRegistryName().getPath(),
                        modLoc("block/allbrick/" + Objects.requireNonNull(allBrickBlock.getRegistryName()).getPath() + "_spec"))
                .customLoader(MateriallyTexturedModelBuilder::new)
                .end();
        simpleBlock(allBrickBlock, blockModel);

        ModelBuilderUtils.applyDefaultItemTransforms(itemModels().getBuilder(allBrickBlock.getRegistryName().getPath())
                .parent(blockModel));
    }

    @NotNull
    @Override
    public String getName()
    {
        return "All Brick BlockStates Provider";
    }
}
