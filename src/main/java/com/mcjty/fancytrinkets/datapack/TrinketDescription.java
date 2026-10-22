package com.mcjty.fancytrinkets.datapack;

import com.mcjty.fancytrinkets.modules.effects.EffectInstance;
import com.mcjty.fancytrinkets.modules.trinkets.TrinketInstance;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mcjty.lib.varia.Tools;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public record TrinketDescription(
        Identifier item,
        Identifier bonusTableId,
        String nameKey,
        String descriptionKey,
        List<EffectRef> effects) {

    public static record EffectRef(Identifier effectId, boolean hidden) {

        public static final Codec<EffectRef> EFFECTREF_CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        Identifier.CODEC.fieldOf("id").forGetter(l -> l.effectId),
                        Codec.BOOL.fieldOf("hidden").forGetter(l -> l.hidden)
                ).apply(instance, EffectRef::new));
    }

    public static final Codec<TrinketDescription> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Identifier.CODEC.fieldOf("item").forGetter(l -> l.item),
                    Identifier.CODEC.fieldOf("bonustable").forGetter(l -> l.bonusTableId),
                    Codec.STRING.fieldOf("name").forGetter(l -> l.nameKey),
                    Codec.STRING.fieldOf("description").forGetter(l -> l.descriptionKey),
                    Codec.list(EffectRef.EFFECTREF_CODEC).fieldOf("effects").forGetter(l -> l.effects)
            ).apply(instance, TrinketDescription::new));

    public TrinketInstance build(Identifier id, Level level) {
        List<EffectInstance> effectInstances = new ArrayList<>();
        for (EffectRef effectRef : effects) {
            EffectDescription effectDescription = Tools.getRegistryAccess(level).lookupOrThrow(CustomRegistries.EFFECT_REGISTRY_KEY).getValue(effectRef.effectId());
            if (effectDescription == null) {
                throw new RuntimeException("Can't find effectId '" + effectRef.effectId().toString() + "'!");
            }
            effectInstances.add(new EffectInstance(effectRef.effectId(), effectRef.hidden, effectDescription.effect()));
        }
        return new TrinketInstance(id, bonusTableId, nameKey, descriptionKey, effectInstances);
    }
}
