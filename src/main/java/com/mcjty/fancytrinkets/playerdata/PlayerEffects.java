package com.mcjty.fancytrinkets.playerdata;


import com.mcjty.fancytrinkets.modules.effects.IEffect;
import mcjty.lib.varia.Counter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerEffects {

    public static final Capability<PlayerEffects> PLAYER_EFFECTS = CapabilityManager.get(new CapabilityToken<>(){});

    public static record EffectHolder(IEffect effect, long endTime) {
    }

    // Indexed on curios slot index
    private final Map<Integer, EffectHolder> effectMap = new HashMap<>();

    public PlayerEffects() {
    }

    public void tick(ServerPlayer player) {
        long time = player.level.getGameTime();
        Counter<IEffect> collectedEffects = new Counter<>();
        List<Integer> toDelete = new ArrayList<>();
        for (Map.Entry<Integer, EffectHolder> entry : effectMap.entrySet()) {
            EffectHolder holder = entry.getValue();
            if (holder.endTime >= time) {
                collectedEffects.increment(holder.effect);
            } else {
                toDelete.add(entry.getKey());
            }
        }
        for (Map.Entry<IEffect, Integer> entry : collectedEffects.entrySet()) {
            entry.getKey().perform(player, entry.getValue());
        }
        for (Integer index : toDelete) {
            effectMap.remove(index);
        }
    }

    public void registerEffect(int index, IEffect effect, long endTime) {
        effectMap.put(index, new PlayerEffects.EffectHolder(effect, endTime));
    }

    public void copyFrom(PlayerEffects source) {
        effectMap.clear();
        effectMap.putAll(source.effectMap);
    }

    public void saveNBTData(CompoundTag compound) {

    }

    public void loadNBTData(CompoundTag compound) {

    }
}
