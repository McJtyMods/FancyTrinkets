package com.mcjty.fancytrinkets.datapack;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BonusTable implements IForgeRegistryEntry<BonusTable> {

    private ResourceLocation name;
    private final List<EffectRef> effects;

    public List<EffectRef> effects() {
        return this.effects;
    }

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

    public BonusTable(List<EffectRef> effects) {
        this.effects = effects;
    }

    @Override
    public BonusTable setRegistryName(ResourceLocation name) {
        this.name = name;
        return this;
    }

    @Nullable
    @Override
    public ResourceLocation getRegistryName() {
        return name;
    }

    @Override
    public Class<BonusTable> getRegistryType() {
        return BonusTable.class;
    }
}
