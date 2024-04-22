package com.ldtteam.domumornamentum.datagen.extra;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.ExtraBlock;
import com.ldtteam.domumornamentum.mixin.DataGeneratorAccessor;
import com.ldtteam.domumornamentum.util.Constants;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import io.github.fabricators_of_create.porting_lib.models.generators.ModelFile;
import io.github.fabricators_of_create.porting_lib.models.generators.block.BlockStateProvider;
import net.minecraft.data.DataGenerator;
import org.jetbrains.annotations.NotNull;

public class ExtraBlockStateProvider extends BlockStateProvider
{

    public ExtraBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(((DataGeneratorAccessor) gen).getPackOutput(), Constants.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ModBlocks.getInstance().getExtraTopBlocks().forEach(this::registerStatesAndModelsFor);
    }

    private void registerStatesAndModelsFor(ExtraBlock extraBlock) {
        final ModelFile cubeAll = models().cubeAll("block/extra/" + extraBlock.getType().getCategory().name().toLowerCase() + "/" + extraBlock.getRegistryName().getPath(), modLoc("block/extra/" + extraBlock.getRegistryName().getPath()));
        simpleBlockWithItem(extraBlock, cubeAll);
    }

    @NotNull
    @Override
    public String getName()
    {
        return "Extra BlockStates Provider";
    }
}
