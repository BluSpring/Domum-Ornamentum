package com.ldtteam.domumornamentum.event.handlers;

import com.ldtteam.domumornamentum.Network;
import com.ldtteam.domumornamentum.datagen.allbrick.AllBrickBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.allbrick.AllBrickBlockTagProvider;
import com.ldtteam.domumornamentum.datagen.allbrick.AllBrickStairBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.bricks.BrickBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.bricks.BrickBlockTagProvider;
import com.ldtteam.domumornamentum.datagen.bricks.BrickItemTagProvider;
import com.ldtteam.domumornamentum.datagen.bricks.BrickRecipeProvider;
import com.ldtteam.domumornamentum.datagen.door.DoorsBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.door.DoorsComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.door.fancy.FancyDoorsBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.door.fancy.FancyDoorsComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.extra.ExtraBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.extra.ExtraBlockTagProvider;
import com.ldtteam.domumornamentum.datagen.extra.ExtraItemTagProvider;
import com.ldtteam.domumornamentum.datagen.extra.ExtraRecipeProvider;
import com.ldtteam.domumornamentum.datagen.fence.FenceBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.fence.FenceCompatibilityTagProvider;
import com.ldtteam.domumornamentum.datagen.fence.FenceComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.fencegate.FenceGateBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.fencegate.FenceGateCompatibilityTagProvider;
import com.ldtteam.domumornamentum.datagen.fencegate.FenceGateComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.floatingcarpet.FloatingCarpetBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.floatingcarpet.FloatingCarpetBlockTagProvider;
import com.ldtteam.domumornamentum.datagen.floatingcarpet.FloatingCarpetRecipeProvider;
import com.ldtteam.domumornamentum.datagen.frames.light.FramedLightBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.frames.light.FramedLightComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.frames.timber.TimberFramesBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.frames.timber.TimberFramesComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.global.*;
import com.ldtteam.domumornamentum.datagen.panel.PanelBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.pillar.PillarBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.pillar.PillarComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.post.PostBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.post.PostComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.shingle.normal.ShinglesBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.shingle.normal.ShinglesComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.shingle.slab.ShingleSlabBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.shingle.slab.ShingleSlabComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.slab.SlabBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.slab.SlabCompatibilityTagProvider;
import com.ldtteam.domumornamentum.datagen.slab.SlabComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.stair.StairsBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.stair.StairsCompatibilityTagProvider;
import com.ldtteam.domumornamentum.datagen.stair.StairsComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.trapdoor.TrapdoorsBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.trapdoor.TrapdoorsCompatibilityTagProvider;
import com.ldtteam.domumornamentum.datagen.trapdoor.TrapdoorsComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.trapdoor.fancy.FancyTrapdoorsBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.trapdoor.fancy.FancyTrapdoorsCompatibilityTagProvider;
import com.ldtteam.domumornamentum.datagen.trapdoor.fancy.FancyTrapdoorsComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.wall.paper.PaperwallBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.wall.paper.PaperwallComponentTagProvider;
import com.ldtteam.domumornamentum.datagen.wall.vanilla.WallBlockStateProvider;
import com.ldtteam.domumornamentum.datagen.wall.vanilla.WallCompatibilityTagProvider;
import com.ldtteam.domumornamentum.datagen.wall.vanilla.WallComponentTagProvider;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

import java.util.concurrent.atomic.AtomicReference;

public class ModBusEventHandler
{
    /**
     * Called when mod is being initialized.
     *
     */
    public static void onModInit()
    {
        Network.getNetwork().registerMessages();
    }

