package com.mcjty.fancytrinkets.keys;
import com.mcjty.fancytrinkets.setup.Messages;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
public final class KeyInputHandler {
    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.gui.screen() != null) return;
            for (int i = 0; i < KeyBindings.toggles.length; i++) {
                while (KeyBindings.toggles[i].consumeClick()) Messages.sendToServer(PacketSendKey.create(i + 1));
            }
        });
    }
}
