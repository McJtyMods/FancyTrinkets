package com.mcjty.fancytrinkets.playerdata;

import com.mcjty.fancytrinkets.modules.trinkets.items.TrinketItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class PacketSyncPlayerEffects {

    private final Set<String> toggles;

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(toggles.size());
        for (String toggle : toggles) {
            buf.writeUtf(toggle);
        }
    }

    public PacketSyncPlayerEffects(FriendlyByteBuf buf) {
        int size = buf.readInt();
        toggles = new HashSet<>(size);
        for (int i = 0 ; i < size ; i++) {
            toggles.add(buf.readUtf(32767));
        }
    }

    public PacketSyncPlayerEffects(PlayerEffects effects) {
        this.toggles = new HashSet<>(effects.getToggles());
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        TrinketItem.toggles = toggles;
    }
}
