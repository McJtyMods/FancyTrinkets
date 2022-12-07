package com.mcjty.fancytrinkets.datagen;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.datapack.TrinketSet;
import com.mcjty.fancytrinkets.modules.trinkets.DefaultTrinkets;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GenTrinketSets extends JsonCodecProvider<TrinketSet> {

    public GenTrinketSets(DataGenerator generator, ExistingFileHelper fileHelper) {
        super(generator, fileHelper, FancyTrinkets.MODID, JsonOps.INSTANCE,
                PackType.SERVER_DATA, "fancytrinkets/trinketsets", TrinketSet.CODEC, getEntries());
    }

    private static Map<ResourceLocation, TrinketSet> getEntries() {
        List<ResourceLocation> trinkets = new ArrayList<>(DefaultTrinkets.DEFAULT_TRINKETS.keySet());
        return Map.of(new ResourceLocation(FancyTrinkets.MODID, "standard"), new TrinketSet(trinkets));
    }
}
