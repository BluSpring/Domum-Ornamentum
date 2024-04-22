package com.ldtteam.domumornamentum.fabric.model;

import com.ldtteam.domumornamentum.fabric.rendering.ChunkRenderTypeSet;
import com.ldtteam.domumornamentum.fabric.rendering.ItemBlockRenderTypesExtension;
import com.ldtteam.domumornamentum.fabric.rendering.RenderTypeHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface BakedModelExtension {
    private BakedModel self()
    {
        return (BakedModel) this;
    }

    /**
     * A null {@link RenderType} is used for the breaking overlay as well as non-standard rendering, so models should return all their quads.
     */
    @NotNull
    default List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData data, @Nullable RenderType renderType)
    {
        return self().getQuads(state, side, rand);
    }

    default boolean useAmbientOcclusion(BlockState state)
    {
        return self().useAmbientOcclusion();
    }

    default boolean useAmbientOcclusion(BlockState state, RenderType renderType)
    {
        return this.useAmbientOcclusion(state);
    }

    /**
     * Applies a transform for the given {@link ItemDisplayContext} and {@code applyLeftHandTransform}, and
     * returns the model to be rendered.
     */
    default BakedModel applyTransform(ItemDisplayContext transformType, PoseStack poseStack, boolean applyLeftHandTransform)
    {
        self().getTransforms().getTransform(transformType).apply(applyLeftHandTransform, poseStack);
        return self();
    }

    default @NotNull ModelData getModelData(@NotNull BlockAndTintGetter level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull ModelData modelData)
    {
        return modelData;
    }

    default TextureAtlasSprite getParticleIcon(@NotNull ModelData data)
    {
        return self().getParticleIcon();
    }

    /**
     * Gets the set of {@link RenderType render types} to use when drawing this block in the level.
     * Supported types are those returned by {@link RenderType#chunkBufferLayers()}.
     * <p>
     * By default, defers query to {@link ItemBlockRenderTypes}.
     */
    default ChunkRenderTypeSet getRenderTypes(@NotNull BlockState state, @NotNull RandomSource rand, @NotNull ModelData data)
    {
        return ItemBlockRenderTypesExtension.getRenderLayers(state);
    }

    /**
     * Gets an ordered list of {@link RenderType render types} to use when drawing this item.
     * All render types using the {@link com.mojang.blaze3d.vertex.DefaultVertexFormat#NEW_ENTITY} format are supported.
     * <p>
     * This method will only be called on the models returned by {@link #getRenderPasses(ItemStack, boolean)}.
     * <p>
     * By default, defers query to {@link ItemBlockRenderTypes}.
     *
     * @see #getRenderPasses(ItemStack, boolean)
     */
    default List<RenderType> getRenderTypes(ItemStack itemStack, boolean fabulous)
    {
        return List.of(RenderTypeHelper.getFallbackItemRenderType(itemStack, self(), fabulous));
    }

    /**
     * Gets an ordered list of baked models used to render this model as an item.
     * Each of those models' render types will be queried via {@link #getRenderTypes(ItemStack, boolean)}.
     * <p>
     * By default, returns the model itself.
     *
     * @see #getRenderTypes(ItemStack, boolean)
     */
    default List<BakedModel> getRenderPasses(ItemStack itemStack, boolean fabulous)
    {
        return List.of(self());
    }
}
