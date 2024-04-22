package com.ldtteam.domumornamentum.client.event.handlers;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.client.color.MateriallyTexturedBlockBlockColor;
import com.ldtteam.domumornamentum.client.color.MateriallyTexturedBlockItemColor;
import io.github.fabricators_of_create.porting_lib.event.client.ColorHandlersCallback;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;

public class RegisterColorHandlersEventHandler {
    public static void init() {
        ColorHandlersCallback.BLOCK.register(RegisterColorHandlersEventHandler::onRegisterColorHandlersBlock);
        ColorHandlersCallback.ITEM.register(RegisterColorHandlersEventHandler::onRegisterColorHandlersItem);
    }

    public static void onRegisterColorHandlersItem(ItemColors event, BlockColors blockColors) {
        event.register(
                new MateriallyTexturedBlockItemColor(),
                ModBlocks.getMateriallyTexturableItems()
        );
    }

    public static void onRegisterColorHandlersBlock(BlockColors event) {
        event.register(
                new MateriallyTexturedBlockBlockColor(),
                ModBlocks.getMateriallyTexturableBlocks()
        );
    }
}
