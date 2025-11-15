package com.mcjty.fancytrinkets.playerdata;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.modules.trinkets.items.TrinketItem;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.HashSet;
import java.util.Set;

public record PacketSyncPlayerEffects(Set<String> toggles) implements CustomPacketPayload {

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(FancyTrinkets.MODID, "sync_player_effects");
    public static final CustomPacketPayload.Type<PacketSyncPlayerEffects> TYPE = new Type<>(ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, PacketSyncPlayerEffects> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.collection(HashSet::new)), PacketSyncPlayerEffects::toggles,
            PacketSyncPlayerEffects::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static PacketSyncPlayerEffects create(PlayerEffectData effects) {
        return new PacketSyncPlayerEffects(new HashSet<>(effects.toggles()));
    }

    public void handle(IPayloadContext ctx) {
        TrinketItem.toggles = toggles;
    }
}
