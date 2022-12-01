package com.mcjty.fancytrinkets.compat;

import com.mcjty.fancytrinkets.modules.trinkets.items.TrinketItem;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class TrinketItemSubtypeInterpreter implements IIngredientSubtypeInterpreter<ItemStack> {
    public static final TrinketItemSubtypeInterpreter INSTANCE = new TrinketItemSubtypeInterpreter();

    private TrinketItemSubtypeInterpreter() {

    }

    @Override
    public String apply(ItemStack ingredient, UidContext context) {
        ResourceLocation trinketId = TrinketItem.getTrinketId(ingredient);
        return trinketId == null ? IIngredientSubtypeInterpreter.NONE : trinketId.toString();
    }
}
