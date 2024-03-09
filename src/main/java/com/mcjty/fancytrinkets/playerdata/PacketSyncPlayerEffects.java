package com.mcjty.fancytrinkets.playerdata;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.modules.trinkets.items.TrinketItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.HashSet;
import java.util.Set;

public record PacketSyncPlayerEffects(Set<String> toggles) implements CustomPacketPayload {

    public static final ResourceLocation ID = new ResourceLocation(FancyTrinkets.MODID, "sync_player_effects");

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeInt(toggles.size());
        for (String toggle : toggles) {
            buf.writeUtf(toggle);
        }
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    public static PacketSyncPlayerEffects create(FriendlyByteBuf buf) {
        int size = buf.readInt();
        Set<String> toggles = new HashSet<>(size);
        for (int i = 0 ; i < size ; i++) {
            toggles.add(buf.readUtf(32767));
        }
        return new PacketSyncPlayerEffects(toggles);
    }

    public static PacketSyncPlayerEffects create(PlayerEffects effects) {
        return new PacketSyncPlayerEffects(new HashSet<>(effects.getToggles()));
    }

    public void handle(PlayPayloadContext ctx) {
        TrinketItem.toggles = toggles;
    }
}
