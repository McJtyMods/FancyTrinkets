package com.mcjty.fancytrinkets.datapack;

import com.mcjty.fancytrinkets.modules.effects.EffectInstance;
import com.mcjty.fancytrinkets.modules.trinkets.TrinketInstance;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;

import java.util.ArrayList;
import java.util.List;

public record TrinketDescription(
        ResourceLocation item,
        ResourceLocation bonusTableId,
        String nameKey,
        String descriptionKey,
        List<EffectRef> effects) {

    public static record EffectRef(ResourceLocation effectId, boolean hidden) {

        public static final Codec<EffectRef> EFFECTREF_CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        Codec.STRING.fieldOf("id").forGetter(l -> l.effectId.toString()),
                        Codec.BOOL.fieldOf("hidden").forGetter(l -> l.hidden)
                ).apply(instance, (id, hidden) -> new EffectRef(new ResourceLocation(id), hidden)));
    }

    public static final Codec<TrinketDescription> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("item").forGetter(l -> l.item.toString()),
                    Codec.STRING.fieldOf("bonustable").forGetter(l -> l.bonusTableId.toString()),
                    Codec.STRING.fieldOf("name").forGetter(l -> l.nameKey),
                    Codec.STRING.fieldOf("description").forGetter(l -> l.descriptionKey),
                    Codec.list(EffectRef.EFFECTREF_CODEC).fieldOf("effects").forGetter(l -> l.effects)
            ).apply(instance, TrinketDescription::create));

    private static TrinketDescription create(String item, String bonusTable, String nameKey, String descriptionKey, List<EffectRef> effects) {
        return new TrinketDescription(new ResourceLocation(item), new ResourceLocation(bonusTable), nameKey, descriptionKey, effects);
    }

    public TrinketInstance build(ResourceLocation id, ServerLevel level) {
        List<EffectInstance> effectInstances = new ArrayList<>();
        for (EffectRef effectRef : effects) {
            EffectDescription effectDescription = level.registryAccess().registryOrThrow(CustomRegistries.EFFECT_REGISTRY_KEY).get(effectRef.effectId());
            if (effectDescription == null) {
                throw new RuntimeException("Can't find effectId '" + effectRef.effectId().toString() + "'!");
            }
            effectInstances.add(new EffectInstance(effectRef.effectId(), effectRef.hidden, effectDescription.effect()));
        }
        return new TrinketInstance(id, bonusTableId, nameKey, descriptionKey, effectInstances);
    }
}
