package com.mcjty.fancytrinkets.setup;
import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.keys.*;
import com.mcjty.fancytrinkets.modules.trinkets.items.TrinketItem;
import com.mcjty.fancytrinkets.modules.xpcrafter.client.GuiExperienceCrafter;
import com.mcjty.fancytrinkets.playerdata.PacketSyncPlayerEffects;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import java.util.List;
public final class ClientSetup implements ClientModInitializer {
    @Override public void onInitializeClient() {
        FancyTrinkets.modules.initClient();
        GuiExperienceCrafter.register();
        KeyBindings.init();
        for (var key : KeyBindings.toggles) KeyMappingHelper.registerKeyMapping(key);
        KeyInputHandler.register();
        ClientPlayNetworking.registerGlobalReceiver(PacketSyncPlayerEffects.TYPE, (packet, context) -> packet.handle());
        ClientPlayNetworking.registerGlobalReceiver(RecipeSync.TYPE, (packet, context) -> ClientEventHandlers.setXpRecipes(packet.recipes()));
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> com.mcjty.fancytrinkets.modules.trinkets.TrinketsModule.registerTrinkets(client.level));
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            ClientEventHandlers.setXpRecipes(List.of());
            TrinketItem.toggles.clear();
            com.mcjty.fancytrinkets.modules.trinkets.TrinketsModule.TRINKET_ITEMS.values().forEach(info -> info.item().get().clearTrinketInstances());
        });
    }
}
