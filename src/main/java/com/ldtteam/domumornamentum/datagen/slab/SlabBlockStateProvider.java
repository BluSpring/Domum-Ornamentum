package com.ldtteam.domumornamentum.datagen.slab;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.vanilla.SlabBlock;
import com.ldtteam.domumornamentum.datagen.MateriallyTexturedModelBuilder;
import com.ldtteam.domumornamentum.datagen.utils.ModelBuilderUtils;
import com.ldtteam.domumornamentum.mixin.DataGeneratorAccessor;
import com.ldtteam.domumornamentum.util.Constants;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import io.github.fabricators_of_create.porting_lib.models.generators.block.BlockStateProvider;
import io.github.fabricators_of_create.porting_lib.models.generators.block.MultiPartBlockStateBuilder;
import io.github.fabricators_of_create.porting_lib.models.generators.item.ItemModelBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.state.properties.SlabType;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.world.level.block.SlabBlock.TYPE;

public class SlabBlockStateProvider extends BlockStateProvider
{

    public SlabBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(((DataGeneratorAccessor) gen).getPackOutput(), Constants.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        createBlockstateFile(ModBlocks.getInstance().getSlab());
    }

    private void createBlockstateFile(final SlabBlock slabBlock)
    {
        final MultiPartBlockStateBuilder builder = getMultipartBuilder(slabBlock);
        for (SlabType value : SlabType.values()) {
            builder.part()
              .modelFile(models().withExistingParent("block/slab/" + value.getSerializedName(), modLoc("block/slab/slab_" + value.getSerializedName() + "_spec"))
                           .customLoader(MateriallyTexturedModelBuilder::new)
                           .end())
              .addModel()
              .condition(TYPE, value)
              .end();
        }

        final ItemModelBuilder itemModelBuilder = itemModels().withExistingParent(slabBlock.getRegistryName().getPath(), modLoc("item/slab/slab_spec"))
                                                    .customLoader(MateriallyTexturedModelBuilder::new)
                                                    .end();
        ModelBuilderUtils.applyDefaultItemTransforms(itemModelBuilder);
    }

    @NotNull
    @Override
    public String getName()
    {
        return "Slab BlockStates Provider";
    }
}
