package com.mcjty.fancytrinkets.datagen;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.modules.trinkets.DefaultTrinkets;
import com.mcjty.fancytrinkets.modules.trinkets.items.TrinketItem;
import com.mcjty.fancytrinkets.modules.xpcrafter.recipe.XpRecipeBuilder;
import mcjty.lib.datagen.BaseRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.PartialNBTIngredient;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class GenRecipes extends BaseRecipeProvider {

    public GenRecipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {

        build(consumer, trinket("base_gold_ring"), XpRecipeBuilder.shapedRecipe(createTrinketStack("base_gold_ring"))
                        .define('g', Tags.Items.INGOTS_GOLD),
                " ggg ", "g   g", "g   g", "g   g", " ggg ");
        build(consumer, trinket("base_star"), XpRecipeBuilder.shapedRecipe(createTrinketStack("base_star"))
                .define('g', Tags.Items.DUSTS_PRISMARINE),
                "g g g", "  g  ", "ggggg", "  g  ", "g g g");
        build(consumer, trinket("flight_star"), XpRecipeBuilder.shapedRecipe(createTrinketStack("flight_star"))
                        .define('f', Tags.Items.FEATHERS)
                        .define('g', Tags.Items.INGOTS_GOLD)
                        .define('S', createTrinketIngredient("base_star")),
                " fgf ", "f   f", "g S g", "f   f", " fgf ");
    }

    private ResourceLocation trinket(String id) {
        return new ResourceLocation(FancyTrinkets.MODID, id);
    }

    private ItemStack createTrinketStack(String id) {
        ResourceLocation trinkedId = trinket(id);
        return TrinketItem.createTrinketStack(DefaultTrinkets.DEFAULT_TRINKETS.get(trinkedId).trinketDescription(),
                trinkedId);
    }

    @NotNull
    private PartialNBTIngredient createTrinketIngredient(String id) {
        ItemStack stack = createTrinketStack(id);
        return PartialNBTIngredient.of(stack.getItem(), stack.getTag());
    }

}
