package com.mcjty.fancytrinkets.setup;
import com.mcjty.fancytrinkets.playerdata.*;
import com.mcjty.fancytrinkets.modules.trinkets.TrinketsModule;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
public final class FabricEventHandlers {
    public static void register() {
        ServerTickEvents.START_SERVER_TICK.register(server -> {
            for (var player : server.getPlayerList().getPlayers()) PlayerEffects.getPlayerEffects(player).tick(player);
        });
        ServerLifecycleEvents.SERVER_STARTING.register(server -> PlayerEffects.cleanup());
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> PlayerEffects.cleanup());
        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, resources, success) -> {
            if (success) TrinketsModule.registerTrinkets(server.overworld());
        });
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> sync(handler.player));
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, player, alive) -> sync(player));
    }
    private static void sync(net.minecraft.server.level.ServerPlayer player) {
        Messages.sendToPlayer(PacketSyncPlayerEffects.create(player.getAttachedOrCreate(Registration.PLAYER_EFFECTS)), player);
    }
}
