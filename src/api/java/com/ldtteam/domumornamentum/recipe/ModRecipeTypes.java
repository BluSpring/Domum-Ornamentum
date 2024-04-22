package com.ldtteam.domumornamentum.recipe;

import com.ldtteam.domumornamentum.recipe.architectscutter.ArchitectsCutterRecipe;
import com.ldtteam.domumornamentum.util.Constants;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;

public class ModRecipeTypes
{
    public static final LazyRegistrar<RecipeType<?>> RECIPES = LazyRegistrar.create(BuiltInRegistries.RECIPE_TYPE, Constants.MOD_ID);

    public static RegistryObject<RecipeType<ArchitectsCutterRecipe>> ARCHITECTS_CUTTER  = RECIPES.register("architects_cutter", () -> new RecipeType<>() {
        private final String name = new ResourceLocation(Constants.MOD_ID, "architects_cutter").toString();

        @Override
        public String toString() {
            return name;
        }
    });

    private ModRecipeTypes()
    {
        throw new IllegalStateException("Can not instantiate an instance of: ModRecipeTypes. This is a utility class");
    }
}
