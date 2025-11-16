package com.mcjty.fancytrinkets.modules.xpcrafter.recipe;

import mcjty.lib.crafting.IRecipeBuilder;
import mcjty.lib.varia.Tools;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.ICondition;
import org.jetbrains.annotations.Nullable;

public class XpRecipeBuilder implements IRecipeBuilder<XpRecipeBuilder> {

    private final ItemStack result;
    private ShapedRecipeBuilder builder;
    private final Advancement.Builder advancementBuilder = Advancement.Builder.advancement();

    public XpRecipeBuilder(ItemStack result) {
        this.result = result;
        builder = new ShapedRecipeBuilder(RecipeCategory.MISC, this.result);
    }

    public static XpRecipeBuilder shapedRecipe(ItemStack result) {
        return new XpRecipeBuilder(result);
    }

    @Override
    public XpRecipeBuilder define(Character symbol, TagKey<Item> tagKey) {
        return this.define(symbol, Ingredient.of(tagKey));
    }

    @Override
    public XpRecipeBuilder define(Character symbol, ItemLike itemLike) {
        return this.define(symbol, Ingredient.of(itemLike));
    }

    @Override
    public XpRecipeBuilder define(Character symbol, Ingredient ingredient) {
        builder = builder.define(symbol, ingredient);
        return this;
    }

    @Override
    public XpRecipeBuilder patternLine(String patternIn) {
        builder = builder.pattern(patternIn);
        return this;
    }

    public XpRecipeBuilder unlockedBy(String name, Criterion<? extends CriterionTriggerInstance> criterionIn) {
        builder = builder.unlockedBy(name, criterionIn);
        return this;
    }


    @Override
    public XpRecipeBuilder setGroup(String groupIn) {
        return this;
    }

    @Override
    public void build(RecipeOutput consumer) {
        this.build(consumer, Tools.getId(this.result.getItem()));
    }

    @Override
    public void build(RecipeOutput consumer, String save) {
        this.build(consumer, ResourceLocation.parse(save));
    }

    @Override
    public void build(RecipeOutput consumer, ResourceLocation id) {
        builder.save(new RecipeOutput() {
            @Override
            public Advancement.Builder advancement() {
                return advancementBuilder;
            }

            @Override
            public void accept(ResourceLocation resourceLocation, Recipe<?> recipe, @Nullable AdvancementHolder advancementHolder, ICondition... iConditions) {
                consumer.accept(id, new XpRecipe(recipe.getGroup(), id, ((ShapedRecipe) recipe).pattern, recipe.getResultItem(null)),
                        advancementHolder, iConditions);
            }
        });

    }
}