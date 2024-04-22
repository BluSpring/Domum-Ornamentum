package com.ldtteam.domumornamentum.client.render;

import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.mixin.ItemRendererAccessor;
import com.ldtteam.domumornamentum.util.ItemStackUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryStack;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ModelGhostRenderer {

    private static final ModelGhostRenderer INSTANCE = new ModelGhostRenderer();

    private static final BufferBuilderTransparent BUFFER = new BufferBuilderTransparent();

    public static ModelGhostRenderer getInstance() {
        return INSTANCE;
    }

    private ModelGhostRenderer() {
    }

    public void renderGhost(
            final WorldRenderContext renderContext,
            final ItemStack renderStack,
            final Vec3 targetedRenderPos,
            final BlockHitResult blockHitResult,
            final boolean ignoreDepth) {
        var poseStack = renderContext.matrixStack();
        poseStack.pushPose();

        // Offset/scale by an unnoticeable amount to prevent z-fighting
        final Vec3 camera = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        poseStack.translate(
                targetedRenderPos.x - camera.x - 0.000125,
                targetedRenderPos.y - camera.y + 0.000125,
                targetedRenderPos.z - camera.z - 0.000125
        );
        poseStack.scale(1.001F, 1.001F, 1.001F);

        final Vector4f color = new Vector4f(0, 0, 1, 0.5f);

        BUFFER.setAlphaPercentage(color.w());

        final List<ModelToRender> models;
        MaterialTextureData modelData = null;
        final BlockState placementState;
        final boolean renderItemMode;
        if (renderStack.getItem() instanceof BlockItem blockItem) {
            final BlockPlaceContext context = new BlockPlaceContext(
                    Objects.requireNonNull(Minecraft.getInstance().player),
                    Objects.requireNonNull(ItemStackUtils.getHandWithMateriallyTexturedItemStackFromPlayer(Minecraft.getInstance().player)),
                    renderStack,
                    blockHitResult
            );
            placementState = blockItem.getBlock().getStateForPlacement(context);
            if (placementState == null) {
                poseStack.popPose();
                return;
            }
            final BakedModel model = Minecraft.getInstance().getBlockRenderer().getBlockModelShaper().getBlockModel(placementState);

            if (blockItem.getBlock() instanceof EntityBlock entityBlock) {
                final BlockEntity blockEntity = entityBlock.newBlockEntity(context.getClickedPos(), placementState);
                if (blockEntity != null) {
                    CompoundTag beingPlacedTag = renderStack.getTag();
                    if (beingPlacedTag == null)
                        beingPlacedTag = new CompoundTag();

                    final CompoundTag currentTag = blockEntity.saveWithoutMetadata();
                    final CompoundTag currentTagCopy = currentTag.copy();
                    currentTag.merge(beingPlacedTag);
                    if (!currentTag.equals(currentTagCopy)) {
                        blockEntity.load(currentTag);
                        blockEntity.setChanged();
                    }

                    modelData = (MaterialTextureData) blockEntity.getRenderData();
                    //modelData = ((BakedModelExtension) model).getModelData(Minecraft.getInstance().level, context.getClickedPos(), placementState, modelData);
                }
            }

            /*if (modelData == null) {
                modelData = ModelData.EMPTY;
            }*/

            final RandomSource randomSource = RandomSource.create();
            randomSource.setSeed(42L);
            /*final ChunkRenderTypeSet renderTypeSet = ((BakedModelExtension) model).getRenderTypes(placementState, randomSource, modelData);
            models = renderTypeSet.asList().stream()
                    .map(renderType -> new ModelToRender(model, renderType))
                    .toList();*/
            models = List.of(new ModelToRender(model, RenderType.solid()));
            renderItemMode = false;
        } else {
            placementState = null;
            final BakedModel model = Minecraft.getInstance().getItemRenderer().getModel(renderStack, null, null, 0);
            /*final List<BakedModel> renderPasses = ((BakedModelExtension) model).getRenderPasses(renderStack, true);
            //TODO; Figure this out.
            modelData = ModelData.EMPTY;
            models = renderPasses.stream()
                    .flatMap(pass -> ((BakedModelExtension) pass).getRenderTypes(renderStack, true).stream().map(type -> new ModelToRender(pass, type)))
                    .toList();*/
            models = List.of(new ModelToRender(model, RenderType.solid()));
            renderItemMode = true;
        }

        renderGhost(
                placementState,
                blockHitResult.getBlockPos(),
                renderContext,
                models,
                modelData,
                color,
                false,
                renderItemMode
        );

        poseStack.popPose();
    }

    private void renderGhost(
            final BlockState state,
            final BlockPos pos,
            final WorldRenderContext renderContext,
            final List<ModelToRender> models,
            final MaterialTextureData modelData,
            final Vector4f color,
            final boolean renderColoredGhost,
            final boolean renderItemMode) {
        final RenderType renderType;
        if (renderColoredGhost)
        {
            renderType = ModRenderTypes.GHOST_BLOCK_COLORED_PREVIEW.get();
        }
        else
        {
            renderType = ModRenderTypes.GHOST_BLOCK_PREVIEW.get();
        }
        BUFFER.begin(renderType.mode(), renderType.format());
        if (renderColoredGhost)
        {
            for (ModelToRender model : models) {
                renderColoredModelLists(
                        state,
                        model,
                        modelData,
                        renderContext,
                        color
                );
            }
        }
        else
        {
            for (ModelToRender model : models) {
                renderFullModelLists(
                        model,
                        modelData,
                        state,
                        pos,
                        renderContext,
                        renderItemMode
                );
            }
        }
        renderType.end(BUFFER, RenderSystem.getVertexSorting());
    }

    private static final float[] DIRECTIONAL_BRIGHTNESS = { 0.5f, 1f, 0.7f, 0.7f, 0.6f, 0.6f };

    private static Vector3f[] getShadedColors(final Vector4f color) {
        // Directionally shade the color by the amount MC normally does
        return Arrays.stream(Direction.values())
                .map(direction ->
                {
                    final float brightness = DIRECTIONAL_BRIGHTNESS[direction.get3DDataValue()];
                    return new Vector3f(
                            color.x() * brightness,
                            color.y() * brightness,
                            color.z() * brightness);
                }).toArray(Vector3f[]::new);
    }

    private static Vector3f[] getNormals(final PoseStack.Pose pose) {
        // Transform the normal vector of each direction by the pose's normal matrix
        return Arrays.stream(Direction.values())
                .map(direction ->
                {
                    final Vec3i faceNormal = direction.getNormal();
                    final Vector3f normal = new Vector3f(faceNormal.getX(), faceNormal.getY(), faceNormal.getZ());
                    normal.mul(pose.normal());
                    return normal;
                }).toArray(Vector3f[]::new);
    }

    private static void renderFullModelLists(
            ModelToRender pModel,
            MaterialTextureData modelData,
            BlockState state,
            BlockPos pos,
            WorldRenderContext renderContext,
            boolean renderItemMode) {
        RandomSource randomsource = RandomSource.create();

        for(Direction direction : Direction.values()) {
            randomsource.setSeed(42L);
            if (renderItemMode) {
                //((ItemRendererAccessor) Minecraft.getInstance().getItemRenderer()).callRenderQuadList(pPoseStack, ModelGhostRenderer.BUFFER, ((BakedModelExtension) pModel.model()).getQuads(state, direction, randomsource, modelData, pModel.renderType()), new ItemStack(state.getBlock()), 15728880, OverlayTexture.NO_OVERLAY);
                ((ItemRendererAccessor) Minecraft.getInstance().getItemRenderer()).callRenderQuadList(renderContext.matrixStack(), ModelGhostRenderer.BUFFER, pModel.model().getQuads(state, direction, randomsource), new ItemStack(state.getBlock()), 15728880, OverlayTexture.NO_OVERLAY);
            } else {
                renderBlockTintedQuadList(renderContext, pModel.model().getQuads(state, direction, randomsource), state, pos);
            }
        }

        randomsource.setSeed(42L);
        if (renderItemMode) {
            ((ItemRendererAccessor) Minecraft.getInstance().getItemRenderer()).callRenderQuadList(renderContext.matrixStack(), ModelGhostRenderer.BUFFER, pModel.model().getQuads(state, null, randomsource), new ItemStack(state.getBlock()), 15728880, OverlayTexture.NO_OVERLAY);
        } else {
            renderBlockTintedQuadList(renderContext, pModel.model().getQuads(state, null, randomsource), state, pos);
        }
    }

    private static void renderBlockTintedQuadList(WorldRenderContext renderContext, List<BakedQuad> pQuads, BlockState placementState, BlockPos pos) {
        var pPoseStack = renderContext.matrixStack();
        PoseStack.Pose posestack$pose = pPoseStack.last();

        for(BakedQuad bakedquad : pQuads) {
            int i = -1;
            if (bakedquad.isTinted()) {
                i = Minecraft.getInstance().getBlockColors().getColor(placementState, Minecraft.getInstance().level, pos, bakedquad.getTintIndex());
            }

            float f = (float)(i >> 16 & 0xFF) / 255.0F;
            float f1 = (float)(i >> 8 & 0xFF) / 255.0F;
            float f2 = (float)(i & 0xFF) / 255.0F;
            int packedLight = 15728880;
            ModelGhostRenderer.BUFFER.putBulkData(posestack$pose, bakedquad, new float[] { 1.0F, 1.0F, 1.0F, 1.0F }, f, f1, f2, new int[] { packedLight, packedLight, packedLight, packedLight }, OverlayTexture.NO_OVERLAY, true);
        }
    }

    /**
     * Optimized version of ItemRenderer#renderModelLists that ignores textures, and renders a model's quads with a single RGBA color shaded by the quads' direction to match MCs similar shading
     */
    private static void renderColoredModelLists(
            final BlockState state,
            final ModelToRender model,
            final MaterialTextureData modelData,
            final WorldRenderContext renderContext,
            final Vector4f color) {
        var poseStack = renderContext.matrixStack();
        final RandomSource random = RandomSource.create(42);

        // Setup normals and shaded colors for each direction
        final Vector3f[] normals = getNormals(poseStack.last());
        final Vector3f[] shadedColors = getShadedColors(color);

        // Initialize reusable position vector to avoid needless creation of new ones
        final Vector4f pos = new Vector4f();

        for (final Direction direction : Direction.values()) {
            // Render outer directional quads
            random.setSeed(42L);
            renderColoredQuadList(poseStack.last().pose(), model.model().getQuads(state, direction, random), normals, shadedColors, pos);
        }

        // Render quads of unspecified direction
        random.setSeed(42L);
        renderColoredQuadList(poseStack.last().pose(), model.model().getQuads(state, null, random), normals, shadedColors, pos);
    }

    /**
     * Optimized version of ItemRenderer#renderQuadList
     */
    private static void renderColoredQuadList(
            final Matrix4f pose,
            final List<BakedQuad> quads,
            final Vector3f[] normals,
            final Vector3f[] shadedColors,
            final Vector4f pos) {
        for (final BakedQuad quad : quads) {
            putColoredBulkData(
                    pose,
                    quad,
                    shadedColors[quad.getDirection().ordinal()],
                    normals[quad.getDirection().ordinal()],
                    pos);
        }
    }

    private static void putColoredBulkData(
            final Matrix4f pose,
            final BakedQuad bakedQuad,
            final Vector3f color,
            final Vector3f normal,
            final Vector4f pos) {
        // Get vertex data
        final int[] vertices = bakedQuad.getVertices();
        final int vertexCount = vertices.length / DefaultVertexFormat.BLOCK.getIntegerSize();

        try (final MemoryStack memorystack = MemoryStack.stackPush()) {
            // Setup buffers
            final ByteBuffer bytebuffer = memorystack.malloc(DefaultVertexFormat.BLOCK.getVertexSize());
            final IntBuffer intbuffer = bytebuffer.asIntBuffer();

            for (int v = 0; v < vertexCount; ++v) {
                // Add vertex data to the buffer
                ((Buffer) intbuffer).clear();
                intbuffer.put(vertices, v * 8, 8);

                // Extract relative position, then transform it to the position in the world
                pos.set(bytebuffer.getFloat(0),
                        bytebuffer.getFloat(4),
                        bytebuffer.getFloat(8),
                        1f);
                pos.mul(pose);

                ((VertexConsumer) ModelGhostRenderer.BUFFER).vertex(pos.x(), pos.y(), pos.z())
                        .color(color.x(), color.y(), color.z(), 1f)
                        .normal(normal.x(), normal.y(), normal.z())
                        .endVertex();
            }
        }
    }

    private static class BufferBuilderTransparent extends BufferBuilder {

        private float alphaPercentage;

        public BufferBuilderTransparent() {
            super(2097152);
        }

        public void setAlphaPercentage(final float alphaPercentage) {
            this.alphaPercentage = Mth.clamp(alphaPercentage, 0, 1);
        }

        @Override
        public @NotNull VertexConsumer color(int red, int green, int blue, int alpha) {
            return super.color(red, green, blue, (int) (alpha * alphaPercentage));
        }

        @Override
        public void vertex(float x, float y, float z, float red, float green, float blue, float alpha, float texU,
                float texV, int overlayUV, int lightmapUV, float normalX, float normalY, float normalZ) {
            super.vertex(x, y, z, red, green, blue, alpha * alphaPercentage, texU, texV, overlayUV, lightmapUV, normalX, normalY, normalZ);
        }

    }

    private record ModelToRender(BakedModel model, RenderType renderType) {}
}
