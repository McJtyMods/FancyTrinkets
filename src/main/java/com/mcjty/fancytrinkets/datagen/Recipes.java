package com.mcjty.fancytrinkets.datagen;

import mcjty.lib.datagen.BaseRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;

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
    }

}
