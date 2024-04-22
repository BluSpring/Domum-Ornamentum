package com.ldtteam.domumornamentum.entity.block;

import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlock;
import com.ldtteam.domumornamentum.util.Constants;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

/**
 * Class to create the modBlocks.
 * References to the blocks can be made here
 */
public final class ModBlockEntityTypes
{
    public static final LazyRegistrar<BlockEntityType<?>> BLOCK_ENTITIES = LazyRegistrar.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Constants.MOD_ID);

    @SuppressWarnings({"SuspiciousToArrayCall", "ConstantConditions"}) //Not really true.
    public static RegistryObject<BlockEntityType<BlockEntity>> MATERIALLY_TEXTURED = BLOCK_ENTITIES.register(Constants.BlockEntityTypes.MATERIALLY_RETEXTURABLE,
      () -> BlockEntityType.Builder.of((BlockEntityType.BlockEntitySupplier<BlockEntity>) MateriallyTexturedBlockEntity::new,
        BuiltInRegistries.BLOCK.stream().filter(IMateriallyTexturedBlock.class::isInstance).toArray(Block[]::new)
      ).build(null)
    );

    /**
     * Private constructor to hide the implicit public one.
     */
    private ModBlockEntityTypes()
    {
    }
}
