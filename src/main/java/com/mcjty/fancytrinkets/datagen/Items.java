package com.mcjty.fancytrinkets.datagen;

import com.mcjty.fancytrinkets.modules.trinkets.TrinketsModule;
import mcjty.lib.datagen.BaseItemModelProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

import static com.mcjty.fancytrinkets.FancyTrinkets.MODID;

public class Items extends BaseItemModelProvider {

    public Items(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        itemGenerated(TrinketsModule.REGENERATION_RING.get(), "item/potion_ring");
    }

    @Override
    public String getName() {
        return "Fancy Trinkets Item Models";
    }
}
