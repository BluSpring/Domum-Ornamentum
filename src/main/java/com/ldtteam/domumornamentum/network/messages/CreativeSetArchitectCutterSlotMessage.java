package com.ldtteam.domumornamentum.network.messages;

import com.ldtteam.domumornamentum.client.screens.ArchitectsCutterScreen;
import com.ldtteam.domumornamentum.container.ArchitectsCutterContainer;
import com.ldtteam.domumornamentum.fabric.NetworkContext;
import net.fabricmc.api.EnvType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Sets the item in the {@link ArchitectsCutterScreen} for creative mode.
 */
public class CreativeSetArchitectCutterSlotMessage implements IMessage
{
    private final int slot;
    private final ItemStack stack;

    /**
     * Construct message.
     * @param slot  the slot index to set.
     * @param stack the item stack to set.
     */
    public CreativeSetArchitectCutterSlotMessage(final int slot, @NotNull final ItemStack stack)
    {
        this.slot = slot;
        this.stack = stack;
    }

    /**
     * Construct from network.
     * @param buf the buffer.
     */
    public CreativeSetArchitectCutterSlotMessage(@NotNull final FriendlyByteBuf buf)
    {
        this.slot = buf.readVarInt();
        this.stack = buf.readItem();
    }

    @Override
    public void toBytes(@NotNull final FriendlyByteBuf buf)
    {
        buf.writeVarInt(this.slot);
        buf.writeItem(this.stack);
    }

    @Nullable
    @Override
    public EnvType getExecutionSide()
    {
        return EnvType.SERVER;
    }

    @Override
    public void onExecute(@NotNull final NetworkContext ctxIn, final boolean isLogicalServer)
    {
        final ServerPlayer player = ctxIn.getSender();

        if (player != null && player.isCreative() && player.containerMenu instanceof ArchitectsCutterContainer menu)
        {
            if (this.slot >= 0 && this.slot < menu.slots.size())
            {
                final Slot menuSlot = menu.slots.get(this.slot);
                if (menuSlot.isActive() && menuSlot.allowModification(player) && menuSlot.mayPlace(this.stack))
                {
                    menuSlot.setByPlayer(this.stack);
                }
            }
        }
    }
}
