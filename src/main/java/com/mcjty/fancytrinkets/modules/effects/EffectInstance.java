package com.mcjty.fancytrinkets.modules.effects;

import net.minecraft.resources.ResourceLocation;

public record EffectInstance(ResourceLocation id, boolean hidden, IEffect effect) {
}
