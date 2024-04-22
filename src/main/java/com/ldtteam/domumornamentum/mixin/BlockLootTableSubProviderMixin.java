package com.ldtteam.domumornamentum.mixin;

import com.ldtteam.domumornamentum.fabric.BlockLootTableSubProviderExtension;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Iterator;

@Mixin(BlockLootSubProvider.class)
public class BlockLootTableSubProviderMixin implements BlockLootTableSubProviderExtension {
    @Redirect(method = "generate(Ljava/util/function/BiConsumer;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/DefaultedRegistry;iterator()Ljava/util/Iterator;"))
    private Iterator<Block> useKnownBlocks(DefaultedRegistry instance) {
        return this.getKnownBlocks().iterator();
    }
}
