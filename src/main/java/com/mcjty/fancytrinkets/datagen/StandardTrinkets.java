package com.mcjty.fancytrinkets.datagen;

import com.google.gson.JsonElement;
import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.datapack.TrinketDescription;
import com.mcjty.fancytrinkets.modules.trinkets.TrinketsModule;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public class StandardTrinkets implements DataProvider {

    private final DataGenerator.PathProvider pathProvider;

    public StandardTrinkets(DataGenerator generator) {
        this.pathProvider = generator.createPathProvider(DataGenerator.Target.DATA_PACK, "fancytrinkets/trinkets");
    }

    @Override
    public void run(CachedOutput output) throws IOException {
        for (Map.Entry<ResourceLocation, TrinketsModule.TrinketInfo> entry : TrinketsModule.TRINKETS.entrySet()) {
            trinket(output, entry.getKey().getPath(), entry.getValue().trinketDescription());
        }
    }

    private void trinket(CachedOutput output, String id, TrinketDescription trinket) {
        JsonElement element = TrinketDescription.CODEC.encodeStart(JsonOps.INSTANCE, trinket)
                .getOrThrow(false, s -> FancyTrinkets.setup.getLogger().error(s));
        Path path = pathProvider.json(new ResourceLocation(FancyTrinkets.MODID, id));
        try {
            DataProvider.saveStable(output, element, path);
        } catch (IOException ioexception) {
            FancyTrinkets.setup.getLogger().error("Couldn't save trinkets to {}", path, ioexception);
        }
    }

    @Override
    public String getName() {
        return "FancyTrinkets trinkets provider";
    }
}
