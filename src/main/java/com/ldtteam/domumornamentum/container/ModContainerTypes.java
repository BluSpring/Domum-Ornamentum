package com.ldtteam.domumornamentum.container;

import com.ldtteam.domumornamentum.util.Constants;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.MenuType;

public class ModContainerTypes
{
    public static final LazyRegistrar<MenuType<?>> CONTAINERS = LazyRegistrar.create(BuiltInRegistries.MENU, Constants.MOD_ID);

    public static RegistryObject<MenuType<ArchitectsCutterContainer>> ARCHITECTS_CUTTER = CONTAINERS.register("architects_cutter", () -> new MenuType<>(ArchitectsCutterContainer::new, FeatureFlagSet.of()));

    private ModContainerTypes()
    {
        throw new IllegalStateException("Can not instantiate an instance of: ModContainerTypes. This is a utility class");
    }
}
