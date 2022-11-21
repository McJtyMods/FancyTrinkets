package com.mcjty.fancytrinkets.modules.trinkets;

import net.minecraft.resources.ResourceLocation;

public record TrinketInstance(
        ResourceLocation id,
        String nameKey,
        String descriptionKey) {
}
