package com.mcjty.fancytrinkets.datagen;

import com.mcjty.fancytrinkets.modules.xpcrafter.XpCrafterModule;
import mcjty.lib.datagen.BaseLootTableProvider;
import net.minecraft.data.DataGenerator;

public class GenLootTables extends BaseLootTableProvider {

    public GenLootTables(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void addTables() {
        addStandardTable(XpCrafterModule.EXPERIENCE_CRAFTER.get(), XpCrafterModule.TYPE_EXPERIENCE_CRAFTER.get());
    }

    @Override
    public String getName() {
        return "FancyTrinkets loot table provider";
    }
}
