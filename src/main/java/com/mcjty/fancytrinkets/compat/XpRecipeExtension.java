package com.mcjty.fancytrinkets.compat;

import com.mcjty.fancytrinkets.modules.xpcrafter.recipe.XpRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.ICraftingGridHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICraftingCategoryExtension;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;

public class XpRecipeExtension implements ICraftingCategoryExtension<XpRecipe> {

    @Override
    public void setRecipe(RecipeHolder<XpRecipe> recipeHolder, IRecipeLayoutBuilder builder, ICraftingGridHelper craftingGridHelper, IFocusGroup focuses) {
        final XpRecipe recipe = recipeHolder.value();

        craftingGridHelper.createAndSetIngredients(builder, recipe.getIngredients(), getWidth(recipeHolder), getHeight(recipeHolder));
        craftingGridHelper.createAndSetOutputs(builder, List.of(recipe.getResultItem()));
    }


    @Override
    public int getWidth(RecipeHolder<XpRecipe> recipeHolder) {
        return XpRecipe.RECIPE_DIMENSION;
    }

    @Override
    public int getHeight(RecipeHolder<XpRecipe> recipeHolder) {
        return XpRecipe.RECIPE_DIMENSION;
    }
}
