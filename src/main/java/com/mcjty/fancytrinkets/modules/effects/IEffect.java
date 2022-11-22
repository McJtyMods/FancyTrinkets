package com.mcjty.fancytrinkets.modules.effects;

import mcjty.lib.varia.ComponentFactory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public interface IEffect {

    /// Get the id of this effect
    ResourceLocation getId();

    /// Get the description for this effect
    default Component getDescription() {
        return ComponentFactory.translatable("effect." + getId().getNamespace() + "." + getId().getPath());
    }

    default void tick(ItemStack stack, Entity entity, int index) {}

    default void onEquip(ItemStack stack, Entity entity, int index) {}

    default void onUnequip(ItemStack stack, Entity entity, int index) {}

    // Actually perform this effect (delayed from PlayerEffects)
    default void perform(ServerPlayer player, int strength) {}
}
