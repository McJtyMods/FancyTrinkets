package com.mcjty.fancytrinkets.setup;

import com.mcjty.fancytrinkets.playerdata.PlayerEffects;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class ForgeEventHandlers {

    @SubscribeEvent
    public void onLivingDamageEvent(LivingDamageEvent.Pre event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            PlayerEffects playerEffects = PlayerEffects.getPlayerEffects(player);
            String msgId = event.getSource().getMsgId();
            float reduction = playerEffects.getDamageReduction(player, msgId);
            float damage = event.getNewDamage() * reduction;
            event.setNewDamage(damage);
            // @todo 1.21 no more setCanceled here?
//            if (damage < 0.00001f) {
//                event.setCanceled(true);
//            }
        }
    }

    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent.Pre event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            PlayerEffects effects = PlayerEffects.getPlayerEffects(serverPlayer);
            effects.tick(serverPlayer);
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerAboutToStartEvent event) {
        PlayerEffects.cleanup();
    }

    @SubscribeEvent
    public void onServerStopping(ServerStoppingEvent event) {
        PlayerEffects.cleanup();
    }
}
