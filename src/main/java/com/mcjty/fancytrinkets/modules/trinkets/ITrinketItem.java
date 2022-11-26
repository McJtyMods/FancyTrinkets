package com.mcjty.fancytrinkets.modules.trinkets;

import com.mcjty.fancytrinkets.datapack.TrinketDescription;
import com.mcjty.fancytrinkets.modules.effects.IEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

public interface ITrinketItem {
    void registerTrinketInstance(ServerLevel level, ResourceLocation id, TrinketDescription description);

    void forAllEffects(ItemStack stack, Consumer<IEffect> consumer);
}
