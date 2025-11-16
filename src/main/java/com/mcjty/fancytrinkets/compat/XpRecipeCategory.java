package com.mcjty.fancytrinkets.compat;

import com.mcjty.fancytrinkets.modules.xpcrafter.XpCrafterModule;
import com.mcjty.fancytrinkets.modules.xpcrafter.recipe.XpRecipe;
import mcjty.lib.varia.ComponentFactory;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.AbstractRecipeCategory;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class XpRecipeCategory extends AbstractRecipeCategory<XpRecipe> {
    public static final String KEY_XP_RECIPE_CATEGORY = "fancytrinkets.xp_recipe_category";

    public XpRecipeCategory(IGuiHelper guiHelper) {
        super(
            FancyJeiPlugin.XP_RECIPE_TYPE,
            ComponentFactory.translatable(KEY_XP_RECIPE_CATEGORY),
            guiHelper.createDrawableItemLike(XpCrafterModule.EXPERIENCE_CRAFTER.block()),
            140,
            98
        );
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, XpRecipe recipe, IFocusGroup focuses) {
        for (int y = 0 ; y < XpRecipe.RECIPE_DIMENSION ; y++) {
            for (int x = 0 ; x < XpRecipe.RECIPE_DIMENSION ; x++) {
                builder.addSlot(RecipeIngredientRole.INPUT, 5 + x*18, 5 + y*18)
                        .addIngredients(recipe.getIngredients().get(y*XpRecipe.RECIPE_DIMENSION + x))
                        .setStandardSlotBackground();

            }
        }
        builder.addSlot(RecipeIngredientRole.OUTPUT, 3 + 6*18,  9)
                .addItemStack(recipe.getResultItem())
                .setOutputSlotBackground();

    }
}
