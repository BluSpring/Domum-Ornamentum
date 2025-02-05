package com.ldtteam.domumornamentum.datagen.shingle.slab;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.ShingleSlabBlock;
import com.ldtteam.domumornamentum.block.types.ShingleSlabShapeType;
import com.ldtteam.domumornamentum.datagen.MateriallyTexturedModelBuilder;
import com.ldtteam.domumornamentum.datagen.utils.ModelBuilderUtils;
import com.ldtteam.domumornamentum.mixin.DataGeneratorAccessor;
import com.ldtteam.domumornamentum.util.Constants;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import io.github.fabricators_of_create.porting_lib.models.generators.ModelFile;
import io.github.fabricators_of_create.porting_lib.models.generators.block.BlockStateProvider;
import io.github.fabricators_of_create.porting_lib.models.generators.block.MultiPartBlockStateBuilder;
import io.github.fabricators_of_create.porting_lib.models.generators.item.ItemModelBuilder;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.StairBlock;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.Map;

public class ShingleSlabBlockStateProvider extends BlockStateProvider
{

    public ShingleSlabBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(((DataGeneratorAccessor) gen).getPackOutput(), Constants.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        createBlockstateFile(ModBlocks.getInstance().getShingleSlab());
    }


    private void createBlockstateFile(final ShingleSlabBlock shingle) {
        final MultiPartBlockStateBuilder builder = getMultipartBuilder(shingle);
        final Map<ShingleSlabShapeType, ModelFile> models = new EnumMap<>(ShingleSlabShapeType.class);
        for (Direction facingValue : StairBlock.FACING.getPossibleValues())
        {
            for (ShingleSlabShapeType shapeValue : ShingleSlabBlock.SHAPE.getPossibleValues())
            {
                builder.part()
                        .modelFile(models.computeIfAbsent(shapeValue, shape -> models().withExistingParent("block/shingle_slab/" + shapeValue.name().toLowerCase(), modLoc("block/shingle_slab/shingle_slab_" + shapeValue.name().toLowerCase() + "_spec"))
                                .customLoader(MateriallyTexturedModelBuilder::new)
                                .end()))
                        .uvLock(false)
                        .rotationY(getYFromFacing(facingValue))
                        .addModel()
                        .condition(StairBlock.FACING, facingValue)
                        .condition(ShingleSlabBlock.SHAPE, shapeValue)
                        .end();
            }
        }

        final ItemModelBuilder itemModelBuilder = itemModels().getBuilder(shingle.getRegistryName().getPath()).parent(models.get(ShingleSlabShapeType.TOP));
        ModelBuilderUtils.applyDefaultItemTransforms(itemModelBuilder);
    }

    @NotNull
    @Override
    public String getName()
    {
        return "Shingle Slabs BlockStates Provider";
    }

    private int getYFromFacing(final Direction facing)
    {
        return switch (facing) {
            default -> 0;
            case EAST -> 90;
            case SOUTH -> 180;
            case WEST -> 270;
        };
    }
}
