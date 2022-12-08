package com.mcjty.fancytrinkets.datagen;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.datapack.BonusTable;
import com.mcjty.fancytrinkets.modules.trinkets.DefaultBonusTables;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;
import java.util.stream.Collectors;

public class GenBonusTables extends JsonCodecProvider<BonusTable> {

    public GenBonusTables(DataGenerator generator, ExistingFileHelper fileHelper) {
        super(generator, fileHelper, FancyTrinkets.MODID, JsonOps.INSTANCE,
                PackType.SERVER_DATA, "fancytrinkets/bonustables", BonusTable.CODEC, getEntries());
    }

    private static Map<ResourceLocation, BonusTable> getEntries() {
        return DefaultBonusTables.DEFAULT_BONUS_TABLES.entrySet().stream()
                .map(entry -> Pair.of(entry.getKey(), entry.getValue().bonusTable()))
                .collect(Collectors.toMap(Pair::getLeft, Pair::getRight));
    }
}
