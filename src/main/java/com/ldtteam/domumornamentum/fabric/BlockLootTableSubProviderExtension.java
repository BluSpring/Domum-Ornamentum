package com.ldtteam.domumornamentum.fabric;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;

public interface BlockLootTableSubProviderExtension {
    default Iterable<Block> getKnownBlocks() {
        return BuiltInRegistries.BLOCK;
    }
}
