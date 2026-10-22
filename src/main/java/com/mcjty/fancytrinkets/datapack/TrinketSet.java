package com.mcjty.fancytrinkets.datapack;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.Identifier;

import java.util.List;

public record TrinketSet(List<Identifier> trinkets) {

    public static final Codec<TrinketSet> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.list(Identifier.CODEC).fieldOf("trinkets").forGetter(l -> l.trinkets)
            ).apply(instance, TrinketSet::new));

}
