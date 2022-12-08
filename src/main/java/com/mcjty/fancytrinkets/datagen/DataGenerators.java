package com.mcjty.fancytrinkets.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = com.mcjty.fancytrinkets.FancyTrinkets.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();

        if (event.includeClient()) {
            generator.addProvider(new GenItems(generator, fileHelper));
            generator.addProvider(new GenLanguage(generator, "en_us"));
            generator.addProvider(new GenBlockStates(generator, fileHelper));
        }

        if (event.includeServer()) {
            GenBlockTags blockTags = new GenBlockTags(generator, fileHelper);
            generator.addProvider(blockTags);
            generator.addProvider(new GenItemTags(generator, blockTags, fileHelper));
            generator.addProvider(new GenRecipes(generator));
            generator.addProvider(new GenEffects(generator, fileHelper));
            generator.addProvider(new GenTrinkets(generator, fileHelper));
            generator.addProvider(new GenTrinketSets(generator, fileHelper));
            generator.addProvider(new GenBonusTables(generator, fileHelper));
            generator.addProvider(new GenLootTables(generator));
            generator.addProvider(new GenGLM(generator));
        }
    }
}
