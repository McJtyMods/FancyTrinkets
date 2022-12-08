package com.mcjty.fancytrinkets.compat;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.modules.xpcrafter.XpCrafterModule;
import com.mcjty.fancytrinkets.modules.xpcrafter.recipe.XpRecipe;
import com.mojang.blaze3d.vertex.PoseStack;
import mcjty.lib.varia.ComponentFactory;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class XpRecipeCategory implements IRecipeCategory<XpRecipe> {

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable slot;

    public static final String KEY_XP_RECIPE_CATEGORY = "fancytrinkets.xp_recipe_category";

    public XpRecipeCategory(IGuiHelper guiHelper) {
        slot = guiHelper.getSlotDrawable();
        icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(XpCrafterModule.EXPERIENCE_CRAFTER.get()));
        background = guiHelper.createBlankDrawable(140, 120);
    }

    @Override
    public ResourceLocation getUid() {
        return new ResourceLocation(FancyTrinkets.MODID, "xprecipe");
    }

    @Override
    public Class<? extends XpRecipe> getRecipeClass() {
        return XpRecipe.class;
    }

    @Override
    public RecipeType<XpRecipe> getRecipeType() {
        return FancyJeiPlugin.XP_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return ComponentFactory.translatable(KEY_XP_RECIPE_CATEGORY);
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void draw(XpRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        for (int y = 0 ; y < XpRecipe.RECIPE_DIMENSION ; y++) {
            for (int x = 0 ; x < XpRecipe.RECIPE_DIMENSION ; x++) {
                slot.draw(stack, 4 + x*18, 4 + y*18);
            }
        }
        slot.draw(stack, 4+6*18, 4);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, XpRecipe recipe, IFocusGroup focuses) {
        for (int y = 0 ; y < XpRecipe.RECIPE_DIMENSION ; y++) {
            for (int x = 0 ; x < XpRecipe.RECIPE_DIMENSION ; x++) {
                builder.addSlot(RecipeIngredientRole.INPUT, 5 + x*18, 5 + y*18)
                        .addIngredients(recipe.getIngredients().get(y*XpRecipe.RECIPE_DIMENSION + x));

            }
        }
        builder.addSlot(RecipeIngredientRole.OUTPUT, 5 + 6*18,  5)
                .addItemStack(recipe.getResultItem());

    }
}
