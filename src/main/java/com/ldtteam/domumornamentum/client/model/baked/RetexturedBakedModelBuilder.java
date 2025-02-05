package com.ldtteam.domumornamentum.client.model.baked;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ldtteam.domumornamentum.client.model.data.MaterialTextureData;
import com.ldtteam.domumornamentum.client.model.utils.ModelSpriteQuadTransformer;
import com.ldtteam.domumornamentum.client.model.utils.ModelSpriteQuadTransformerData;
import com.ldtteam.domumornamentum.fabric.rendering.IQuadTransformer;
import com.ldtteam.domumornamentum.mixin.MultiPartBakedModelAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.MultiPartBakedModel;
import net.minecraft.client.resources.model.SimpleBakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Predicate;

public class RetexturedBakedModelBuilder {

    private static final RandomSource RANDOM = RandomSource.create();

    public static RetexturedBakedModelBuilder createFor(BlockState sourceState, RenderType renderType, boolean itemStackMode, final BakedModel target) {
        return new RetexturedBakedModelBuilder(target, sourceState, renderType, itemStackMode, target);
    }

    public static RetexturedBakedModelBuilder createFor(BlockState sourceState, RenderType renderType, boolean itemStackMode, final BakedModel sourceModel, final BakedModel target) {
        return new RetexturedBakedModelBuilder(sourceModel, sourceState, renderType, itemStackMode, target);
    }

    private final BakedModel sourceModel;
    private final BakedModel target;
    private final RenderType renderType;
    private final BlockState sourceState;
    private final boolean itemStackMode;
    private final Map<ResourceLocation, ReplacementModelData> retexturingMaps = Maps.newHashMap();

    private RetexturedBakedModelBuilder(final BakedModel sourceModel, BlockState sourceState, RenderType renderType, boolean itemStackMode, final BakedModel target) {
        this.sourceModel = sourceModel;
        this.sourceState = sourceState;
        this.renderType = renderType;
        this.itemStackMode = itemStackMode;
        this.target = target;
    }

    public RetexturedBakedModelBuilder with(
            final ResourceLocation source,
            final BakedModel target,
            final BlockState state
            ) {
        this.retexturingMaps.putIfAbsent(source, new ReplacementModelData(target, state));
        return this;
    }

    public RetexturedBakedModelBuilder with(
            final ResourceLocation source,
            final Block target
    ) {
        final BlockState defaultState = target.defaultBlockState();
        final BakedModel bakedModel = Minecraft.getInstance().getBlockRenderer().getBlockModel(defaultState);

        if (!itemStackMode) {
            /*if (((BakedModelExtension) bakedModel).getRenderTypes(target.defaultBlockState(), RANDOM, ModelData.EMPTY).contains(this.renderType)) {
                return this.with(source, bakedModel, defaultState);
            } else {
                return this.withOut(source);
            }*/
            return this.with(source, bakedModel, defaultState);
        } else {
            /*if (((BakedModelExtension) bakedModel).getRenderTypes(new ItemStack(target), Minecraft.useShaderTransparency()).contains(this.renderType)) {
                return this.with(source, bakedModel, defaultState);
            } else {
                return this.withOut(source);
            }*/
            return this.with(source, bakedModel, defaultState);
        }

    }

    public RetexturedBakedModelBuilder withOut(
            final ResourceLocation source
    ) {
        this.retexturingMaps.putIfAbsent(source, null);
        return this;
    }

    private static List<BakedQuad> getQuads(BakedModel target, BlockState state, Direction direction, RandomSource random, MaterialTextureData modelData) {
        var quads = new LinkedList<BakedQuad>();

        if (target instanceof MultiPartBakedModel) {
            var accessor =  ((MultiPartBakedModelAccessor) target);
            BitSet bitSet = accessor.getSelectorCache().get(state);
            if (bitSet == null) {
                bitSet = new BitSet();

                for(int i = 0; i < accessor.getSelectors().size(); ++i) {
                    Pair<Predicate<BlockState>, BakedModel> pair = accessor.getSelectors().get(i);
                    if ((pair.getLeft()).test(state)) {
                        bitSet.set(i);
                    }
                }

                accessor.getSelectorCache().put(state, bitSet);
            }

            List<BakedModel> list = Lists.newArrayList();

            for(int j = 0; j < bitSet.length(); ++j) {
                if (bitSet.get(j)) {
                    list.add((accessor.getSelectors().get(j)).getRight());
                }
            }

            for (BakedModel bakedModel : list) {
                if (bakedModel instanceof MateriallyTexturedBakedModel texturedBakedModel)
                    quads.addAll(texturedBakedModel.getQuads(state, direction, random, modelData, null));
                else
                    quads.addAll(bakedModel.getQuads(state, direction, random));
            }
        } else if (target instanceof MateriallyTexturedBakedModel texturedBakedModel) {
            quads.addAll(texturedBakedModel.getQuads(state, direction, random, modelData, null));
        } else {
            quads.addAll(target.getQuads(state, direction, random));
        }

        return quads;
    }

