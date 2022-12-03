package com.mcjty.fancytrinkets.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = com.mcjty.fancytrinkets.FancyTrinkets.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        generator.addProvider(event.includeClient(), new GenItems(generator, event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), new GenLanguage(generator, "en_us"));
        generator.addProvider(event.includeClient(), new GenBlockStates(generator, event.getExistingFileHelper()));
        GenBlockTags blockTags = new GenBlockTags(generator, event.getExistingFileHelper());
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new GenItemTags(generator, blockTags, event.getExistingFileHelper()));
        generator.addProvider(event.includeServer(), new GenRecipes(generator));
        generator.addProvider(event.includeServer(), new GenEffects(generator));
        generator.addProvider(event.includeServer(), new GenTrinkets(generator));
        generator.addProvider(event.includeServer(), new GenBonusTables(generator));
        generator.addProvider(event.includeServer(), new GenLootTables(generator));
        generator.addProvider(event.includeServer(), new GenGLM(generator));
    }
}
