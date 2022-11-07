package com.mcjty.fancytrinkets.modules.rings;

import com.mcjty.fancytrinkets.modules.effects.EffectsModule;
import com.mcjty.fancytrinkets.modules.effects.items.TrinketItem;
import com.mcjty.fancytrinkets.setup.Registration;
import mcjty.lib.modules.IModule;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.RegistryObject;

public class RingsModule implements IModule {

    public static final RegistryObject<TrinketItem> REGENERATION_RING = Registration.trinket("regeneration_ring", "item/gold_ring",
            "Potion Ring of Regeneration", EffectsModule.EFFECT_REGENERATION);
    public static final RegistryObject<TrinketItem> ABSORBTION_RING = Registration.trinket("absorbtion_ring", "item/gold_ring",
            "Potion Ring of Absorbtion", EffectsModule.EFFECT_ABSORPTION);


    public RingsModule() {
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
