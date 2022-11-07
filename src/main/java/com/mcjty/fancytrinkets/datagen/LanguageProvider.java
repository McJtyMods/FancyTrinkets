package com.mcjty.fancytrinkets.datagen;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.modules.effects.items.TrinketItem;
import com.mcjty.fancytrinkets.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;

public class LanguageProvider extends net.minecraftforge.common.data.LanguageProvider {

    public LanguageProvider(DataGenerator gen, String locale) {
        super(gen, FancyTrinkets.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        add("itemGroup.fancytrinkets", "Fancy Trinkets");
        add("message.fancytrinkets.shiftmessage", "<Press Shift>");
        for (Map.Entry<ResourceLocation, Registration.Trinket> entry : Registration.TRINKETS.entrySet()) {
            Registration.Trinket trinket = entry.getValue();
            RegistryObject<TrinketItem> object = trinket.item();
            add(object.get(), trinket.description());
            add("message.fancytrinkets." + object.getId().getPath() + ".header", trinket.description());    // @todo
        }
    }
}
