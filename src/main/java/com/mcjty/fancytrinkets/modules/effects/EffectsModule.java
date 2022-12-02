package com.mcjty.fancytrinkets.modules.effects;

import mcjty.lib.modules.IModule;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class EffectsModule implements IModule {

    public EffectsModule() {
        DefaultEffects.init();
    }

    @Override
    public void init(FMLCommonSetupEvent event) {
    }

    @Override
    public void initClient(FMLClientSetupEvent event) {
    }

    @Override
    public void initConfig() {
    }

}
