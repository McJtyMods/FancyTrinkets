package com.mcjty.fancytrinkets.modules.xpcrafter.recipe;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.NonNullList;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Map;
import java.util.Set;

public class RecipeJsonTools {

    static Map<String, Ingredient> parseKeys(JsonObject keys) {
        Map<String, Ingredient> map = Maps.newHashMap();

        for(Map.Entry<String, JsonElement> entry : keys.entrySet()) {
            String key = entry.getKey();
            if (key.length() != 1 || " ".equals(key)) {
                throw new JsonSyntaxException("Invalid key entry: '" + key + "'!");
            }
            map.put(key, Ingredient.fromJson(entry.getValue()));
        }

        map.put(" ", Ingredient.EMPTY);
        return map;
    }

    static String[] patternFromJson(JsonArray array) {
        String[] result = new String[array.size()];
        if (result.length > XpRecipe.RECIPE_DIMENSION) {
            throw new JsonSyntaxException("Invalid pattern: too many rows, " + XpRecipe.RECIPE_DIMENSION + " is maximum");
        } else if (result.length == 0) {
            throw new JsonSyntaxException("Invalid empty pattern");
        } else {
            for(int i = 0; i < result.length; ++i) {
                String s = GsonHelper.convertToString(array.get(i), "pattern[" + i + "]");
                if (s.length() > XpRecipe.RECIPE_DIMENSION) {
                    throw new JsonSyntaxException("Invalid pattern: too many columns, " + XpRecipe.RECIPE_DIMENSION + " is maximum");
                }

                if (i > 0 && result[0].length() != s.length()) {
                    throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
                }

                result[i] = s;
            }

            return result;
        }
    }

    static NonNullList<Ingredient> patternToIngredients(String[] pattern, Map<String, Ingredient> keys) {
        NonNullList<Ingredient> nonnulllist = NonNullList.withSize(XpRecipe.RECIPE_DIMENSION * XpRecipe.RECIPE_DIMENSION, Ingredient.EMPTY);
        Set<String> set = Sets.newHashSet(keys.keySet());
        set.remove(" ");

        for(int i = 0; i < pattern.length; ++i) {
            for(int j = 0; j < pattern[i].length(); ++j) {
                String s = pattern[i].substring(j, j + 1);
                Ingredient ingredient = keys.get(s);
                if (ingredient == null) {
                    throw new JsonSyntaxException("Unknown symbol '" + s + "'!");
                }

                set.remove(s);
                nonnulllist.set(j + XpRecipe.RECIPE_DIMENSION * i, ingredient);
            }
        }

        if (!set.isEmpty()) {
            throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + set);
        } else {
            return nonnulllist;
        }
    }
}
