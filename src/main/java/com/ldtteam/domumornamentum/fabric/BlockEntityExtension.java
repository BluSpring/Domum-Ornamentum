package com.ldtteam.domumornamentum.fabric;

import com.ldtteam.domumornamentum.fabric.model.ModelData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface BlockEntityExtension {
    default BlockEntity self() {
        return (BlockEntity) this;
    }

    default void domum$requestModelDataUpdate() {
        BlockEntity te = self();
        Level level = te.getLevel();
        if (level != null && level.isClientSide)
        {
            var modelDataManager = ((LevelExtension) level).domum$getModelDataManager();
            if (modelDataManager != null)
            {
                modelDataManager.requestRefresh(te);
            }
        }
    }

    default ModelData domum$getModelData() {
        return ModelData.EMPTY;
    }
}
