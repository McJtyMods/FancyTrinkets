package com.mcjty.fancytrinkets;

import com.mcjty.fancytrinkets.curios.CuriosSetup;
import com.mcjty.fancytrinkets.modules.effects.EffectsModule;
import com.mcjty.fancytrinkets.modules.effects.IEffect;
import com.mcjty.fancytrinkets.modules.rings.RingsModule;
import com.mcjty.fancytrinkets.setup.Config;
import com.mcjty.fancytrinkets.setup.ModSetup;
import com.mcjty.fancytrinkets.setup.Registration;
import mcjty.lib.modules.Modules;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.HashMap;
import java.util.Map;

import static com.mcjty.fancytrinkets.FancyTrinkets.MODID;

@Mod(MODID)
public class FancyTrinkets {

    public static final String MODID = "fancytrinkets";

    @SuppressWarnings("PublicField")
    public static ModSetup setup = new ModSetup();
    private final Modules modules = new Modules();

    public FancyTrinkets() {
        Config.register();
        setupModules();
        Registration.register();

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(setup::init);
        bus.addListener(modules::init);
        bus.addListener(this::onInterModEnqueueEvent);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            bus.addListener(modules::initClient);
        });
    }

    private void setupModules() {
        modules.register(new EffectsModule());
        modules.register(new RingsModule());
    }

    private void onInterModEnqueueEvent(InterModEnqueueEvent event) {
        CuriosSetup.setupCurios();
    }
}
