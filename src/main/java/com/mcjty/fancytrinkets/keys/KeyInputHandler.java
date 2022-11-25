package com.mcjty.fancytrinkets.keys;

import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class KeyInputHandler {

    @SubscribeEvent
    public void onKeyInput(InputEvent.Key event) {
        if (KeyBindings.toggle1.consumeClick()) {
        } else if (KeyBindings.toggle2.consumeClick()) {
        } else if (KeyBindings.toggle3.consumeClick()) {
        } else if (KeyBindings.toggle4.consumeClick()) {
        } else if (KeyBindings.toggle5.consumeClick()) {
        } else if (KeyBindings.toggle6.consumeClick()) {
        } else if (KeyBindings.toggle7.consumeClick()) {
        } else if (KeyBindings.toggle8.consumeClick()) {
        }
    }
}
