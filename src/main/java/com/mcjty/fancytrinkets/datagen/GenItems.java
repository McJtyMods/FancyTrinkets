package com.mcjty.fancytrinkets.datagen;

import com.mcjty.fancytrinkets.modules.loot.LootModule;
import com.mcjty.fancytrinkets.modules.trinkets.TrinketsModule;
import com.mcjty.fancytrinkets.modules.xpcrafter.XpCrafterModule;
import mcjty.lib.datagen.BaseItemModelProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Map;

import static com.mcjty.fancytrinkets.FancyTrinkets.MODID;

public class GenItems extends BaseItemModelProvider {

    public GenItems(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (Map.Entry<ResourceLocation, TrinketsModule.TrinketInfo> entry : TrinketsModule.TRINKET_ITEMS.entrySet()) {
            TrinketsModule.TrinketInfo trinket = entry.getValue();
            itemGenerated(trinket.item().get(), trinket.texture());
        }

        for (Map.Entry<String, LootModule.Essence> entry : LootModule.ESSENCE_ITEMS.entrySet()) {
            LootModule.Essence essence = entry.getValue();
            itemGenerated(essence.item().get(), essence.texture());
        }

        parentedBlock(XpCrafterModule.EXPERIENCE_CRAFTER.get(), "block/experience_crafter");
    }

    @Override
    public String getName() {
        return "Fancy Trinkets Item Models";
    }
}
