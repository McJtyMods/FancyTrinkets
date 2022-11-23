package com.mcjty.fancytrinkets.datapack;

import com.mcjty.fancytrinkets.modules.effects.EffectsModule;
import com.mcjty.fancytrinkets.modules.effects.IEffect;
import com.mcjty.fancytrinkets.modules.effects.EffectInstance;
import com.mcjty.fancytrinkets.modules.trinkets.TrinketInstance;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public record TrinketDescription(
        ResourceLocation id,
        ResourceLocation item,
        String nameKey,
        String descriptionKey,
        List<EffectDescription> effects
) {

    public static final Codec<TrinketDescription> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("id").forGetter(l -> l.id.toString()),
                    Codec.STRING.fieldOf("item").forGetter(l -> l.item.toString()),
                    Codec.STRING.fieldOf("name").forGetter(l -> l.nameKey),
                    Codec.STRING.fieldOf("description").forGetter(l -> l.descriptionKey),
                    Codec.list(EffectDescription.CODEC).fieldOf("effects").forGetter(l -> l.effects)
            ).apply(instance, TrinketDescription::create));

    private static TrinketDescription create(String id, String item, String nameKey, String descriptionKey, List<EffectDescription> effects) {
        return new TrinketDescription(new ResourceLocation(id), new ResourceLocation(item), nameKey, descriptionKey, effects);
    }

    public TrinketInstance build() {
        List<EffectInstance> effectInstances = new ArrayList<>();
        for (EffectDescription effectDesc : effects) {
            EffectsModule.EffectInfo effectInfo = EffectsModule.EFFECTS.get(effectDesc.id());
            if (effectInfo == null) {
                throw new RuntimeException("Can't find effect '" + effectDesc.id().toString() + "'!");
            }
            IEffect effect  = effectInfo.effect();
            effectInstances.add(new EffectInstance(effectDesc.id(), effect));
        }
        return new TrinketInstance(id, nameKey, descriptionKey, effectInstances);
    }
}
