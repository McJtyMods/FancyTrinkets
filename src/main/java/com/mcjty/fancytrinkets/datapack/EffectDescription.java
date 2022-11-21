package com.mcjty.fancytrinkets.datapack;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

public record EffectDescription(ResourceLocation id, int level) {

    public static final Codec<EffectDescription> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("id").forGetter(l -> l.id.toString()),
                    Codec.INT.fieldOf("level").forGetter(l -> l.level)
            ).apply(instance, (id, level) -> new EffectDescription(new ResourceLocation(id), level)));

}
