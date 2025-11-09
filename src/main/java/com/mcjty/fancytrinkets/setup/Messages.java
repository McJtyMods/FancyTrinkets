package com.mcjty.fancytrinkets.setup;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.keys.PacketSendKey;
import com.mcjty.fancytrinkets.playerdata.PacketSyncPlayerEffects;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class Messages {

    public static void registerMessages(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(FancyTrinkets.MODID)
                .versioned("1.0")
                .optional();

        registrar.playToServer(PacketSendKey.TYPE, PacketSendKey.STREAM_CODEC, PacketSendKey::handle);
        registrar.playToServer(PacketSyncPlayerEffects.TYPE, PacketSyncPlayerEffects.STREAM_CODEC, PacketSyncPlayerEffects::handle);
    }

    public static <T extends CustomPacketPayload> void sendToPlayer(T packet, Player player) {
        PacketDistributor.sendToPlayer((ServerPlayer)player, packet);
    }

    public static <T extends CustomPacketPayload> void sendToServer(T packet) {
        PacketDistributor.sendToServer(packet);
    }
}
