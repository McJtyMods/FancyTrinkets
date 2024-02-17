package com.mcjty.fancytrinkets.modules.effects;

import com.mcjty.fancytrinkets.datapack.EffectDescription;
import mcjty.lib.datagen.DataGen;
import mcjty.lib.datagen.Dob;
import mcjty.lib.modules.IModule;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.stream.Collectors;

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
    public void initConfig(IEventBus bus) {
    }

    @Override
    public void initDatagen(DataGen dataGen) {
        dataGen.addCodecProvider("effects", "fancytrinkets/effects", EffectDescription.CODEC);
        dataGen.add(
                Dob.builder()
                        .codecObjectSupplier("effects", () -> DefaultEffects.DEFAULT_EFFECTS.entrySet().stream()
                                .map(entry -> Pair.of(entry.getKey(), entry.getValue().effect()))
                                .collect(Collectors.toMap(Pair::getLeft, Pair::getRight)))
        );
    }
}
