package com.ldtteam.domumornamentum.datagen.global;

import com.ldtteam.domumornamentum.block.IMateriallyTexturedBlock;
import com.ldtteam.domumornamentum.util.Constants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Consumer;

public class MateriallyTexturedBlockRecipeProvider extends RecipeProvider
{

    public MateriallyTexturedBlockRecipeProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    public void buildRecipes(@NotNull Consumer<FinishedRecipe> finishedRecipe) {
        BuiltInRegistries.BLOCK.forEach(
                block -> {
                    if (Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(block)).getNamespace().equals(Constants.MOD_ID) && block instanceof IMateriallyTexturedBlock materiallyTexturedBlock) {
                        materiallyTexturedBlock.getValidCutterRecipes().forEach(finishedRecipe);
                    }
                }
        );
    }

    @Override
    public @NotNull String getName()
    {
        return "Materially textured block recipes";
    }
}