    public static void dataGeneratorSetup(final FabricDataGenerator generator)
    {
        var pack = generator.createPack();
        var existingFileHelper = ExistingFileHelper.withResourcesFromArg();
        
        //Extra blocks
        pack.addProvider((output, future) -> new ExtraBlockStateProvider(generator, existingFileHelper));
        pack.addProvider((output, future) -> new ExtraRecipeProvider(output));
        AtomicReference<ExtraBlockTagProvider> extraBlockTagProvider = new AtomicReference<>();
        pack.addProvider((output, future) -> {
            extraBlockTagProvider.set(new ExtraBlockTagProvider(output, future, existingFileHelper));
            return extraBlockTagProvider.get();
        });
        pack.addProvider((output, future) -> new ExtraItemTagProvider(output, future, extraBlockTagProvider.get().contentsGetter(), existingFileHelper));

        //Brick blocks
        pack.addProvider((output, future) -> new BrickBlockStateProvider(generator, existingFileHelper));
        pack.addProvider((output, future) -> new BrickRecipeProvider(output));
        final AtomicReference<BrickBlockTagProvider> brickBlockTagProvider = new AtomicReference<>();
        pack.addProvider((output, future) -> {
            brickBlockTagProvider.set(new BrickBlockTagProvider(output, future, existingFileHelper));
            return brickBlockTagProvider.get();
        });
        pack.addProvider((output, future) -> new BrickItemTagProvider(output, future, brickBlockTagProvider.get()));

        pack.addProvider((output, future) -> new GlobalTagProvider(output, future, existingFileHelper));

        // Timber Frames
        pack.addProvider((output, future) -> new TimberFramesBlockStateProvider(generator, existingFileHelper));
        pack.addProvider((output, future) -> new TimberFramesComponentTagProvider(output, future, existingFileHelper));

        // Framed Light
        pack.addProvider((output, future) -> new FramedLightBlockStateProvider(generator, existingFileHelper));
        pack.addProvider((output, future) -> new FramedLightComponentTagProvider(output, future, existingFileHelper));

        //Shingles
        pack.addProvider((output, future) -> new ShinglesBlockStateProvider(generator, existingFileHelper));
        pack.addProvider((output, future) -> new ShinglesComponentTagProvider(output, future, existingFileHelper));

        //ShingleSlab
        pack.addProvider((output, future) -> new ShingleSlabBlockStateProvider(generator, existingFileHelper));
        pack.addProvider((output, future) -> new ShingleSlabComponentTagProvider(output, future, existingFileHelper));

        //Paper wall
        pack.addProvider((output, future) -> new PaperwallBlockStateProvider(generator, existingFileHelper));
        pack.addProvider((output, future) -> new PaperwallComponentTagProvider(output, future, existingFileHelper));

        //Fence
        pack.addProvider((output, future) -> new FenceBlockStateProvider(generator, existingFileHelper));
        pack.addProvider((output, future) -> new FenceComponentTagProvider(output, future, existingFileHelper));
        pack.addProvider((output, future) -> new FenceCompatibilityTagProvider(output, future, existingFileHelper));

        //FenceGate
        pack.addProvider((output, future) -> new FenceGateBlockStateProvider(generator, existingFileHelper));
        pack.addProvider((output, future) -> new FenceGateComponentTagProvider(output, future, existingFileHelper));
        pack.addProvider((output, future) -> new FenceGateCompatibilityTagProvider(output, future, existingFileHelper));

        //Slab
        pack.addProvider((output, future) -> new SlabBlockStateProvider(generator, existingFileHelper));
        pack.addProvider((output, future) -> new SlabComponentTagProvider(output, future, existingFileHelper));
        pack.addProvider((output, future) -> new SlabCompatibilityTagProvider(output, future, existingFileHelper));

        //Wall
        pack.addProvider((output, future) -> new WallBlockStateProvider(generator, existingFileHelper));
        pack.addProvider((output, future) -> new WallComponentTagProvider(output, future, existingFileHelper));
        pack.addProvider((output, future) -> new WallCompatibilityTagProvider(output, future, existingFileHelper));

        //Stair
        pack.addProvider((output, future) -> new StairsBlockStateProvider(generator, existingFileHelper));
        pack.addProvider((output, future) -> new StairsComponentTagProvider(output, future, existingFileHelper));
        pack.addProvider((output, future) -> new StairsCompatibilityTagProvider(output, future, existingFileHelper));

        //Trapdoor
        pack.addProvider((output, future) -> new TrapdoorsBlockStateProvider(generator, existingFileHelper));
        pack.addProvider((output, future) -> new TrapdoorsComponentTagProvider(output, future, existingFileHelper));
        pack.addProvider((output, future) -> new TrapdoorsCompatibilityTagProvider(output, future, existingFileHelper));

        pack.addProvider((output, future) -> new PanelBlockStateProvider(generator, existingFileHelper));

        //Post
        pack.addProvider((output, future) -> new PostBlockStateProvider(generator, existingFileHelper));
        pack.addProvider((output, future) -> new PostComponentTagProvider(output, future, existingFileHelper));


        //Fancy Trapdoor
        pack.addProvider((output, future) -> new FancyTrapdoorsBlockStateProvider(generator, existingFileHelper));
        pack.addProvider((output, future) -> new FancyTrapdoorsComponentTagProvider(output, future, existingFileHelper));
        pack.addProvider((output, future) -> new FancyTrapdoorsCompatibilityTagProvider(output, future, existingFileHelper));

        //Door
        pack.addProvider((output, future) -> new DoorsBlockStateProvider(generator, existingFileHelper));
        pack.addProvider((output, future) -> new DoorsComponentTagProvider(output, future, existingFileHelper));
        // Commented to temporarily prevent the tag generation issue for doors
        //pack.addProvider((output, future) -> new DoorsCompatibilityTagProvider(output, future, existingFileHelper));

        //FancyDoor
        pack.addProvider((output, future) -> new FancyDoorsBlockStateProvider(generator, existingFileHelper));
        pack.addProvider((output, future) -> new FancyDoorsComponentTagProvider(output, future, existingFileHelper));
        //pack.addProvider((output, future) -> new FancyDoorsCompatibilityTagProvider(output, future, existingFileHelper));

        //Floating carpets
        pack.addProvider((output, future) -> new FloatingCarpetBlockStateProvider(generator, existingFileHelper));
        pack.addProvider((output, future) -> new FloatingCarpetBlockTagProvider(output, future, existingFileHelper));
        pack.addProvider((output, future) -> new FloatingCarpetRecipeProvider(output));

        //Pillars
        pack.addProvider((output, future) -> new PillarBlockStateProvider(generator, existingFileHelper));
        pack.addProvider((output, future) -> new PillarComponentTagProvider(output, future, existingFileHelper));

        //AllBrick
        pack.addProvider((output, future) -> new AllBrickBlockStateProvider(generator, existingFileHelper));
        pack.addProvider((output, future) -> new AllBrickStairBlockStateProvider(generator, existingFileHelper));

        pack.addProvider((output, future) -> new AllBrickBlockTagProvider(output, future, existingFileHelper));

        //Global
        pack.addProvider((output, future) -> new GlobalRecipeProvider(output));
        pack.addProvider((output, future) -> new GlobalLanguageProvider(output));
        pack.addProvider((output, future) -> new GlobalLootTableProvider(output));
        pack.addProvider((output, future) -> new MateriallyTexturedBlockRecipeProvider(output));
    }
}
