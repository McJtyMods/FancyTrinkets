package com.mcjty.fancytrinkets.datagen;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.modules.effects.EffectsModule;
import com.mcjty.fancytrinkets.modules.trinkets.items.TrinketItem;
import com.mcjty.fancytrinkets.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;

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

        for (Registration.TrinketInfo info : Registration.TRINKETS.values()) {
            add("item." + info.id().getNamespace() + "." + info.id().getPath(), info.description());
        }

        add("trinket.fancytrinkets.regeneration_ring.name", "Ring of Regeneration");
        add("trinket.fancytrinkets.regeneration_ring.description", "This ring gives you regeneration while wearing it");
        add("trinket.fancytrinkets.strength_ring.name", "Ring of Strength");
        add("trinket.fancytrinkets.strength_ring.description", "This ring gives you a strength boost while wearing it");
        add("trinket.fancytrinkets.flight_star.name", "Star of Flight");
        add("trinket.fancytrinkets.flight_star.description", "With this you can get creative flight");

        for (Map.Entry<ResourceLocation, EffectsModule.EffectInfo> entry : EffectsModule.EFFECTS.entrySet()) {
            add("effect.fancytrinkets." + entry.getKey().getPath(), entry.getValue().description());
        }
    }
}