    public BakedModel build() {
        final SimpleBakedModel.Builder builder = new SimpleBakedModel.Builder(
                //((BakedModelExtension) sourceModel).useAmbientOcclusion(this.sourceState, this.renderType),
                sourceModel.useAmbientOcclusion(),
                sourceModel.usesBlockLight(),
                sourceModel.isGui3d(),
                sourceModel.getTransforms(),
                sourceModel.getOverrides()
        );

        getQuads(this.target, null, null, RANDOM, MaterialTextureData.EMPTY).forEach(quad -> {
            if (needsRetexturing(this.retexturingMaps, quad.getSprite())) {
                retexture(quad, null).ifPresent(builder::addUnculledFace);
            } else if (!needsErasure(this.retexturingMaps, quad.getSprite())) {
                builder.addUnculledFace(quad);
            }
        });

        for (final Direction value : Direction.values()) {
            getQuads(this.target, null, value, RANDOM, MaterialTextureData.EMPTY).forEach(quad -> {
                if (needsRetexturing(this.retexturingMaps, quad.getSprite())) {
                    retexture(quad, value).ifPresent(newQuad -> builder.addCulledFace(value, newQuad));
                } else if (!needsErasure(this.retexturingMaps, quad.getSprite())) {
                    builder.addCulledFace(value, quad);
                }
            });
        }

        TextureAtlasSprite particleTexture = this.target.getParticleIcon();
        if (needsRetexturing(this.retexturingMaps, particleTexture)) {
            final BakedModel particleOverrideTextureModel = this.retexturingMaps.get(particleTexture.contents().name()).model();
            particleTexture = particleOverrideTextureModel.getParticleIcon();
        }
        builder.particle(particleTexture);

        return builder.build();
    }

    private boolean needsRetexturing(Map<ResourceLocation, ?> retexturingMaps, TextureAtlasSprite quad) {
        return retexturingMaps.containsKey(quad.contents().name()) && retexturingMaps.get(quad.contents().name()) != null;
    }

    private boolean needsErasure(Map<ResourceLocation, ?> retexturingMaps, TextureAtlasSprite quad) {
        return retexturingMaps.containsKey(quad.contents().name()) && retexturingMaps.get(quad.contents().name()) == null;
    }

    private Optional<BakedQuad> retexture(@NotNull BakedQuad quad, @Nullable Direction direction) {
        if (!needsRetexturing(this.retexturingMaps, quad.getSprite()))
            return Optional.empty();

        if (needsErasure(this.retexturingMaps, quad.getSprite()))
            return Optional.empty();

        final Optional<ModelSpriteQuadTransformerData> retexturingQuad = this.getRetexturingQuad(quad, direction);

        return retexturingQuad.map(sprite -> {
            final IQuadTransformer quadTransformer = ModelSpriteQuadTransformer.create(sprite);
            return quadTransformer.process(quad);
        });
    }

    private Optional<ModelSpriteQuadTransformerData> getRetexturingQuad(@NotNull BakedQuad source, @Nullable Direction direction) {
        if (!needsRetexturing(retexturingMaps, source.getSprite())) {
            return Optional.empty();
        }

        if (needsErasure(retexturingMaps, source.getSprite())) {
            return Optional.empty();
        }

        final ReplacementModelData modelData = this.retexturingMaps.get(source.getSprite().contents().name());
        List<BakedQuad> targetQuads = modelData.model().getQuads(
                null,
                direction,
                RANDOM
        );

        //If we did not find anything, that might be because the target model specifies culling while our source did not.
        //Lets try with the targeting direction (the normal) of the quad itself.
        if (targetQuads.isEmpty())
            targetQuads = getQuads(
                modelData.model(),
                null,
                source.getDirection(),
                RANDOM,
                MaterialTextureData.EMPTY
            );

        if (targetQuads.isEmpty())
            return Optional.empty();

        final ModelSpriteQuadTransformerData data = new ModelSpriteQuadTransformerData(
                targetQuads.get(0),
                modelData.state()
            );

        return Optional.of(data);
    }

    record ReplacementModelData(BakedModel model, BlockState state) {}

}
