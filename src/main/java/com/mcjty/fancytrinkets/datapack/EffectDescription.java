package com.mcjty.fancytrinkets.datapack;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

public record EffectDescription(ResourceLocation id) {

    public static final Codec<EffectDescription> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("id").forGetter(l -> l.id.toString())
            ).apply(instance, (id) -> new EffectDescription(new ResourceLocation(id))));

}
