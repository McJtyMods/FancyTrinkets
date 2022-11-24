package com.mcjty.fancytrinkets.datapack;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public record EffectDescription(ResourceLocation id, Map<String, String> params) {

    public static final Codec<EffectDescription> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("id").forGetter(l -> l.id.toString()),
                    Codec.unboundedMap(Codec.STRING, Codec.STRING).optionalFieldOf("params").forGetter(l -> l.params.isEmpty() ? Optional.empty() : Optional.of(l.params))
            ).apply(instance, (id, params) -> new EffectDescription(new ResourceLocation(id), params.orElse(Collections.emptyMap()))));

}
