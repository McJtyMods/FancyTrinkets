package com.mcjty.fancytrinkets.datagen;
import com.mcjty.fancytrinkets.FancyTrinkets;
import mcjty.lib.datagen.DataGen;
import net.fabricmc.fabric.api.datagen.v1.*;
public final class TrinketsDataGenerator implements DataGeneratorEntrypoint {
    @Override public void onInitializeDataGenerator(FabricDataGenerator generator) {
        generator.createPack().addProvider((output, registries) -> {
            DataGen data = new DataGen(output, registries);
            FancyTrinkets.modules.datagen(data, registries);
            FancyTrinkets.setup.datagen(data);
            data.generate();
            return data;
        });
    }
}
