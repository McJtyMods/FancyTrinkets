package com.mcjty.fancytrinkets.playerdata;


import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.*;

public record PlayerEffectData(Set<String> toggles, Map<String, Float> damageReduction) {

    public static final PlayerEffectData DEFAULT = new PlayerEffectData(Collections.emptySet(), Collections.emptyMap());

    private static final Codec<Set<String>> TOGGLE_CODEC = Codec.list(Codec.STRING)
            .xmap(HashSet::new, ArrayList::new);
    private static final Codec<Map<String, Float>> DAMAGE_REDUCTION_CODEC = Codec.unboundedMap(Codec.STRING, Codec.FLOAT)
            .xmap(HashMap::new, map -> map);
    public static final Codec<PlayerEffectData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            TOGGLE_CODEC.optionalFieldOf("toggles", Set.of()).forGetter(playerEffects -> playerEffects.toggles),
            DAMAGE_REDUCTION_CODEC.optionalFieldOf("damageReduction", Map.of()).forGetter(playerEffects -> playerEffects.damageReduction)
    ).apply(instance, PlayerEffectData::new));

    public PlayerEffectData toggle(String toggle) {
        Set<String> newToggles = new HashSet<>(toggles);
        if (newToggles.contains(toggle)) {
            newToggles.remove(toggle);
        } else {
            newToggles.add(toggle);
        }
        return new PlayerEffectData(newToggles, damageReduction);
    }

    public boolean isToggleOn(String toggle) {
        return toggles.contains(toggle);
    }

    public PlayerEffectData registerDamageReduction(String dmgId, float factor) {
        Map<String, Float> newDamageReduction = new HashMap<>(damageReduction);
        newDamageReduction.put(dmgId, factor);
        return new PlayerEffectData(toggles, newDamageReduction);
    }

    public PlayerEffectData unregisterDamageReduction(String dmgId) {
        Map<String, Float> newDamageReduction = new HashMap<>(damageReduction);
        newDamageReduction.remove(dmgId);
        return new PlayerEffectData(toggles, newDamageReduction);
    }

    public float getDamageReduction(String dmgId) {
        return damageReduction.getOrDefault(dmgId, 1.0f);
    }
}
