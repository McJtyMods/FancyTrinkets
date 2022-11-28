package com.mcjty.fancytrinkets.datagen;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.modules.xpcrafter.XpCrafterModule;
import mcjty.lib.datagen.BaseBlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;

public class BlockTags extends BaseBlockTagsProvider {

    public BlockTags(DataGenerator generator, ExistingFileHelper helper) {
        super(generator, FancyTrinkets.MODID, helper);
    }

    @Override
    protected void addTags() {
        ironPickaxe(XpCrafterModule.EXPERIENCE_CRAFTER);
    }

    @Override
    @Nonnull
    public String getName() {
        return "FancyTrinkets Tags";
    }
}
