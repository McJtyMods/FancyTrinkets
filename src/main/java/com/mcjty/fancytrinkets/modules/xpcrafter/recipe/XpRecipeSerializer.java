package com.mcjty.fancytrinkets.modules.xpcrafter.recipe;

import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;

import javax.annotation.Nonnull;
import java.util.Map;

public class XpRecipeSerializer implements RecipeSerializer<XpRecipe> {

    @Override
    @Nonnull
    public XpRecipe fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
        Map<String, Ingredient> map = RecipeJsonTools.parseKeys(GsonHelper.getAsJsonObject(json, "key"));
        String[] pattern = RecipeJsonTools.patternFromJson(GsonHelper.getAsJsonArray(json, "pattern"));
        NonNullList<Ingredient> ingredients = RecipeJsonTools.patternToIngredients(pattern, map);
        ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
        return new XpRecipe(recipeId, ingredients, result);
    }

    @Override
    public XpRecipe fromNetwork(@Nonnull ResourceLocation recipeId, @Nonnull FriendlyByteBuf buffer) {
        NonNullList<Ingredient> ingredients = NonNullList.withSize(XpRecipe.RECIPE_DIMENSION * XpRecipe.RECIPE_DIMENSION, Ingredient.EMPTY);

        for(int i = 0; i < ingredients.size(); ++i) {
            ingredients.set(i, Ingredient.fromNetwork(buffer));
        }

        ItemStack result = buffer.readItem();
        return new XpRecipe(recipeId, ingredients, result);
    }

    @Override
    public void toNetwork(@Nonnull FriendlyByteBuf buffer, XpRecipe recipe) {
        for(Ingredient ingredient : recipe.getIngredients()) {
            ingredient.toNetwork(buffer);
        }
        buffer.writeItem(recipe.getResultItem());
    }
}
