package com.mcjty.fancytrinkets.setup;

import com.mcjty.fancytrinkets.keys.KeyBindings;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;

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


}
