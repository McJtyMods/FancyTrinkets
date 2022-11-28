package com.mcjty.fancytrinkets.modules.xpcrafter.recipe;

import com.mcjty.fancytrinkets.modules.xpcrafter.XpCrafterModule;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.IShapedRecipe;

public class XpRecipe implements IShapedRecipe<Container> {

    private final ResourceLocation id;

    public XpRecipe(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public int getRecipeWidth() {
        return 5;
    }

    @Override
    public int getRecipeHeight() {
        return 5;
    }

    @Override
    public boolean matches(Container pContainer, Level pLevel) {
        return false;
    }

    @Override
    public ItemStack assemble(Container pContainer) {
        return null;
    }

    @Override
    public boolean canCraftInDimensions(int w, int h) {
        return w >= 5 && h >= 5;
    }

    @Override
    public ItemStack getResultItem() {
        return null;
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
