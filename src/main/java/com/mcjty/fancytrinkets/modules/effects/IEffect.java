package com.mcjty.fancytrinkets.modules.effects;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

import java.awt.geom.RectangularShape;

public interface IEffect {

    default void tick(ItemStack stack, Entity entity, String slotId) {}

    default void onEquip(ItemStack stack, Entity entity, String slotId) {}

    default void onUnequip(ItemStack stack, Entity entity, String slotId) {}

    default void onHotkey(ItemStack stack, Entity entity, String slotId, int key) {}

    // Actually perform this effect (delayed from PlayerEffects)
    default void perform(ServerPlayer player, int strength) {}
}
