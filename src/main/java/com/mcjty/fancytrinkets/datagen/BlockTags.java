package com.mcjty.fancytrinkets.datagen;

import com.mcjty.fancytrinkets.FancyTrinkets;
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
    }

    @Override
    @Nonnull
    public String getName() {
        return "FancyTrinkets Tags";
    }
}
