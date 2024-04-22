package com.ldtteam.domumornamentum.datagen.extra;

import com.ldtteam.domumornamentum.block.ModBlocks;
import com.ldtteam.domumornamentum.block.decorative.ExtraBlock;
import com.ldtteam.domumornamentum.block.types.ExtraBlockType;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.DyeItem;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ExtraRecipeProvider extends RecipeProvider
{
    public ExtraRecipeProvider(PackOutput packOutput)
    {
        super(packOutput);
    }

    @Override
    public void buildRecipes(@NotNull Consumer<FinishedRecipe> writer) {
        ModBlocks.getInstance().getExtraTopBlocks().forEach(extraBlock -> extraBlockRecipe(writer, extraBlock));
    }

    private void extraBlockRecipe(Consumer<FinishedRecipe> writer, ExtraBlock extraBlock) {
        final ExtraBlockType type = extraBlock.getType();
        final ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, extraBlock, 4);
        builder.pattern("X X");
        builder.pattern(" Z ");
        builder.pattern("X X");
        builder.define('X', type.getMaterial());
        if (type.getColor() == null) {
            builder.define('Z', type.getMaterial());
        } else {
            builder.define('Z', DyeItem.byColor(type.getColor()));
        }
        builder.unlockedBy("has_material", has(type.getMaterial()));
        if (type.getColor() != null) {
            builder.unlockedBy("has_dye", has( DyeItem.byColor(type.getColor())));
        }
        builder.save(writer);
    }

    @NotNull
    @Override
    public String getName()
    {
        return "Extra Blocks Recipe Provider";
    }
}
