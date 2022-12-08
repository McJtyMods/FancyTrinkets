package com.mcjty.fancytrinkets.setup;

import com.mcjty.fancytrinkets.keys.KeyBindings;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientEventHandlers {

    public static void onRegisterKeyMappings(FMLClientSetupEvent event) {
        KeyBindings.init();
    }

}
