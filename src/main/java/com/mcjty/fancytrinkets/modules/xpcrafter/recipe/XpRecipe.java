package com.mcjty.fancytrinkets.modules.xpcrafter.recipe;

import com.mcjty.fancytrinkets.modules.xpcrafter.XpCrafterModule;
import mcjty.lib.crafting.BaseShapedRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class XpRecipe extends BaseShapedRecipe {

    private final ResourceLocation id;
    private final ItemStack result;

    public static final int RECIPE_DIMENSION = 5;

    public XpRecipe(String group, ResourceLocation id, ShapedRecipePattern pattern, ItemStack result) {
        super(group, CraftingBookCategory.MISC, pattern, result);
        this.id = id;
        this.result = result;
    }

    @Override
    public boolean matches(@Nonnull CraftingInput inv, @Nonnull Level level) {
        for (int i = 0 ; i < RECIPE_DIMENSION * RECIPE_DIMENSION ; i++) {
            if (!pattern.ingredients().get(i).test(inv.getItem(i))) {
                return false;
            }
        }
        return true;
    }

    public ResourceLocation getId() {
        return id;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return pattern.ingredients();
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
    public RecipeSerializer<?> getSerializer() {
        return XpCrafterModule.XP_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return XpCrafterModule.XP_RECIPE_TYPE.get();
    }
}
