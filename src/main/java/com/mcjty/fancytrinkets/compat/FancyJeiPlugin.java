package com.mcjty.fancytrinkets.compat;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.setup.Registration;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public class FancyJeiPlugin implements IModPlugin {

    private static final ResourceLocation ID = new ResourceLocation(FancyTrinkets.MODID, "jeiplugin");

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        for (Registration.TrinketInfo info : Registration.TRINKET_ITEMS.values()) {
            registration.useNbtForSubtypes(info.item().get());
        }
    }


}
