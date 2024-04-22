package com.ldtteam.domumornamentum.datagen.bricks;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.BrickBlock;
import com.ldtteam.domumornamentum.mixin.DataGeneratorAccessor;
import com.ldtteam.domumornamentum.util.Constants;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import io.github.fabricators_of_create.porting_lib.models.generators.ModelFile;
import io.github.fabricators_of_create.porting_lib.models.generators.block.BlockStateProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class BrickBlockStateProvider extends BlockStateProvider
{
    public BrickBlockStateProvider(DataGenerator generator, ExistingFileHelper existingFileHelper)
    {
        super(((DataGeneratorAccessor) generator).getPackOutput(), Constants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels()
    {
        ModBlocks.getInstance().getBricks().forEach(this::registerStatesAndModelsFor);
    }

    private void registerStatesAndModelsFor(final BrickBlock brickBlock) {
        final ModelFile blockModel = models().cubeAll("block/brick/" + brickBlock.getType().getSerializedName() + "_brick", new ResourceLocation(Constants.MOD_ID, "block/brick/" + brickBlock.getType().getSerializedName()));
        simpleBlock(brickBlock, blockModel);
        simpleBlockItem(brickBlock, blockModel);
    }

    @NotNull
    @Override
    public String getName()
    {
        return "Brick BlockStates Provider";
    }
}
