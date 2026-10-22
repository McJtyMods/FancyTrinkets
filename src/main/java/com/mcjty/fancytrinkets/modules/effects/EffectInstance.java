package com.mcjty.fancytrinkets.modules.effects;

import net.minecraft.resources.Identifier;

public record EffectInstance(Identifier id, boolean hidden, IEffect effect) {
}
