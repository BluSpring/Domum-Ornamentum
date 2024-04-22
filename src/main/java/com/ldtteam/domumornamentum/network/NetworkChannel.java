package com.ldtteam.domumornamentum.network;

import com.ldtteam.domumornamentum.fabric.NetworkContext;
import com.ldtteam.domumornamentum.network.messages.CreativeSetArchitectCutterSlotMessage;
import com.ldtteam.domumornamentum.network.messages.IMessage;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;
import java.util.function.Supplier;

import static com.ldtteam.domumornamentum.util.Constants.MOD_ID;

/**
 * Simple network channel.
 */
public class NetworkChannel
{
    /**
     * Forge network channel
     */
    private final SimpleChannel rawChannel;

    /**
     * Creates a new instance of network channel.
     *
     * @param channelName unique channel name
     * @throws IllegalArgumentException if channelName already exists
     */
    public NetworkChannel(final String channelName)
    {
        final String modVersion = FabricLoader.getInstance().getModContainer(MOD_ID).orElseThrow().getMetadata().getVersion().getFriendlyString();
        rawChannel = new SimpleChannel(new ResourceLocation(MOD_ID, channelName));
        //rawChannel = NetworkRegistry.newSimpleChannel(new ResourceLocation(MOD_ID, channelName), () -> modVersion, str -> str.equals(modVersion), str -> str.equals(modVersion));
    }

    public void registerMessages()
    {
        int idx = 0;

        registerMessage(++idx, CreativeSetArchitectCutterSlotMessage.class, CreativeSetArchitectCutterSlotMessage::new);
    }

    /**
     * Register a message into rawChannel.
     *
     * @param <MSG>      message class type
     * @param id         network id
     * @param msgClazz   message class
     * @param msgCreator supplier with new instance of msgClazz
     */
    private <MSG extends IMessage> void registerMessage(final int id, final Class<MSG> msgClazz, final Function<FriendlyByteBuf, MSG> msgCreator)
    {
        rawChannel.registerC2SPacket(msgClazz, id, msgCreator);
        rawChannel.registerS2CPacket(msgClazz, id, msgCreator);
        //rawChannel.registerMessage(id, msgClazz, IMessage::toBytes, msgCreator, NetworkChannel::execute);
    }

    /**
     * Execute the received message.
     *
     * @param msg         the message.
     * @param ctxSupplier the context supplier.
     */
    @ApiStatus.Internal
    public static void execute(@NotNull final IMessage msg, @NotNull final Supplier<NetworkContext> ctxSupplier)
    {
        final NetworkContext ctx = ctxSupplier.get();
        final EnvType packetOrigin = ctx.direction().getOriginationSide();
        if (msg.getExecutionSide() == null || !packetOrigin.equals(msg.getExecutionSide()))
        {
            msg.onExecute(ctx, packetOrigin.equals(EnvType.CLIENT));
        }
    }

    /**
     * Sends to server.
     *
     * @param msg message to send
     */
    public void sendToServer(final IMessage msg)
    {
        rawChannel.sendToServer(msg);
    }

    /**
     * Sends to player.
     *
     * @param msg    message to send
     * @param player target player
     */
    public void sendToPlayer(final IMessage msg, final ServerPlayer player)
    {
        //rawChannel.send(PacketDistributor.PLAYER.with(() -> player), msg);
        rawChannel.sendToClient(msg, player);
    }
}
