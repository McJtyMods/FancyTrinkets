package com.mcjty.fancytrinkets.datagen;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;
import java.util.Map;

public class ItemTags extends ItemTagsProvider {

    public ItemTags(DataGenerator generator, BlockTagsProvider blockTags, ExistingFileHelper helper) {
        super(generator, blockTags, FancyTrinkets.MODID, helper);
    }

    @Override
    protected void addTags() {
        for (Map.Entry<ResourceLocation, Registration.TrinketInfo> entry : Registration.TRINKET_ITEMS.entrySet()) {
            for (TagKey<Item> key : entry.getValue().tags()) {
                tag(key).add(entry.getValue().item().get());
            }
        }
    }

    @Nonnull
    @Override
    public String getName() {
        return "FancyTrinkets Tags";
    }
}
