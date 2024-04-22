package com.ldtteam.domumornamentum.fabric;

import io.github.fabricators_of_create.porting_lib.util.NetworkDirection;
import net.minecraft.network.Connection;
import net.minecraft.network.PacketListener;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.jetbrains.annotations.Nullable;

public record NetworkContext(
    Connection networkManager,
    NetworkDirection direction
) {
    /**
     * When available, gets the sender for packets that are sent from a client to the server.
     */
    @Nullable
    public ServerPlayer getSender()
    {
        PacketListener netHandler = networkManager.getPacketListener();
        if (netHandler instanceof ServerGamePacketListenerImpl)
        {
            ServerGamePacketListenerImpl netHandlerPlayServer = (ServerGamePacketListenerImpl) netHandler;
            return netHandlerPlayServer.player;
        }
        return null;
    }
}
