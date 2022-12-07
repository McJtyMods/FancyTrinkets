package com.mcjty.fancytrinkets.datapack;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public record TrinketSet(List<ResourceLocation> trinkets) {

    public static final Codec<TrinketSet> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.list(ResourceLocation.CODEC).fieldOf("trinkets").forGetter(l -> l.trinkets)
            ).apply(instance, TrinketSet::new));

}
