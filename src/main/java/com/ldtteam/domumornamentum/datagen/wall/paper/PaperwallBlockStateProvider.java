package com.ldtteam.domumornamentum.datagen.wall.paper;

import com.ldtteam.domumornamentum.block.AbstractBlockPane;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.PaperWallBlock;
import com.ldtteam.domumornamentum.datagen.MateriallyTexturedModelBuilder;
import com.ldtteam.domumornamentum.datagen.utils.ModelBuilderUtils;
import com.ldtteam.domumornamentum.mixin.DataGeneratorAccessor;
import com.ldtteam.domumornamentum.util.Constants;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import io.github.fabricators_of_create.porting_lib.models.generators.block.BlockStateProvider;
import io.github.fabricators_of_create.porting_lib.models.generators.block.MultiPartBlockStateBuilder;
import io.github.fabricators_of_create.porting_lib.models.generators.item.ItemModelBuilder;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Objects;

public class PaperwallBlockStateProvider extends BlockStateProvider {


    public PaperwallBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(((DataGeneratorAccessor) gen).getPackOutput(), Constants.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        createBlockstateFile(ModBlocks.getInstance().getPaperWall());
    }

    private void createBlockstateFile(final PaperWallBlock paperWallBlock) {
        final MultiPartBlockStateBuilder builder = getMultipartBuilder(paperWallBlock);
        builder.part()
                .modelFile(models().withExistingParent("block/paperwall/blockpaperwall_post", modLoc("block/paperwall/blockpaperwall_post_spec"))
                        .customLoader(MateriallyTexturedModelBuilder::new)
                        .end())
                .uvLock(true)
                .addModel()
                .end();

        for (final Direction possibleValue : HorizontalDirectionalBlock.FACING.getPossibleValues()) {
            builder.part()
                    .modelFile(models().withExistingParent("block/paperwall/blockpaperwall_side_" + possibleValue.name().toLowerCase(Locale.ROOT), modLoc("block/paperwall/blockpaperwall_side_" + possibleValue.name().toLowerCase(Locale.ROOT) + "_spec"))
                            .customLoader(MateriallyTexturedModelBuilder::new)
                            .end())
                    .uvLock(true)
                    .addModel()
                    .condition(Objects.requireNonNull(AbstractBlockPane.PROPERTIES.get(possibleValue)), true)
                    .end()
                    .part()
                    .modelFile(models().withExistingParent("block/paperwall/blockpaperwall_side_off_" + possibleValue.name().toLowerCase(Locale.ROOT), modLoc("block/paperwall/blockpaperwall_side_off_" + possibleValue.name().toLowerCase(Locale.ROOT) + "_spec"))
                            .customLoader(MateriallyTexturedModelBuilder::new)
                            .end())
                    .uvLock(true)
                    .addModel()
                    .condition(Objects.requireNonNull(AbstractBlockPane.PROPERTIES.get(possibleValue)), false)
                    .end();
        }

        final ItemModelBuilder itemModelBuilder = itemModels().withExistingParent(paperWallBlock.getRegistryName().getPath(), modLoc("item/paperwall/blockpaperwall_spec"))
                .customLoader(MateriallyTexturedModelBuilder::new)
                .end();

        ModelBuilderUtils.applyDefaultItemTransforms(itemModelBuilder);
    }

    @NotNull
    @Override
    public String getName() {
        return "Paperwall BlockStates Provider";
    }
}
