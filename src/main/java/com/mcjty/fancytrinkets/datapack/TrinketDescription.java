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

public record TrinketDescription(
        ResourceLocation item,
        String nameKey,
        String descriptionKey,
        List<EffectRef> effects) {

    public static record EffectRef(ResourceLocation effect, boolean hidden) {

        public static final Codec<EffectRef> EFFECTREF_CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        Codec.STRING.fieldOf("id").forGetter(l -> l.effect.toString()),
                        Codec.BOOL.fieldOf("hidden").forGetter(l -> l.hidden)
                ).apply(instance, (id, hidden) -> new EffectRef(new ResourceLocation(id), hidden)));
    }

    public static final Codec<TrinketDescription> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("item").forGetter(l -> l.item.toString()),
                    Codec.STRING.fieldOf("name").forGetter(l -> l.nameKey),
                    Codec.STRING.fieldOf("description").forGetter(l -> l.descriptionKey),
                    Codec.list(EffectRef.EFFECTREF_CODEC).fieldOf("effects").forGetter(l -> l.effects)
            ).apply(instance, TrinketDescription::create));

    private static TrinketDescription create(String item, String nameKey, String descriptionKey, List<EffectRef> effects) {
        return new TrinketDescription(new ResourceLocation(item), nameKey, descriptionKey, effects);
    }

    public TrinketInstance build(ResourceLocation id, ServerLevel level) {
        List<EffectInstance> effectInstances = new ArrayList<>();
        for (EffectRef effectRef : effects) {
            EffectDescription effectDescription = level.registryAccess().registryOrThrow(CustomRegistries.EFFECT_REGISTRY_KEY).get(effectRef.effect());
            if (effectDescription == null) {
                throw new RuntimeException("Can't find effect '" + effectRef.effect().toString() + "'!");
            }
            IEffect effect = effectDescription.build();
            effectInstances.add(new EffectInstance(effectRef.effect(), effectRef.hidden, effect));
        }
        return new TrinketInstance(id, nameKey, descriptionKey, effectInstances);
    }
}
