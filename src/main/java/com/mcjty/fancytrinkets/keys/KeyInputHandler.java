package com.mcjty.fancytrinkets.keys;

import com.mcjty.fancytrinkets.setup.Messages;
import mcjty.lib.typed.TypedMap;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class KeyInputHandler {

    @SubscribeEvent
    public void onKeyInput(InputEvent.Key event) {
        if (KeyBindings.toggle1.consumeClick()) {
            Messages.sendToServer(PacketSendKey.create(1));
        } else if (KeyBindings.toggle2.consumeClick()) {
            Messages.sendToServer(PacketSendKey.create(2));
        } else if (KeyBindings.toggle3.consumeClick()) {
            Messages.sendToServer(PacketSendKey.create(3));
        } else if (KeyBindings.toggle4.consumeClick()) {
            Messages.sendToServer(PacketSendKey.create(4));
        } else if (KeyBindings.toggle5.consumeClick()) {
            Messages.sendToServer(PacketSendKey.create(5));
        } else if (KeyBindings.toggle6.consumeClick()) {
            Messages.sendToServer(PacketSendKey.create(6));
        } else if (KeyBindings.toggle7.consumeClick()) {
            Messages.sendToServer(PacketSendKey.create(7));
        } else if (KeyBindings.toggle8.consumeClick()) {
            Messages.sendToServer(PacketSendKey.create(8));
        }
    }
}
