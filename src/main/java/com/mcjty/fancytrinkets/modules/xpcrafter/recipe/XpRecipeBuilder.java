package com.mcjty.fancytrinkets.modules.xpcrafter.recipe;
import mcjty.lib.crafting.IRecipeBuilder;
import mcjty.lib.varia.Tools;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.*;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import java.util.*;
public class XpRecipeBuilder implements IRecipeBuilder<XpRecipeBuilder> {
    private final ItemStackTemplate result;
    private final HolderLookup.Provider lookup;
    private final Map<String, Ingredient> key = new LinkedHashMap<>();
    private final List<String> rows = new ArrayList<>();
    private String group = "";
    private XpRecipeBuilder(HolderLookup.Provider lookup, ItemStackTemplate result) { this.lookup = lookup; this.result = result; }
    public static XpRecipeBuilder shapedRecipe(HolderLookup.Provider lookup, ItemStackTemplate result) { return new XpRecipeBuilder(lookup, result); }
    @Override public XpRecipeBuilder define(Character symbol, TagKey<Item> tag) { return define(symbol, Ingredient.of(lookup.lookupOrThrow(Registries.ITEM).getOrThrow(tag))); }
    @Override public XpRecipeBuilder define(Character symbol, ItemLike item) { return define(symbol, Ingredient.of(item)); }
    @Override public XpRecipeBuilder define(Character symbol, Ingredient ingredient) { key.put(symbol.toString(), ingredient); return this; }
    @Override public XpRecipeBuilder patternLine(String row) { rows.add(row); return this; }
    public XpRecipeBuilder unlockedBy(String name, Criterion<?> criterion) { return this; }
    @Override public XpRecipeBuilder setGroup(String group) { this.group = group; return this; }
    @Override public void build(RecipeOutput output) { build(output, Tools.getId(result.item().value())); }
    @Override public void build(RecipeOutput output, String id) { build(output, Identifier.parse(id)); }
    @Override public void build(RecipeOutput output, Identifier id) {
        XpPattern pattern = new XpPattern(key, rows);
        pattern.ingredients();
        output.accept(ResourceKey.create(Registries.RECIPE, id), new XpRecipe(group, id, pattern, result), null);
    }
}
