package com.mcjty.fancytrinkets.datapack;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public record BonusTable(
        List<EffectRef> effects) {

    // Quality goes from 0 to 100%
    public static record EffectRef(ResourceLocation effect, float quality) {

        public static final Codec<EffectRef> EFFECTREF_CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        ResourceLocation.CODEC.fieldOf("id").forGetter(l -> l.effect),
                        Codec.FLOAT.fieldOf("quality").forGetter(l -> l.quality)
                ).apply(instance, EffectRef::new));
    }

    public static final Codec<BonusTable> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.list(EffectRef.EFFECTREF_CODEC).fieldOf("effects").forGetter(l -> l.effects)
            ).apply(instance, BonusTable::new));
}
