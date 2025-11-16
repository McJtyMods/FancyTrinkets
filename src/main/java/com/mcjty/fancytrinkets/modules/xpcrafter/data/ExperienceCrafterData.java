package com.mcjty.fancytrinkets.modules.xpcrafter.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record ExperienceCrafterData(int experience) {

    public static final ExperienceCrafterData DEFAULT = new ExperienceCrafterData(0);

    public static final Codec<ExperienceCrafterData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("experience").forGetter(ExperienceCrafterData::experience)
                    ).apply(instance, ExperienceCrafterData::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, ExperienceCrafterData> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, ExperienceCrafterData::experience,
            ExperienceCrafterData::new
    );


}
