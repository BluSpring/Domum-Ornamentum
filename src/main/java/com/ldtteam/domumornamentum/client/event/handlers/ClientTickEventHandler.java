package com.ldtteam.domumornamentum.client.event.handlers;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;

public class ClientTickEventHandler
{
    private static final ClientTickEventHandler INSTANCE = new ClientTickEventHandler();

    public static ClientTickEventHandler getInstance()
    {
        return INSTANCE;
    }

    private long clientTicks = 0;
    private long nonePausedTicks = 0;

    private ClientTickEventHandler()
    {
        ClientTickEvents.START_CLIENT_TICK.register(client -> onTickClientTick());
    }

    public static void onTickClientTick()
    {
        //if (event.phase == TickEvent.Phase.START) {
            ClientTickEventHandler.getInstance().onClientTick();
        //}
    }

    private void onClientTick()
    {
        clientTicks++;
        if (!Minecraft.getInstance().isPaused()) {
            nonePausedTicks++;
        }
    }

    public long getClientTicks()
    {
        return clientTicks;
    }

    public long getNonePausedTicks()
    {
        return nonePausedTicks;
    }
}
