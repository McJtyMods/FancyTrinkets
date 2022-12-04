package com.mcjty.fancytrinkets.datagen;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.datapack.EffectDescription;
import com.mcjty.fancytrinkets.modules.effects.DefaultEffects;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;
import java.util.stream.Collectors;

public class GenEffects extends JsonCodecProvider<EffectDescription> {

    public GenEffects(DataGenerator generator, ExistingFileHelper fileHelper) {
        super(generator, fileHelper, FancyTrinkets.MODID, JsonOps.INSTANCE,
                PackType.SERVER_DATA, "fancytrinkets/effects", EffectDescription.CODEC, getEntries());
    }

    private static Map<ResourceLocation, EffectDescription> getEntries() {
        return DefaultEffects.DEFAULT_EFFECTS.entrySet().stream()
                .map(entry -> Pair.of(entry.getKey(), entry.getValue().effect()))
                .collect(Collectors.toMap(Pair::getLeft, Pair::getRight));
    }
}
