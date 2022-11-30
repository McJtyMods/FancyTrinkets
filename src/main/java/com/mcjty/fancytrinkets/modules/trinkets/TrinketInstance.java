package com.mcjty.fancytrinkets.modules.trinkets;

import com.mcjty.fancytrinkets.modules.effects.EffectInstance;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public record TrinketInstance(
        ResourceLocation id,
        ResourceLocation bonusTableId,
        String nameKey,
        String descriptionKey,
        List<EffectInstance> effects) {
}
