package com.mcjty.fancytrinkets.modules.trinkets;

import com.mcjty.fancytrinkets.modules.effects.EffectInstance;
import net.minecraft.resources.Identifier;

import java.util.List;

public record TrinketInstance(
        Identifier id,
        Identifier bonusTableId,
        String nameKey,
        String descriptionKey,
        List<EffectInstance> effects) {
}
