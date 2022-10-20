package com.mcjty.fancytrinkets.modules.signs;

import com.mcjty.fancytrinkets.modules.signs.items.SignConfiguratorItem;
import mcjty.lib.modules.IModule;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.RegistryObject;

import static com.mcjty.fancytrinkets.setup.Registration.ITEMS;

public class SignsModule implements IModule {

    public static final RegistryObject<SignConfiguratorItem> SIGN_CONFIGURATOR = ITEMS.register("sign_configurator", SignConfiguratorItem::new);


    public SignsModule() {
    }

    @Override
    public void init(FMLCommonSetupEvent event) {

    }

    @Override
    public void initClient(FMLClientSetupEvent event) {
    }

    @Override
    public void initConfig() {

    }
}
