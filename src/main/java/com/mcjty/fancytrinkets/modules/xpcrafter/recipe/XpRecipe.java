package com.mcjty.fancytrinkets.modules.xpcrafter.recipe;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.modules.xpcrafter.XpCrafterModule;
import mcjty.lib.crafting.BaseShapedRecipe;
import mcjty.lib.varia.ItemStackTools;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.crafting.IShapedRecipe;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.InvWrapper;

public class XpRecipe extends BaseShapedRecipe {

    private final ResourceLocation id;
    private final NonNullList<Ingredient> ingredients;
    private final ItemStack result;

    public static final int RECIPE_DIMENSION = 5;

    public XpRecipe(ResourceLocation id, NonNullList<Ingredient> ingredients, ItemStack result) {
        super(id, FancyTrinkets.MODID, RECIPE_DIMENSION, RECIPE_DIMENSION, ingredients, result);
        this.id = id;
        this.ingredients = ingredients;
        this.result = result;
    }

    @Override
    public int getRecipeWidth() {
        return RECIPE_DIMENSION;
    }

    @Override
    public int getRecipeHeight() {
        return RECIPE_DIMENSION;
    }

    @Override
    public boolean matches(CraftingContainer container, Level level) {
        for (int i = 0 ; i < RECIPE_DIMENSION * RECIPE_DIMENSION ; i++) {
            if (!ingredients.get(i).test(container.getItem(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }


    @Override
    public ItemStack assemble(CraftingContainer pContainer, RegistryAccess access) {
        if (matches(pContainer, null)) {
            return result.copy();
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth == RECIPE_DIMENSION && pHeight == RECIPE_DIMENSION;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public ItemStack getResultItem() {
        return result;
    }

    @Override
    public ResourceLocation getId() {
        return id;
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
