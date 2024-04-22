package com.ldtteam.domumornamentum.fabric;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.block.Block;

public class TagProviderHelper {
    public static TagsProvider.TagAppender<Block> add(TagsProvider.TagAppender<Block> tagAppender, Block... blocks) {
        for (Block block : blocks) {
            tagAppender.add(BuiltInRegistries.BLOCK.getResourceKey(block).orElseThrow());
        }

        return tagAppender;
    }
}
