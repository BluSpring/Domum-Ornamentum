package com.ldtteam.domumornamentum.fabric;

import com.ldtteam.domumornamentum.fabric.model.ModelDataManager;

public interface LevelExtension {
    default ModelDataManager domum$getModelDataManager() {
        return null;
    }
}
