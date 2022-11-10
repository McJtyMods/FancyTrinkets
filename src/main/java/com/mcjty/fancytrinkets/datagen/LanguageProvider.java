package com.mcjty.fancytrinkets.datagen;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.modules.effects.EffectsModule;
import com.mcjty.fancytrinkets.modules.trinkets.items.TrinketItem;
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
        add(TrinketItem.MESSAGE_EFFECT_HEADER, "  Effect: ");
        add(TrinketItem.MESSAGE_FANCYTRINKETS_SHIFTMESSAGE, "<Press Shift>");
        for (Map.Entry<ResourceLocation, Registration.TrinketInfo> entry : Registration.TRINKETS.entrySet()) {
            Registration.TrinketInfo trinket = entry.getValue();
            RegistryObject<TrinketItem> object = trinket.item();
            add(object.get(), trinket.description());
            add("message.fancytrinkets." + object.getId().getPath() + ".header", trinket.header());
            add("message.fancytrinkets." + object.getId().getPath() + ".gold", trinket.extraInformation());
        }

        for (Map.Entry<ResourceLocation, EffectsModule.EffectInfo> entry : EffectsModule.EFFECTS.entrySet()) {
            add("effect.fancytrinkets." + entry.getKey().getPath(), entry.getValue().description());
        }
    }
}
