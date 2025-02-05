package com.ldtteam.domumornamentum.datagen.global;

import com.google.common.collect.ImmutableList;
import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.BrickBlock;
import com.ldtteam.domumornamentum.block.decorative.ExtraBlock;
import com.ldtteam.domumornamentum.block.decorative.FloatingCarpetBlock;
import com.ldtteam.domumornamentum.fabric.BlockLootTableSubProviderExtension;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

/**
 * This class generates the default loot_table for blocks (if a block is destroyed, it drops its item).
 */
public class GlobalLootTableProvider extends LootTableProvider
{

    public GlobalLootTableProvider(PackOutput packOutput) {
        super(packOutput, Set.of(), List.of(new SubProviderEntry(GlobalLootTableEntries::new, LootContextParamSets.BLOCK)));
    }

    private static final class GlobalLootTableEntries extends BlockLootSubProvider implements BlockLootTableSubProviderExtension {

        private GlobalLootTableEntries() {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags());
        }

        @Override
        public void generate() {
            for (final BrickBlock block : ModBlocks.getInstance().getBricks())
            {
                dropSelf(block);
            }

            for (final ExtraBlock block : ModBlocks.getInstance().getExtraTopBlocks())
            {
                dropSelf(block);
            }

            for (final FloatingCarpetBlock block : ModBlocks.getInstance().getFloatingCarpets())
            {
                dropSelf(block);
            }

            dropSelf(ModBlocks.getInstance().getStandingBarrel());
            dropSelf(ModBlocks.getInstance().getLayingBarrel());
            dropSelf(ModBlocks.getInstance().getArchitectsCutter());
        }

        @Override
        public @NotNull Iterable<Block> getKnownBlocks() {
            return ImmutableList.<Block>builder()
                    .addAll(ModBlocks.getInstance().getBricks())
                    .addAll(ModBlocks.getInstance().getExtraTopBlocks())
                    .addAll(ModBlocks.getInstance().getFloatingCarpets())
                    .add(ModBlocks.getInstance().getStandingBarrel())
                    .add(ModBlocks.getInstance().getLayingBarrel())
                    .add(ModBlocks.getInstance().getArchitectsCutter()).build();
        }
    }

    @Override
    @NotNull
    public String getName()
    {
        return "Default Block Loot Tables Provider";
    }
}
