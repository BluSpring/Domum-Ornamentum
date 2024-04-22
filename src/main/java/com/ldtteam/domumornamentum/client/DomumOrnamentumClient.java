package com.ldtteam.domumornamentum.client;

import com.ldtteam.domumornamentum.client.event.handlers.ClientTickEventHandler;
import com.ldtteam.domumornamentum.client.event.handlers.MateriallyTexturedBlockPreviewRenderHandler;
import com.ldtteam.domumornamentum.client.event.handlers.ModBusEventHandler;
import com.ldtteam.domumornamentum.client.event.handlers.RegisterColorHandlersEventHandler;
import com.ldtteam.domumornamentum.client.model.loader.MateriallyTexturedModelLoader;
import com.ldtteam.domumornamentum.fabric.model.ModelDataManager;
import net.fabricmc.api.ClientModInitializer;

public class DomumOrnamentumClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModelDataManager.init();
        MateriallyTexturedModelLoader.init();
        ClientTickEventHandler.getInstance();
        MateriallyTexturedBlockPreviewRenderHandler.init();
        ModBusEventHandler.init();
        RegisterColorHandlersEventHandler.init();
    }
}
