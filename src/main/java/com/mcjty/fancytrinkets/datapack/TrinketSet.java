package com.mcjty.fancytrinkets.datapack;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TrinketSet implements IForgeRegistryEntry<TrinketSet> {

    private ResourceLocation name;
    private final List<ResourceLocation> trinkets;

    public static final Codec<TrinketSet> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.list(ResourceLocation.CODEC).fieldOf("trinkets").forGetter(l -> l.trinkets)
            ).apply(instance, TrinketSet::new));

    public TrinketSet(List<ResourceLocation> trinkets) {
        this.trinkets = trinkets;
    }

    @Override
    public TrinketSet setRegistryName(ResourceLocation name) {
        this.name = name;
        return this;
    }

    @Nullable
    @Override
    public ResourceLocation getRegistryName() {
        return name;
    }

    @Override
    public Class<TrinketSet> getRegistryType() {
        return TrinketSet.class;
    }

    public List<ResourceLocation> trinkets() {
        return this.trinkets;
    }
}
