package com.mcjty.fancytrinkets;
import com.mcjty.fancytrinkets.datapack.CustomRegistries;
import com.mcjty.fancytrinkets.modules.effects.EffectsModule;
import com.mcjty.fancytrinkets.modules.loot.LootModule;
import com.mcjty.fancytrinkets.modules.trinkets.TrinketsModule;
import com.mcjty.fancytrinkets.modules.xpcrafter.XpCrafterModule;
import com.mcjty.fancytrinkets.setup.*;
import mcjty.lib.modules.Modules;
import net.fabricmc.api.ModInitializer;
import net.minecraft.world.item.Item;
import java.util.function.Supplier;
public class FancyTrinkets implements ModInitializer {
    public static final String MODID = "fancytrinkets";
    public static FancyTrinkets instance;
    public static final ModSetup setup = new ModSetup();
    public static final Modules modules = new Modules();
    @Override public void onInitialize() {
        instance = this;
        Config.register();
        CustomRegistries.init();
        modules.register(new EffectsModule());
        modules.register(new TrinketsModule());
        modules.register(new XpCrafterModule());
        modules.register(new LootModule());
        Registration.register();
        setup.init();
        modules.init();
        setup.registerBlockCapabilities(Registration.RBLOCKS);
        Messages.registerMessages();
        FabricEventHandlers.register();
    }
    public static <T extends Item> Supplier<T> tab(Supplier<T> supplier) { return setup.tab(supplier); }
}
