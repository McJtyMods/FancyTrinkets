package com.mcjty.fancytrinkets.setup;

import com.mcjty.fancytrinkets.keys.KeyBindings;
import com.mcjty.fancytrinkets.modules.trinkets.items.TrinketItemData;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class ClientEventHandlers {

    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        KeyBindings.init();
        event.register(KeyBindings.toggle1);
        event.register(KeyBindings.toggle2);
        event.register(KeyBindings.toggle3);
        event.register(KeyBindings.toggle4);
        event.register(KeyBindings.toggle5);
        event.register(KeyBindings.toggle6);
        event.register(KeyBindings.toggle7);
        event.register(KeyBindings.toggle8);
    }

    public static void onTooltip(ItemTooltipEvent event) {
        event.getItemStack().getCapability(Registration.TRINKET_ITEM_CAPABILITY).ifPresent(trinketItem -> {
            TrinketItemData.appendHoverText(trinketItem, event.getItemStack(), Minecraft.getInstance().level, event.getToolTip(), event.getFlags());
        });
    }
}