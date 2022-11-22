package com.mcjty.fancytrinkets.playerdata;


import com.mcjty.fancytrinkets.modules.effects.IEffect;
import mcjty.lib.varia.Counter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import org.checkerframework.checker.units.qual.C;

import java.util.HashMap;
import java.util.Map;

public class PlayerEffects {

    public static final Capability<PlayerEffects> PLAYER_EFFECTS = CapabilityManager.get(new CapabilityToken<>(){});

    public static class EffectHolder {
        private IEffect effect;
        private long endTime;

        public EffectHolder(IEffect effect, long endTime) {
            this.effect = effect;
            this.endTime = endTime;
        }
    }

    private Map<Integer, EffectHolder> effectMap = new HashMap<>();

    public PlayerEffects() {
    }

    public void tick(ServerPlayer player) {
        Map<Integer, EffectHolder> orig = effectMap;
        effectMap = new HashMap<>();
        long time = player.level.getGameTime();
        Counter<IEffect> collectedEffects = new Counter<>();
        for (Map.Entry<Integer, EffectHolder> entry : orig.entrySet()) {
            EffectHolder holder = entry.getValue();
            if (time <= holder.endTime) {
                collectedEffects.increment(holder.effect);
                effectMap.put(entry.getKey(), holder);
            }
        }
        for (Map.Entry<IEffect, Integer> entry : collectedEffects.entrySet()) {
            entry.getKey().perform(player, entry.getValue());
        }
    }

    public void copyFrom(PlayerEffects source) {
        effectMap.clear();
        effectMap.putAll(source.effectMap);
    }

    public Map<Integer, EffectHolder> getEffectMap() {
        return effectMap;
    }

    public void saveNBTData(CompoundTag compound) {

    }

    public void loadNBTData(CompoundTag compound) {

    }
}
