package com.mcjty.fancytrinkets.setup;
import com.mcjty.fancytrinkets.keys.PacketSendKey;
import com.mcjty.fancytrinkets.playerdata.PacketSyncPlayerEffects;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
public final class Messages {
    public static void registerMessages() {
        PayloadTypeRegistry.serverboundPlay().register(PacketSendKey.TYPE, PacketSendKey.STREAM_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(PacketSyncPlayerEffects.TYPE, PacketSyncPlayerEffects.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(PacketSendKey.TYPE, (packet, context) -> packet.handle(context.player()));
        RecipeSync.register();
    }
    public static <T extends CustomPacketPayload> void sendToPlayer(T packet, Player player) { ServerPlayNetworking.send((ServerPlayer) player, packet); }
    public static <T extends CustomPacketPayload> void sendToServer(T packet) { mcjty.lib.network.Networking.sendToServer(packet); }
}
