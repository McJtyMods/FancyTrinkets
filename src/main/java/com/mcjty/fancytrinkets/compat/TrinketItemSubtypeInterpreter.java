package com.mcjty.fancytrinkets.compat;

import com.mcjty.fancytrinkets.api.ITrinketItem;
import com.mcjty.fancytrinkets.setup.Registration;
import mezz.jei.api.ingredients.subtypes.ISubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;

import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class TrinketItemSubtypeInterpreter implements ISubtypeInterpreter<ItemStack> {
    public static final TrinketItemSubtypeInterpreter INSTANCE = new TrinketItemSubtypeInterpreter();

    private TrinketItemSubtypeInterpreter() {

    }

    @Override
    public @Nullable Object getSubtypeData(ItemStack ingredient, UidContext context) {
        ITrinketItem trinket = ingredient.getCapability(Registration.TRINKET_ITEM_CAPABILITY);
        if (trinket != null) {
            return trinket.getTrinketId(ingredient);
        }
        return null;
    }
}
