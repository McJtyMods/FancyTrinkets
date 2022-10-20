package com.mcjty.fancytrinkets.setup;

import com.mcjty.fancytrinkets.modules.signs.SignsModule;
import mcjty.lib.setup.DefaultModSetup;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ModSetup extends DefaultModSetup {

    public ModSetup() {
        createTab("fancytrinkets", () -> new ItemStack(SignsModule.SIGN_CONFIGURATOR.get()));
    }

    @Override
    public void init(FMLCommonSetupEvent e) {
        super.init(e);

        MinecraftForge.EVENT_BUS.register(new ForgeEventHandlers());
        Messages.registerMessages("fancytrinkets");
    }

    @Override
    protected void setupModCompat() {
    }
}
