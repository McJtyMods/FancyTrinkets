package com.mcjty.fancytrinkets;

import com.mcjty.fancytrinkets.modules.signs.SignsModule;
import com.mcjty.fancytrinkets.setup.Config;
import com.mcjty.fancytrinkets.setup.ModSetup;
import com.mcjty.fancytrinkets.setup.Registration;
import mcjty.lib.modules.Modules;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(com.mcjty.fancytrinkets.FancyTrinkets.MODID)
public class FancyTrinkets {

    public static final String MODID = "fancytrinkets";

    @SuppressWarnings("PublicField")
    public static ModSetup setup = new ModSetup();
    private Modules modules = new Modules();

    public FancyTrinkets() {
        Config.register();
        setupModules();
        Registration.register();

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(setup::init);
        bus.addListener(modules::init);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            bus.addListener(modules::initClient);
        });
    }

    private void setupModules() {
        modules.register(new SignsModule());
    }
}
