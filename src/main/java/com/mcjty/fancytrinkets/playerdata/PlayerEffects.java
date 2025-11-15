package com.mcjty.fancytrinkets.playerdata;


import com.mcjty.fancytrinkets.modules.effects.IEffect;
import com.mcjty.fancytrinkets.setup.Messages;
import com.mcjty.fancytrinkets.setup.Registration;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mcjty.lib.varia.Counter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.*;

public class PlayerEffects {

    public record EffectHolder(IEffect effect, long endTime) {
    }

    private static final Map<UUID, PlayerEffects> PLAYER_EFFECTS_MAP = new HashMap<>();

    public static void cleanup() {
        PLAYER_EFFECTS_MAP.clear();
    }

    // Indexed on curios slot index
    private final Map<String, EffectHolder> effectMap = new HashMap<>();

    public PlayerEffects() {
    }

    public void tick(ServerPlayer player) {
        long time = player.level().getGameTime();
        Counter<IEffect> collectedEffects = new Counter<>();
        List<String> toDelete = new ArrayList<>();
        for (Map.Entry<String, EffectHolder> entry : effectMap.entrySet()) {
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
        for (String index : toDelete) {
            effectMap.remove(index);
        }
    }

    // Toggle and return the new value
    public boolean toggle(ServerPlayer player, String toggle) {
        PlayerEffectData data = player.getData(Registration.PLAYER_EFFECTS);
        data = data.toggle(toggle);
        player.setData(Registration.PLAYER_EFFECTS, data);
        Messages.sendToPlayer(PacketSyncPlayerEffects.create(data), player);
        return data.isToggleOn(toggle);
    }

    public boolean isToggleOn(ServerPlayer player, String toggle) {
        PlayerEffectData data = player.getData(Registration.PLAYER_EFFECTS);
        return data.isToggleOn(toggle);
    }

    public void registerEffect(String slotId, IEffect effect, long endTime) {
        effectMap.put(slotId, new PlayerEffects.EffectHolder(effect, endTime));
    }

    public void unregisterEffect(String slotId) {
        effectMap.remove(slotId);
    }

    public void registerDamageReduction(ServerPlayer player, String dmgId, float factor) {
        PlayerEffectData data = player.getData(Registration.PLAYER_EFFECTS);
        data = data.registerDamageReduction(dmgId, factor);
        player.setData(Registration.PLAYER_EFFECTS, data);
    }

    public void unregisterDamageReduction(ServerPlayer player, String dmgId) {
        PlayerEffectData data = player.getData(Registration.PLAYER_EFFECTS);
        data = data.unregisterDamageReduction(dmgId);
        player.setData(Registration.PLAYER_EFFECTS, data);
    }

    public float getDamageReduction(ServerPlayer player, String dmgId) {
        PlayerEffectData data = player.getData(Registration.PLAYER_EFFECTS);
        return data.getDamageReduction(dmgId);
    }

    // @todo 1.21 don't we need this? Double check
    public void copyFrom(PlayerEffects source) {
        effectMap.clear();
        effectMap.putAll(source.effectMap);
    }


    public static PlayerEffects getPlayerEffects(Player player) {
        return PLAYER_EFFECTS_MAP.computeIfAbsent(player.getUUID(), (uuid) -> new PlayerEffects());
    }

    public static void setPlayerEffects(Player player, PlayerEffects effects) {
        PLAYER_EFFECTS_MAP.put(player.getUUID(), effects);
    }

}
