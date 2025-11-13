package com.mcjty.fancytrinkets;

import com.mcjty.fancytrinkets.curios.CuriosSetup;
import com.mcjty.fancytrinkets.datapack.CustomRegistries;
import com.mcjty.fancytrinkets.keys.KeyInputHandler;
import com.mcjty.fancytrinkets.modules.effects.EffectsModule;
import com.mcjty.fancytrinkets.modules.loot.LootModule;
import com.mcjty.fancytrinkets.modules.trinkets.TrinketsModule;
import com.mcjty.fancytrinkets.modules.xpcrafter.XpCrafterModule;
import com.mcjty.fancytrinkets.setup.*;
import mcjty.lib.datagen.DataGen;
import mcjty.lib.modules.Modules;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.InterModEnqueueEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.function.Supplier;

import static com.mcjty.fancytrinkets.FancyTrinkets.MODID;

@Mod(MODID)
public class FancyTrinkets {

    public static final String MODID = "fancytrinkets";

    public static FancyTrinkets instance;

    @SuppressWarnings("PublicField")
    public static ModSetup setup = new ModSetup();
    private final Modules modules = new Modules();

    public FancyTrinkets(ModContainer mod, IEventBus bus, Dist dist) {
        instance = this;
        Config.register(mod);
        setupModules(bus);
        Registration.register(bus);
        CustomRegistries.init(bus);

        bus.addListener(setup::init);
        bus.addListener(modules::init);
        bus.addListener(this::onInterModEnqueueEvent);
        bus.addListener(this::onDataGen);
        bus.addListener(Messages::registerMessages);

        if (dist.isClient()) {
            bus.addListener(modules::initClient);
            bus.addListener(ClientEventHandlers::onRegisterKeyMappings);
            NeoForge.EVENT_BUS.register(new KeyInputHandler());
        }
    }

    public static <T extends Item> Supplier<T> tab(Supplier<T> supplier) {
        return instance.setup.tab(supplier);
    }

    private void onDataGen(GatherDataEvent event) {
        DataGen datagen = new DataGen(MODID, event);
        modules.datagen(datagen, event.getLookupProvider());
        setup.datagen(datagen);
        datagen.generate();
    }

    private void setupModules(IEventBus bus) {
        modules.register(new EffectsModule());
        modules.register(new TrinketsModule(bus));
        modules.register(new XpCrafterModule(bus));
        modules.register(new LootModule());
    }

    private void onInterModEnqueueEvent(InterModEnqueueEvent event) {
        CuriosSetup.setupCurios();
    }
}
