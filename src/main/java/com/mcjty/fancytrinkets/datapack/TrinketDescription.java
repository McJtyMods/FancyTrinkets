package com.mcjty.fancytrinkets.datapack;

import com.mcjty.fancytrinkets.modules.trinkets.TrinketInstance;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

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
        return new TrinketInstance(id, nameKey, descriptionKey);
    }
}

/*
{
  "id": "fancytrinkets:regeneration_ring",
  "name": "trinket.fancytrinkets.regeneration_ring.name",
  "description": "trinket.fancytrinkets.regeneration_ring.description",
  "effects": [
    {
      "id": "fancytrinkets:regeneration",
      "level": 1
    }
  ],
  "slots": ["ring"],
  "lifetime": "permanent",
  "sets": ["golden_rings"]
}
 */