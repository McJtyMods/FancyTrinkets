package com.mcjty.fancytrinkets.modules.trinkets;

import com.mcjty.fancytrinkets.modules.trinkets.items.PotionEffectRing;
import mcjty.lib.modules.IModule;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.RegistryObject;

import static com.mcjty.fancytrinkets.setup.Registration.ITEMS;

public class TrinketsModule implements IModule {

    public static final RegistryObject<PotionEffectRing> REGENERATION_RING = ITEMS.register("regeneration_ring", PotionEffectRing::new);


    public TrinketsModule() {
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
