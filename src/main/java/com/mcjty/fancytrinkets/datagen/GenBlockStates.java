package com.mcjty.fancytrinkets.datagen;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.modules.xpcrafter.XpCrafterModule;
import mcjty.lib.datagen.BaseBlockStateProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class GenBlockStates extends BaseBlockStateProvider {

    public GenBlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, FancyTrinkets.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(XpCrafterModule.EXPERIENCE_CRAFTER.get(), topBasedModel("experience_crafter",
                modLoc("block/experience_crafter_top"),
                modLoc("block/experience_crafter_side"),
                modLoc("block/experience_crafter_bottom")));
    }
}
