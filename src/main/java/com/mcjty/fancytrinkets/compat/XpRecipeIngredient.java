package com.mcjty.fancytrinkets.compat;

import com.mcjty.fancytrinkets.api.ITrinketItem;
import com.mcjty.fancytrinkets.setup.Registration;
import mezz.jei.api.ingredients.IIngredientTypeWithSubtypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class XpRecipeIngredient implements IIngredientTypeWithSubtypes<XpRecipeIngredient.XpIng, ItemStack> {

    @Override
    public Class<? extends ItemStack> getIngredientClass() {
        return ItemStack.class;
    }

    @Override
    public Class<? extends XpIng> getIngredientBaseClass() {
        return XpIng.class;
    }

    @Override
    public XpIng getBase(ItemStack ingredient) {
        ITrinketItem trinket = ingredient.getCapability(Registration.TRINKET_ITEM_CAPABILITY);
        if (trinket != null) {
            ResourceLocation id = trinket.getTrinketId(ingredient);
            return new XpIng(ingredient.getItem(), id);
        }
        return null;
    }

    public static record XpIng(Item item, ResourceLocation id) {}
}
