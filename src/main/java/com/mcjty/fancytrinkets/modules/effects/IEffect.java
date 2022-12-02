package com.mcjty.fancytrinkets.modules.effects;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public interface IEffect {

    default void tick(ItemStack stack, ServerPlayer player, String slotId) {}

    default void onEquip(ItemStack stack, ServerPlayer player, String slotId) {}

    default void onUnequip(ItemStack stack, ServerPlayer player, String slotId) {}

    default void onHotkey(ItemStack stack, ServerPlayer player, String slotId, int key) {}

    // Actually perform this effectId (delayed from PlayerEffects)
    default void perform(ServerPlayer player, int strength) {}

    Integer getHotkey();

    String getToggle();
}
