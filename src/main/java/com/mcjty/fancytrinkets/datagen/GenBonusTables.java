package com.mcjty.fancytrinkets.datagen;

import com.google.gson.JsonElement;
import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.datapack.BonusTable;
import com.mcjty.fancytrinkets.modules.trinkets.DefaultBonusTables;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public class GenBonusTables implements DataProvider {

    private final DataGenerator.PathProvider pathProvider;

    public GenBonusTables(DataGenerator generator) {
        this.pathProvider = generator.createPathProvider(DataGenerator.Target.DATA_PACK, "fancytrinkets/bonustables");
    }

    @Override
    public void run(CachedOutput output) throws IOException {
        for (Map.Entry<ResourceLocation, DefaultBonusTables.BonusTableInfo> entry : DefaultBonusTables.DEFAULT_BONUS_TABLES.entrySet()) {
            table(output, entry.getKey().getPath(), entry.getValue().bonusTable());
        }
    }

    private void table(CachedOutput output, String id, BonusTable trinket) {
        JsonElement element = BonusTable.CODEC.encodeStart(JsonOps.INSTANCE, trinket)
                .getOrThrow(false, s -> FancyTrinkets.setup.getLogger().error(s));
        Path path = pathProvider.json(new ResourceLocation(FancyTrinkets.MODID, id));
        try {
            DataProvider.saveStable(output, element, path);
        } catch (IOException ioexception) {
            FancyTrinkets.setup.getLogger().error("Couldn't save bonus tables to {}", path, ioexception);
        }
    }

    @Override
    public String getName() {
        return "FancyTrinkets bonus table provider";
    }
}
