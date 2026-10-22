package com.mcjty.fancytrinkets.setup;

import com.mcjty.fancytrinkets.keys.KeyBindings;
import com.mcjty.fancytrinkets.modules.xpcrafter.XpCrafterModule;
import com.mcjty.fancytrinkets.modules.xpcrafter.recipe.XpRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.RecipesReceivedEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

import java.util.List;

public class ClientEventHandlers {

    private static List<XpRecipe> xpRecipes = List.of();

    public static List<XpRecipe> getXpRecipes() {
        return xpRecipes;
    }

    public static void onRecipesReceived(RecipesReceivedEvent event) {
        xpRecipes = event.getRecipeMap().byType(XpCrafterModule.XP_RECIPE_TYPE.get())
                .stream().map(RecipeHolder::value).toList();
    }

    public static void onLoggingOut(ClientPlayerNetworkEvent.LoggingOut event) {
        xpRecipes = List.of();
    }

    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        KeyBindings.init();
        event.register(KeyBindings.toggle1);
        event.register(KeyBindings.toggle2);
        event.register(KeyBindings.toggle3);
        event.register(KeyBindings.toggle4);
        event.register(KeyBindings.toggle5);
        event.register(KeyBindings.toggle6);
        event.register(KeyBindings.toggle7);
        event.register(KeyBindings.toggle8);
    }

}
