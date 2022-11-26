package com.mcjty.fancytrinkets.setup;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.playerdata.PlayerEffects;
import com.mcjty.fancytrinkets.playerdata.PropertiesDispatcher;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ForgeEventHandlers {

    @SubscribeEvent
    public void onLivingDamageEvent(LivingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            player.getCapability(PlayerEffects.PLAYER_EFFECTS).ifPresent(playerEffects -> {
                String msgId = event.getSource().msgId;
                float reduction = playerEffects.getDamageReduction(msgId);
                event.setAmount(event.getAmount() * reduction);
            });
        }
    }

    @SubscribeEvent
    public void onEntityConstructing(AttachCapabilitiesEvent<Entity> event){
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(PlayerEffects.PLAYER_EFFECTS).isPresent()) {
                event.addCapability(new ResourceLocation(FancyTrinkets.MODID, "playereffects"), new PropertiesDispatcher());
            }
        }
    }

    @SubscribeEvent
    public void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            // We need to copyFrom the capabilities
            event.getOriginal().getCapability(PlayerEffects.PLAYER_EFFECTS).ifPresent(oldStore -> {
                event.getEntity().getCapability(PlayerEffects.PLAYER_EFFECTS).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.player instanceof ServerPlayer serverPlayer) {
            serverPlayer.getCapability(PlayerEffects.PLAYER_EFFECTS).ifPresent(playerEffects -> {
                playerEffects.tick(serverPlayer);
            });
        }
    }
}
