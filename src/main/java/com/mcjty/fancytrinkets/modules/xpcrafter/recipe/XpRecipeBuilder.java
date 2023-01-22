package com.mcjty.fancytrinkets.modules.xpcrafter.recipe;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mcjty.fancytrinkets.modules.xpcrafter.XpCrafterModule;
import com.mojang.serialization.JsonOps;
import mcjty.lib.crafting.IRecipeBuilder;
import mcjty.lib.varia.Tools;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class XpRecipeBuilder implements IRecipeBuilder<XpRecipeBuilder> {

    private final ItemStack result;
    private final List<String> pattern = Lists.newArrayList();
    private final Map<Character, Ingredient> key = Maps.newLinkedHashMap();

    public XpRecipeBuilder(ItemStack result) {
        this.result = result.copy();
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
        if (this.key.containsKey(symbol)) {
            throw new IllegalArgumentException("Symbol '" + symbol + "' is already defined!");
        } else if (symbol == ' ') {
            throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
        } else {
            this.key.put(symbol, ingredient);
            return this;
        }
    }

    @Override
    public XpRecipeBuilder patternLine(String patternIn) {
        if (!this.pattern.isEmpty() && patternIn.length() != this.pattern.get(0).length()) {
            throw new IllegalArgumentException("Pattern must be the same width on every line!");
        } else {
            this.pattern.add(patternIn);
            return this;
        }
    }

    @Override
    public XpRecipeBuilder setGroup(String groupIn) {
        return this;
    }

    public XpRecipeBuilder unlockedBy(String name, CriterionTriggerInstance criterionIn) {
        return this;
    }

    @Override
    public void build(Consumer<FinishedRecipe> consumer) {
        this.build(consumer, Tools.getId(this.result.getItem()));
    }

    @Override
    public void build(Consumer<FinishedRecipe> consumer, String save) {
        this.build(consumer, new ResourceLocation(save));
    }

    @Override
    public void build(Consumer<FinishedRecipe> consumer, ResourceLocation id) {
        this.validate(id);
        consumer.accept(new Result(id, this.result, this.pattern, this.key));
    }

    private void validate(ResourceLocation id) {
        if (this.pattern.isEmpty()) {
            throw new IllegalStateException("No pattern is defined for shaped recipe " + id + "!");
        } else {
            Set<Character> set = Sets.newHashSet(this.key.keySet());
            set.remove(' ');

            for(String s : this.pattern) {
                for(int i = 0; i < s.length(); ++i) {
                    char c0 = s.charAt(i);
                    if (!this.key.containsKey(c0) && c0 != ' ') {
                        throw new IllegalStateException("Pattern in recipe " + id + " uses undefined symbol '" + c0 + "'");
                    }

                    set.remove(c0);
                }
            }

            if (!set.isEmpty()) {
                throw new IllegalStateException("Ingredients are defined but not used in pattern for recipe " + id);
            } else if (this.pattern.size() == 1 && this.pattern.get(0).length() == 1) {
                throw new IllegalStateException("Shaped recipe " + id + " only takes in a single item - should it be a shapeless recipe instead?");
            }
        }
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final ItemStack result;
        private final List<String> pattern;
        private final Map<Character, Ingredient> key;

        public Result(ResourceLocation id, ItemStack result, List<String> pattern, Map<Character, Ingredient> ingredients) {
            this.id = id;
            this.result = result;
            this.pattern = pattern;
            this.key = ingredients;
        }

        @Override
        public void serializeRecipeData(@Nonnull JsonObject json) {
            JsonArray jsonarray = new JsonArray();
            for(String s : this.pattern) {
                jsonarray.add(s);
            }

            json.add("pattern", jsonarray);
            JsonObject jsonobject = new JsonObject();

            for(Map.Entry<Character, Ingredient> entry : this.key.entrySet()) {
                jsonobject.add(String.valueOf(entry.getKey()), entry.getValue().toJson());
            }

            json.add("key", jsonobject);
            JsonObject itemObject = new JsonObject();
            itemObject.addProperty("item", Tools.getId(this.result.getItem()).toString());
            if (this.result.getCount() > 1) {
                itemObject.addProperty("count", this.result.getCount());
            }
            if (this.result.hasTag()) {
                CompoundTag.CODEC.encodeStart(JsonOps.INSTANCE, this.result.getTag()).result().ifPresent(
                        result -> itemObject.add("nbt", result)
                );
            }

            json.add("result", itemObject);
        }
        @Override
        @Nonnull
        public RecipeSerializer<?> getType() {
            return XpCrafterModule.XP_RECIPE_SERIALIZER.get();
        }

        @Override
        @Nonnull
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        @Nullable
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Override
        @Nullable
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }
}
