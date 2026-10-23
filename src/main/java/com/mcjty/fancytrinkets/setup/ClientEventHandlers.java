package com.mcjty.fancytrinkets.setup;
import com.mcjty.fancytrinkets.modules.xpcrafter.recipe.XpRecipe;
import java.util.List;
public final class ClientEventHandlers {
    private static List<XpRecipe> xpRecipes = List.of();
    public static List<XpRecipe> getXpRecipes() { return xpRecipes; }
    public static void setXpRecipes(List<XpRecipe> recipes) {
        List<XpRecipe> previous = xpRecipes;
        xpRecipes = List.copyOf(recipes);
        if (net.fabricmc.loader.api.FabricLoader.getInstance().isModLoaded("jei")) {
            com.mcjty.fancytrinkets.compat.FancyJeiPlugin.updateRecipes(previous, xpRecipes);
        }
    }
}
