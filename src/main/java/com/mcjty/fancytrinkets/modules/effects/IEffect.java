package com.mcjty.fancytrinkets.modules.effects;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public interface IEffect {

    /// Get the id of this effect
    ResourceLocation getId();

    /// Get the description for this effect
    Component getDescription();
}
