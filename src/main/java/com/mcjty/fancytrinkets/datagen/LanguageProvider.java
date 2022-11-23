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
        add("trinket.fancytrinkets.stepassist_ring.name", "Step Assist Ring");
        add("trinket.fancytrinkets.stepassist_ring.description", "Use this ring to get step assist");
        add("trinket.fancytrinkets.reduced_gravity_ring.name", "Reduced Gravity Ring");
        add("trinket.fancytrinkets.reduced_gravity_ring.description", "Use this ring to get a gravity reduction");
        add("trinket.fancytrinkets.swimspeed_ring.name", "Swim Speed Ring");
        add("trinket.fancytrinkets.swimspeed_ring.description", "Use this ring to swim faster in water");
        add("trinket.fancytrinkets.power_star.name", "Power Star");
        add("trinket.fancytrinkets.power_star.description", "All that power!");
        add("trinket.fancytrinkets.swift_star.name", "Swiftness Star");
        add("trinket.fancytrinkets.swift_star.description", "Gives you the power to move!");
        add("trinket.fancytrinkets.super_health.name", "Super health boost");
        add("trinket.fancytrinkets.super_health.description", "This makes you very healthy indeed!");
        add("trinket.fancytrinkets.nogravity_feather.name", "No gravity");
        add("trinket.fancytrinkets.nogravity_feather.description", "You feel so light");

        for (Map.Entry<ResourceLocation, EffectsModule.EffectInfo> entry : EffectsModule.EFFECTS.entrySet()) {
            add("effect.fancytrinkets." + entry.getKey().getPath(), entry.getValue().description());
        }
    }
}
