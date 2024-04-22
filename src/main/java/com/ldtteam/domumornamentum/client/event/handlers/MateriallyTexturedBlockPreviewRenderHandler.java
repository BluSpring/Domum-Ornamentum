package com.ldtteam.domumornamentum.client.event.handlers;

import com.ldtteam.domumornamentum.client.render.ModelGhostRenderer;
import com.ldtteam.domumornamentum.util.ItemStackUtils;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class MateriallyTexturedBlockPreviewRenderHandler {
    public static void init() {
        WorldRenderEvents.BEFORE_BLOCK_OUTLINE.register((context, hitResult) -> {
            renderMateriallyTexturedBlockPreview(context);
            return true;
        });
    }

    public static void renderMateriallyTexturedBlockPreview(final WorldRenderContext context) {
        final HitResult rayTraceResult = Minecraft.getInstance().hitResult;
        if (!(rayTraceResult instanceof final BlockHitResult blockRayTraceResult) || blockRayTraceResult.getType() == HitResult.Type.MISS)
            return;

        final Player playerEntity = Minecraft.getInstance().player;
        if (playerEntity == null || playerEntity.isSpectator())
            return;

        final ItemStack heldStack = ItemStackUtils.getMateriallyTexturedItemStackFromPlayer(playerEntity);
        if (heldStack.isEmpty())
            return;

        Vec3 targetedRenderPos = Vec3.atLowerCornerOf(blockRayTraceResult.getBlockPos().offset(blockRayTraceResult.getDirection().getNormal()));
        renderGhost(context, heldStack, targetedRenderPos, blockRayTraceResult);
    }

    private static void renderGhost(
            final WorldRenderContext context,
            final ItemStack heldStack,
            final Vec3 targetedRenderPos, BlockHitResult blockRayTraceResult) {
        ModelGhostRenderer.getInstance().renderGhost(
                context,
                heldStack,
                targetedRenderPos,
                blockRayTraceResult,
                false
        );
    }

}
