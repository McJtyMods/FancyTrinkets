package com.mcjty.fancytrinkets.datagen;

import com.google.gson.JsonElement;
import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.datapack.EffectDescription;
import com.mcjty.fancytrinkets.modules.effects.DefaultEffects;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public class GenEffects implements DataProvider {

    private final DataGenerator.PathProvider pathProvider;

    public GenEffects(DataGenerator generator) {
        this.pathProvider = generator.createPathProvider(DataGenerator.Target.DATA_PACK, "fancytrinkets/effects");
    }

    @Override
    public void run(CachedOutput output) throws IOException {
        for (Map.Entry<ResourceLocation, DefaultEffects.EffectInfo> entry : DefaultEffects.DEFAULT_EFFECTS.entrySet()) {
            effect(output, entry.getKey().getPath(), entry.getValue().effect());
        }
    }

    private void effect(CachedOutput output, String id, EffectDescription effect) {
        JsonElement element = EffectDescription.CODEC.encodeStart(JsonOps.INSTANCE, effect)
                .getOrThrow(false, s -> FancyTrinkets.setup.getLogger().error(s));
        Path path = pathProvider.json(new ResourceLocation(FancyTrinkets.MODID, id));
        try {
            DataProvider.saveStable(output, element, path);
        } catch (IOException ioexception) {
            FancyTrinkets.setup.getLogger().error("Couldn't save effects to {}", path, ioexception);
        }
    }

    @Override
    public String getName() {
        return "FancyTrinkets effects provider";
    }
}
