package com.mcjty.fancytrinkets.modules.trinkets.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public record TrinketData(Identifier trinketId, List<Identifier> effects) {

    public static final TrinketData DEFAULT = new TrinketData(null, Collections.emptyList());

    public static final Codec<TrinketData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.optionalFieldOf("id").forGetter(s -> Optional.ofNullable(s.trinketId)),
            Codec.list(Identifier.CODEC).fieldOf("effects").forGetter(TrinketData::effects)
                    ).apply(instance, (resourceLocation, effects) -> new TrinketData(resourceLocation.orElse(null), effects))
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, TrinketData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.optional(Identifier.STREAM_CODEC), s -> Optional.ofNullable(s.trinketId),
            Identifier.STREAM_CODEC.apply(ByteBufCodecs.list()), TrinketData::effects,
            (resourceLocation, effects) -> new TrinketData(resourceLocation.orElse(null), effects)
    );

    public TrinketData withEffects(List<Identifier> effects) {
        return new TrinketData(trinketId, effects);
    }
}
