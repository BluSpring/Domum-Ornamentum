package com.ldtteam.domumornamentum.client.event.handlers;

import com.ldtteam.domumornamentum.block.IModBlocks;
import com.ldtteam.domumornamentum.block.decorative.ExtraBlock;
import com.ldtteam.domumornamentum.block.types.*;
import com.ldtteam.domumornamentum.client.screens.ArchitectsCutterScreen;
import com.ldtteam.domumornamentum.container.ModContainerTypes;
import com.ldtteam.domumornamentum.fabric.ItemPropertiesHelper;
import com.ldtteam.domumornamentum.shingles.ShingleHeightType;
import com.ldtteam.domumornamentum.util.Constants;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class ModBusEventHandler
{
    public static void init() {
        onFMLClientSetup();
    }

    public static void enqueueWork(Runnable runnable) {
        runnable.run();
    }

    public static void onFMLClientSetup()
    {
        enqueueWork(() -> ItemPropertiesHelper.register(IModBlocks.getInstance().getTrapdoor().asItem(), new ResourceLocation(Constants.TRAPDOOR_MODEL_OVERRIDE),
          (itemStack, clientLevel, livingEntity, i) -> {
            if (!itemStack.getOrCreateTag().contains("type"))
                return 0f;

              TrapdoorType trapdoorType;
              try {
                  trapdoorType = TrapdoorType.valueOf(itemStack.getOrCreateTag().getString("type").toUpperCase());
              } catch (Exception ex) {
                  trapdoorType = TrapdoorType.FULL;
              }

              return trapdoorType.ordinal();
          }));
        enqueueWork(() -> ItemPropertiesHelper.register(IModBlocks.getInstance().getDoor().asItem(), new ResourceLocation(Constants.DOOR_MODEL_OVERRIDE),
          (itemStack, clientLevel, livingEntity, i) -> handleDoorTypeOverride(itemStack)));
        enqueueWork(() -> ItemPropertiesHelper.register(IModBlocks.getInstance().getFancyDoor().asItem(), new ResourceLocation(Constants.DOOR_MODEL_OVERRIDE),
          (itemStack, clientLevel, livingEntity, i) -> handleFancyDoorTypeOverride(itemStack)));
        enqueueWork(() -> ItemPropertiesHelper.register(IModBlocks.getInstance().getFancyTrapdoor().asItem(), new ResourceLocation(Constants.TRAPDOOR_MODEL_OVERRIDE),
          (itemStack, clientLevel, livingEntity, i) -> handleFancyTrapdoorTypeOverride(itemStack)));
        enqueueWork(() -> ItemPropertiesHelper.register(IModBlocks.getInstance().getPanel().asItem(), new ResourceLocation(Constants.TRAPDOOR_MODEL_OVERRIDE),
          (itemStack, clientLevel, livingEntity, i) -> handleStaticTrapdoorTypeOverride(itemStack)));
        enqueueWork(() -> ItemPropertiesHelper.register(IModBlocks.getInstance().getPost().asItem(), new ResourceLocation(Constants.POST_MODEL_OVERRIDE),
                (itemStack, clientLevel, livingEntity, i) -> handlePostTypeOverride(itemStack)));

        enqueueWork(() -> MenuScreens.register(
          ModContainerTypes.ARCHITECTS_CUTTER.get(),
          ArchitectsCutterScreen::new
        ));

        enqueueWork(() -> {
            BlockRenderLayerMap.INSTANCE.putBlock(IModBlocks.getInstance().getArchitectsCutter(), RenderType.cutout());
            BlockRenderLayerMap.INSTANCE.putBlock(IModBlocks.getInstance().getStandingBarrel(), RenderType.cutout());
            BlockRenderLayerMap.INSTANCE.putBlock(IModBlocks.getInstance().getLayingBarrel(), RenderType.cutout());

            BlockRenderLayerMap.INSTANCE.putBlock(IModBlocks.getInstance().getShingleSlab(), RenderType.translucent());
            BlockRenderLayerMap.INSTANCE.putBlock(IModBlocks.getInstance().getPaperWall(), RenderType.translucent());
            BlockRenderLayerMap.INSTANCE.putBlock(IModBlocks.getInstance().getFence(), RenderType.translucent());
            BlockRenderLayerMap.INSTANCE.putBlock(IModBlocks.getInstance().getFenceGate(), RenderType.translucent());
            BlockRenderLayerMap.INSTANCE.putBlock(IModBlocks.getInstance().getSlab(), RenderType.translucent());
            BlockRenderLayerMap.INSTANCE.putBlock(IModBlocks.getInstance().getStair(), RenderType.translucent());
            BlockRenderLayerMap.INSTANCE.putBlock(IModBlocks.getInstance().getWall(), RenderType.translucent());
            BlockRenderLayerMap.INSTANCE.putBlock(IModBlocks.getInstance().getFancyDoor(), RenderType.translucent());
            BlockRenderLayerMap.INSTANCE.putBlock(IModBlocks.getInstance().getFancyTrapdoor(), RenderType.translucent());
            BlockRenderLayerMap.INSTANCE.putBlock(IModBlocks.getInstance().getTrapdoor(), RenderType.translucent());
            BlockRenderLayerMap.INSTANCE.putBlock(IModBlocks.getInstance().getDoor(), RenderType.translucent());
            BlockRenderLayerMap.INSTANCE.putBlock(IModBlocks.getInstance().getPanel(), RenderType.translucent());
            BlockRenderLayerMap.INSTANCE.putBlock(IModBlocks.getInstance().getPost(), RenderType.translucent());

            for (final ShingleHeightType heightType : ShingleHeightType.values())
            {
                BlockRenderLayerMap.INSTANCE.putBlock(IModBlocks.getInstance().getShingle(heightType), RenderType.translucent());
            }

            IModBlocks.getInstance().getFloatingCarpets().forEach(block -> BlockRenderLayerMap.INSTANCE.putBlock(block, RenderType.cutout()));
            IModBlocks.getInstance().getTimberFrames().forEach(block -> BlockRenderLayerMap.INSTANCE.putBlock(block, RenderType.translucent()));
            IModBlocks.getInstance().getAllBrickBlocks().forEach(block -> BlockRenderLayerMap.INSTANCE.putBlock(block, RenderType.solid()));
            IModBlocks.getInstance().getExtraTopBlocks().forEach(b -> BlockRenderLayerMap.INSTANCE.putBlock(b, ((ExtraBlock) b).getType().isTranslucent() ?  RenderType.translucent() : RenderType.solid()));
        });
    }

    private static float handleDoorTypeOverride(ItemStack itemStack)
    {
        if (!itemStack.getOrCreateTag().contains("type"))
        {
            return 0f;
        }

        DoorType doorType;
        try
        {
            doorType = DoorType.valueOf(itemStack.getOrCreateTag().getString("type").toUpperCase());
        }
        catch (Exception ex)
        {
            doorType = DoorType.FULL;
        }

        return doorType.ordinal();
    }

    private static float handleFancyDoorTypeOverride(ItemStack itemStack)
    {
        if (!itemStack.getOrCreateTag().contains("type"))
        {
            return 0f;
        }

        FancyDoorType doorType;
        try
        {
            doorType = FancyDoorType.valueOf(itemStack.getOrCreateTag().getString("type").toUpperCase());
        }
        catch (Exception ex)
        {
            doorType = FancyDoorType.FULL;
        }

        return doorType.ordinal();
    }

    private static float handleFancyTrapdoorTypeOverride(ItemStack itemStack)
    {
        if (!itemStack.getOrCreateTag().contains("type"))
        {
            return 0f;
        }

        FancyTrapdoorType doorType;
        try
        {
            doorType = FancyTrapdoorType.valueOf(itemStack.getOrCreateTag().getString("type").toUpperCase());
        }
        catch (Exception ex)
        {
            doorType = FancyTrapdoorType.FULL;
        }

        return doorType.ordinal();
    }

    private static float handleStaticTrapdoorTypeOverride(ItemStack itemStack)
    {
        if (!itemStack.getOrCreateTag().contains("type"))
        {
            return 0f;
        }

        TrapdoorType doorType;
        try
        {
            doorType = TrapdoorType.valueOf(itemStack.getOrCreateTag().getString("type").toUpperCase());
        }
        catch (Exception ex)
        {
            doorType = TrapdoorType.FULL;
        }

        return doorType.ordinal();
    }
    private static float handlePostTypeOverride(ItemStack itemStack)
    {
        if (!itemStack.getOrCreateTag().contains("type"))
        {
            return 0f;
        }

        PostType postType;
        try
        {
            postType = PostType.valueOf(itemStack.getOrCreateTag().getString("type").toUpperCase());
        }
        catch (Exception ex)
        {
            postType = PostType.PLAIN;
        }

        return postType.ordinal();
    }
}