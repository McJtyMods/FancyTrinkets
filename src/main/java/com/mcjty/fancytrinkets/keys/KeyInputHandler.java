package com.mcjty.fancytrinkets.keys;

import com.mcjty.fancytrinkets.setup.Messages;
import mcjty.lib.typed.TypedMap;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class KeyInputHandler {

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (KeyBindings.toggle1.consumeClick()) {
            Messages.INSTANCE.sendToServer(new PacketSendKey(1));
        } else if (KeyBindings.toggle2.consumeClick()) {
            Messages.INSTANCE.sendToServer(new PacketSendKey(2));
        } else if (KeyBindings.toggle3.consumeClick()) {
            Messages.INSTANCE.sendToServer(new PacketSendKey(3));
        } else if (KeyBindings.toggle4.consumeClick()) {
            Messages.INSTANCE.sendToServer(new PacketSendKey(4));
        } else if (KeyBindings.toggle5.consumeClick()) {
            Messages.INSTANCE.sendToServer(new PacketSendKey(5));
        } else if (KeyBindings.toggle6.consumeClick()) {
            Messages.INSTANCE.sendToServer(new PacketSendKey(6));
        } else if (KeyBindings.toggle7.consumeClick()) {
            Messages.INSTANCE.sendToServer(new PacketSendKey(7));
        } else if (KeyBindings.toggle8.consumeClick()) {
            Messages.INSTANCE.sendToServer(new PacketSendKey(8));
        }
    }
}
