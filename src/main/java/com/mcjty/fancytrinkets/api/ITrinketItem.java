package com.mcjty.fancytrinkets.api;

import com.mcjty.fancytrinkets.datapack.TrinketDescription;
import com.mcjty.fancytrinkets.modules.effects.IEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.BiConsumer;

public interface ITrinketItem {

    // Get the id of the trinket that this item represents
    ResourceLocation getTrinketId(ItemStack stack);

    void registerTrinketInstance(ServerLevel level, ResourceLocation id, TrinketDescription description);

    void forAllEffects(Level level, ItemStack stack, BiConsumer<IEffect, Integer> consumer);

    void addEffects(ItemStack stack, List<ResourceLocation> effects);
}
