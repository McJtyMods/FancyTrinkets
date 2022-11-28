package com.mcjty.fancytrinkets.modules.xpcrafter.recipe;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;

import javax.annotation.Nonnull;

public class XpRecipeSerializer implements RecipeSerializer<XpRecipe> {

    private final ShapedRecipe.Serializer serializer = new ShapedRecipe.Serializer();

    @Override
    @Nonnull
    public XpRecipe fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
        ShapedRecipe recipe = serializer.fromJson(recipeId, json);
        return new XpRecipe(recipe);
    }

    @Override
    public XpRecipe fromNetwork(@Nonnull ResourceLocation recipeId, @Nonnull FriendlyByteBuf buffer) {
        ShapedRecipe recipe = serializer.fromNetwork(recipeId, buffer);
        return new XpRecipe(recipe);
    }

    @Override
    public void toNetwork(@Nonnull FriendlyByteBuf buffer, XpRecipe recipe) {
        serializer.toNetwork(buffer, recipe);
    }
}
