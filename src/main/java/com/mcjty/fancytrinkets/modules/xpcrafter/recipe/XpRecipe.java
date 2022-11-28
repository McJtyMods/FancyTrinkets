package com.mcjty.fancytrinkets.modules.xpcrafter.recipe;

import com.mcjty.fancytrinkets.modules.xpcrafter.XpCrafterModule;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;

public class XpRecipe extends ShapedRecipe {

    public static final int RECIPE_DIMENSION = 5;

    public XpRecipe(ShapedRecipe recipe) {
        this(recipe.getId(), recipe.getGroup(), recipe.getWidth(), recipe.getHeight(), recipe.getIngredients(), recipe.getResultItem());
    }

    public XpRecipe(ResourceLocation id, String group, int w, int h, NonNullList<Ingredient> recipeItems, ItemStack result) {
        super(id, group, w, h, recipeItems, result);
    }



    @Override
    public RecipeSerializer<?> getSerializer() {
        return XpCrafterModule.XP_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return XpCrafterModule.XP_RECIPE_TYPE.get();
    }
}
