package com.mcjty.fancytrinkets.datagen;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.datapack.TrinketDescription;
import com.mcjty.fancytrinkets.modules.trinkets.DefaultTrinkets;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;
import java.util.stream.Collectors;

public class GenTrinkets extends JsonCodecProvider<TrinketDescription> {

    public GenTrinkets(DataGenerator generator, ExistingFileHelper fileHelper) {
        super(generator, fileHelper, FancyTrinkets.MODID, JsonOps.INSTANCE,
                PackType.SERVER_DATA, "fancytrinkets/trinkets", TrinketDescription.CODEC, getEntries());
    }

    private static Map<ResourceLocation, TrinketDescription> getEntries() {
        return DefaultTrinkets.DEFAULT_TRINKETS.entrySet().stream()
                .map(entry -> Pair.of(entry.getKey(), entry.getValue().trinketDescription()))
                .collect(Collectors.toMap(Pair::getLeft, Pair::getRight));
    }
}
