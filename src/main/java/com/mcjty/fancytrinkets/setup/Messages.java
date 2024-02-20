package com.mcjty.fancytrinkets.setup;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.keys.PacketSendKey;
import com.mcjty.fancytrinkets.playerdata.PacketSyncPlayerEffects;
import mcjty.lib.network.IPayloadRegistrar;
import mcjty.lib.network.Networking;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkDirection;

public class Messages {

    private static IPayloadRegistrar registrar;

    public static void registerMessages() {
        registrar = Networking.registrar(FancyTrinkets.MODID)
                .versioned("1.0")
                .optional();

        registrar.play(PacketSendKey.class, PacketSendKey::create, handler -> handler.server(PacketSendKey::handle));
        registrar.play(PacketSyncPlayerEffects.class, PacketSyncPlayerEffects::create, handler -> handler.server(PacketSyncPlayerEffects::handle));
    }

    public static <T> void sendToPlayer(T packet, Player player) {
        registrar.getChannel().sendTo(packet, ((ServerPlayer)player).connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static <T> void sendToServer(T packet) {
        registrar.getChannel().sendToServer(packet);
    }
}
