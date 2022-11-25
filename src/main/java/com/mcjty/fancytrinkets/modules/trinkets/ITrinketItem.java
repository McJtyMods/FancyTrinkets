package com.mcjty.fancytrinkets.modules.trinkets;

import com.mcjty.fancytrinkets.datapack.TrinketDescription;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;

public interface ITrinketItem {
    void registerTrinketInstance(ServerLevel level, ResourceLocation id, TrinketDescription description);
}
