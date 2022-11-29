package com.mcjty.fancytrinkets.datagen;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.modules.trinkets.items.TrinketItem;
import com.mcjty.fancytrinkets.modules.xpcrafter.recipe.XpRecipeBuilder;
import mcjty.lib.datagen.BaseRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.StrictNBTIngredient;

import java.util.function.Consumer;

public class Recipes extends BaseRecipeProvider {

    public Recipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
//        build(consumer, ShapedRecipeBuilder.shaped(SignsModule.SIGN_CONFIGURATOR.get())
//                        .define('S', SignsModule.SQUARE_SIGN.get())
//                        .unlockedBy("iron", has(Items.IRON_INGOT)),
//                "S  ", " i ", "  i");
        build(consumer, new ResourceLocation(FancyTrinkets.MODID, "base_star"), XpRecipeBuilder.shapedRecipe(TrinketItem.createTrinketStack("base_star"))
                        .define('S', Items.NETHER_STAR),
                " rrr ", "r   r", "r S r", "r   r", " rrr ");
        build(consumer, new ResourceLocation(FancyTrinkets.MODID, "flight_star"), XpRecipeBuilder.shapedRecipe(TrinketItem.createTrinketStack("flight_star"))
                        .define('S', StrictNBTIngredient.of(TrinketItem.createTrinketStack("base_star"))),
                " rrr ", "r   r", "r S r", "r   r", " rrr ");
    }

}
