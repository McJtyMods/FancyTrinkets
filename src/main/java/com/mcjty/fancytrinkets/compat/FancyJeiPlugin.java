package com.mcjty.fancytrinkets.compat;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.modules.trinkets.TrinketsModule;
import com.mcjty.fancytrinkets.modules.xpcrafter.XpCrafterModule;
import com.mcjty.fancytrinkets.modules.xpcrafter.blocks.ExperienceCrafterBE;
import com.mcjty.fancytrinkets.modules.xpcrafter.recipe.XpRecipe;
import mcjty.lib.container.GenericContainer;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.types.IRecipeType;
import mezz.jei.api.registration.*;
import net.minecraft.resources.Identifier;

import mcjty.lib.setup.DeferredItem;

import javax.annotation.ParametersAreNonnullByDefault;

@JeiPlugin
@ParametersAreNonnullByDefault
public class FancyJeiPlugin implements IModPlugin {

    private static mezz.jei.api.runtime.IJeiRuntime runtime;

    private static final Identifier ID = Identifier.fromNamespaceAndPath(FancyTrinkets.MODID, "jeiplugin");

    public static final IRecipeType<XpRecipe> XP_RECIPE_TYPE = IRecipeType.create(FancyTrinkets.MODID, "xp_recipe", XpRecipe.class);

    @Override
    public Identifier getPluginUid() {
        return ID;
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        TrinketsModule.TRINKET_ITEMS.values()
            .stream()
            .map(TrinketsModule.TrinketInfo::item)
            .map(DeferredItem::get)
            .distinct()
            .forEach(item -> {
                registration.registerSubtypeInterpreter(item, TrinketItemSubtypeInterpreter.INSTANCE);
            });
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addCraftingStation(XP_RECIPE_TYPE, XpCrafterModule.EXPERIENCE_CRAFTER.block().get());
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IJeiHelpers jeiHelpers = registration.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registration.addRecipeCategories(new XpRecipeCategory(guiHelper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(XP_RECIPE_TYPE, com.mcjty.fancytrinkets.setup.ClientEventHandlers.getXpRecipes());
    }

    @Override
    public void onRuntimeAvailable(mezz.jei.api.runtime.IJeiRuntime jeiRuntime) {
        runtime = jeiRuntime;
    }

    @Override
    public void onRuntimeUnavailable() {
        runtime = null;
    }

    public static void updateRecipes(java.util.List<XpRecipe> previous, java.util.List<XpRecipe> recipes) {
        // Custom recipe payloads can arrive after JEI has completed its initial registration.
        if (runtime != null) {
            runtime.getRecipeManager().hideRecipes(XP_RECIPE_TYPE, previous);
            runtime.getRecipeManager().addRecipes(XP_RECIPE_TYPE, recipes);
        }
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(GenericContainer.class, XpCrafterModule.CONTAINER_EXPERIENCE_CRAFTER.get(), XP_RECIPE_TYPE,
                ExperienceCrafterBE.SLOT_GRID, XpRecipe.RECIPE_DIMENSION * XpRecipe.RECIPE_DIMENSION,
                ExperienceCrafterBE.SLOT_GRID + XpRecipe.RECIPE_DIMENSION * XpRecipe.RECIPE_DIMENSION, 36);
    }
}
