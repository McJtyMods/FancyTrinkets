package com.mcjty.fancytrinkets.modules.effects;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface IEffect {

    /// Get the id of this effect
    ResourceLocation getId();

    /// Get the description for this effect
    Component getDescription();

    void tick(ItemStack stack, Level level, Entity entity);
}
