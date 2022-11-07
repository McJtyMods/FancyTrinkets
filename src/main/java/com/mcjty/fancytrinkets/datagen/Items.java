package com.mcjty.fancytrinkets.datagen;

import com.mcjty.fancytrinkets.setup.Registration;
import mcjty.lib.datagen.BaseItemModelProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Map;

import static com.mcjty.fancytrinkets.FancyTrinkets.MODID;

public class Items extends BaseItemModelProvider {

    public Items(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (Map.Entry<ResourceLocation, Registration.Trinket> entry : Registration.TRINKETS.entrySet()) {
            Registration.Trinket trinket = entry.getValue();
            itemGenerated(trinket.item().get(), trinket.texture());
        }
    }

    @Override
    public String getName() {
        return "Fancy Trinkets Item Models";
    }
}
