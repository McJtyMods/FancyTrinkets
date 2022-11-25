package com.mcjty.fancytrinkets.datapack;

import com.mcjty.fancytrinkets.modules.effects.EffectInstance;
import com.mcjty.fancytrinkets.modules.effects.IEffect;
import com.mcjty.fancytrinkets.modules.trinkets.TrinketInstance;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public record TrinketDescription(
        ResourceLocation item,
        String nameKey,
        String descriptionKey,
        List<ResourceLocation> effects) {

    public static final Codec<TrinketDescription> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("item").forGetter(l -> l.item.toString()),
                    Codec.STRING.fieldOf("name").forGetter(l -> l.nameKey),
                    Codec.STRING.fieldOf("description").forGetter(l -> l.descriptionKey),
                    Codec.list(Codec.STRING).fieldOf("effects").forGetter(l -> l.effects.stream().map(ResourceLocation::toString).collect(Collectors.toList()))
            ).apply(instance, TrinketDescription::create));

    private static TrinketDescription create(String item, String nameKey, String descriptionKey, List<String> effects) {
        return new TrinketDescription(new ResourceLocation(item), nameKey, descriptionKey,
                effects.stream().map(ResourceLocation::new).collect(Collectors.toList()));
    }

    public TrinketInstance build(ResourceLocation id, ServerLevel level) {
        List<EffectInstance> effectInstances = new ArrayList<>();
        for (ResourceLocation effectId : effects) {
            EffectDescription effectDescription = level.registryAccess().registryOrThrow(CustomRegistries.EFFECT_REGISTRY_KEY).get(effectId);
            if (effectDescription == null) {
                throw new RuntimeException("Can't find effect '" + effectId.toString() + "'!");
            }
            IEffect effect = effectDescription.build();
            effectInstances.add(new EffectInstance(effectId, effect));
        }
        return new TrinketInstance(id, nameKey, descriptionKey, effectInstances);
    }
}
