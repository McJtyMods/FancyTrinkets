package com.mcjty.fancytrinkets.modules.xpcrafter.recipe;

import com.mcjty.fancytrinkets.modules.xpcrafter.XpCrafterModule;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Optional;

/** The crafter's recipes use their own type, separate from vanilla crafting recipes. */
public class XpRecipe implements Recipe<CraftingInput> {
    public static final int RECIPE_DIMENSION = 5;

    private final String group;
    private final Identifier id;
    public final XpPattern pattern;
    private final ItemStackTemplate result;
    private final XpPattern matchingPattern;

    public XpRecipe(String group, Identifier id, XpPattern pattern, ItemStackTemplate result) {
        this.group = group;
        this.id = id;
        this.pattern = pattern;
        this.matchingPattern = pattern.trimmed();
        this.result = result;
    }

    // CraftingInput trims empty edges; retain the original grid for recipe displays and transfers.
    @Override
    public boolean matches(CraftingInput input, Level level) {
        if (input.width() != matchingPattern.width() || input.height() != matchingPattern.height()) return false;
        for (int i = 0; i < input.size(); i++) {
            if (!Ingredient.testOptionalIngredient(matchingPattern.ingredients().get(i), input.getItem(i))) return false;
        }
        return true;
    }

    public Identifier getId() { return id; }
    public String getGroup() { return group; }
    @Override public String group() { return group; }
    public int getWidth() { return pattern.width(); }
    public int getHeight() { return pattern.height(); }
    public List<Optional<Ingredient>> getIngredients() { return pattern.ingredients(); }
    public ItemStackTemplate getResultTemplate() { return result; }
    public ItemStack getResultItem() { return result.create(); }
    @Override public ItemStack assemble(CraftingInput input) { return result.create(); }
    @Override public boolean isSpecial() { return true; }
    @Override public boolean showNotification() { return true; }
    @Override public PlacementInfo placementInfo() { return PlacementInfo.createFromOptionals(pattern.ingredients()); }
    @Override public RecipeBookCategory recipeBookCategory() { return RecipeBookCategories.CRAFTING_MISC; }
    @Override public RecipeSerializer<XpRecipe> getSerializer() { return XpCrafterModule.XP_RECIPE_SERIALIZER.get(); }
    @Override public RecipeType<XpRecipe> getType() { return XpCrafterModule.XP_RECIPE_TYPE.get(); }
}
