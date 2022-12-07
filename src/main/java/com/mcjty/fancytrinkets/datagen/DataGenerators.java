package com.mcjty.fancytrinkets.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = com.mcjty.fancytrinkets.FancyTrinkets.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new GenItems(generator, fileHelper));
        generator.addProvider(event.includeClient(), new GenLanguage(generator, "en_us"));
        generator.addProvider(event.includeClient(), new GenBlockStates(generator, fileHelper));

        GenBlockTags blockTags = new GenBlockTags(generator, fileHelper);
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new GenItemTags(generator, blockTags, fileHelper));
        generator.addProvider(event.includeServer(), new GenRecipes(generator));
        generator.addProvider(event.includeServer(), new GenEffects(generator, fileHelper));
        generator.addProvider(event.includeServer(), new GenTrinkets(generator, fileHelper));
        generator.addProvider(event.includeServer(), new GenTrinketSets(generator, fileHelper));
        generator.addProvider(event.includeServer(), new GenBonusTables(generator, fileHelper));
        generator.addProvider(event.includeServer(), new GenLootTables(generator));
        generator.addProvider(event.includeServer(), new GenGLM(generator));
    }
}
