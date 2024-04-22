package com.ldtteam.domumornamentum.mixin;

import com.ldtteam.domumornamentum.fabric.LevelExtension;
import com.ldtteam.domumornamentum.fabric.model.ModelDataManager;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ClientLevel.class)
public class ClientLevelMixin implements LevelExtension {
    @Unique private final ModelDataManager modelDataManager = new ModelDataManager((ClientLevel) (Object) this);

    @Override
    public ModelDataManager domum$getModelDataManager() {
        return modelDataManager;
    }
}
