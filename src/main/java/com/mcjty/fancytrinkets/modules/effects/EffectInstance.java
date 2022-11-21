package com.mcjty.fancytrinkets.modules.effects;

import com.mcjty.fancytrinkets.modules.effects.IEffect;
import net.minecraft.resources.ResourceLocation;

public record EffectInstance(ResourceLocation id, IEffect effect) {
}
