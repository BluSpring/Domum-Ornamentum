package com.ldtteam.domumornamentum.fabric.model;

import com.google.common.base.Preconditions;
import com.ldtteam.domumornamentum.fabric.BlockEntityExtension;
import com.ldtteam.domumornamentum.fabric.LevelExtension;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientChunkEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ModelDataManager
{
    private final Level level;
    private final Map<ChunkPos, Set<BlockPos>> needModelDataRefresh = new ConcurrentHashMap<>();
    private final Map<ChunkPos, Map<BlockPos, ModelData>> modelDataCache = new ConcurrentHashMap<>();

    public ModelDataManager(Level level)
    {
        this.level = level;
    }

    public void requestRefresh(@NotNull BlockEntity blockEntity)
    {
        Preconditions.checkNotNull(blockEntity, "Block entity must not be null");
        needModelDataRefresh.computeIfAbsent(new ChunkPos(blockEntity.getBlockPos()), $ -> Collections.newSetFromMap(new ConcurrentHashMap<>()))
            .add(blockEntity.getBlockPos());
    }

    private void refreshAt(ChunkPos chunk)
    {
        Set<BlockPos> needUpdate = needModelDataRefresh.remove(chunk);

        if (needUpdate != null)
        {
            Map<BlockPos, ModelData> data = modelDataCache.computeIfAbsent(chunk, $ -> new ConcurrentHashMap<>());
            for (BlockPos pos : needUpdate)
            {
                BlockEntity toUpdate = level.getBlockEntity(pos);
                if (toUpdate != null && !toUpdate.isRemoved())
                {
                    data.put(pos, ((BlockEntityExtension) toUpdate).domum$getModelData());
                }
                else
                {
                    data.remove(pos);
                }
            }
        }
    }

    public @Nullable ModelData getAt(BlockPos pos)
    {
        return getAt(new ChunkPos(pos)).get(pos);
    }

    public Map<BlockPos, ModelData> getAt(ChunkPos pos)
    {
        Preconditions.checkArgument(level.isClientSide, "Cannot request model data for server level");
        refreshAt(pos);
        return modelDataCache.getOrDefault(pos, Collections.emptyMap());
    }

    public static void init() {}

    static {
        ClientChunkEvents.CHUNK_UNLOAD.register((level, chunk) -> {
            if (level == null)
                return;

            var modelDataManager = ((LevelExtension) level).domum$getModelDataManager();
            if (modelDataManager == null)
                return;

            ChunkPos chunkPos = chunk.getPos();
            modelDataManager.needModelDataRefresh.remove(chunkPos);
            modelDataManager.modelDataCache.remove(chunkPos);
        });
    }
}

