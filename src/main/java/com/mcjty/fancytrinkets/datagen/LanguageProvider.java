package com.mcjty.fancytrinkets.datagen;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.compat.XpRecipeCategory;
import com.mcjty.fancytrinkets.keys.KeyBindings;
import com.mcjty.fancytrinkets.modules.effects.EffectsModule;
import com.mcjty.fancytrinkets.modules.trinkets.TrinketsModule;
import com.mcjty.fancytrinkets.modules.trinkets.items.TrinketItem;
import com.mcjty.fancytrinkets.modules.xpcrafter.XpCrafterModule;
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
        add(TrinketItem.MESSAGE_FANCYTRINKETS_SHIFTMESSAGE, "<Press Shift>");
        add(TrinketItem.MESSAGE_FANCYTRINKETS_BONUS, "Bonus effects:");
        add(KeyBindings.FANCYTRINKETS_KEY_TOGGLE_1, "Fancy Trinkets Toggle 1");
        add(KeyBindings.FANCYTRINKETS_KEY_TOGGLE_2, "Fancy Trinkets Toggle 2");
        add(KeyBindings.FANCYTRINKETS_KEY_TOGGLE_3, "Fancy Trinkets Toggle 3");
        add(KeyBindings.FANCYTRINKETS_KEY_TOGGLE_4, "Fancy Trinkets Toggle 4");
        add(KeyBindings.FANCYTRINKETS_KEY_TOGGLE_5, "Fancy Trinkets Toggle 5");
        add(KeyBindings.FANCYTRINKETS_KEY_TOGGLE_6, "Fancy Trinkets Toggle 6");
        add(KeyBindings.FANCYTRINKETS_KEY_TOGGLE_7, "Fancy Trinkets Toggle 7");
        add(KeyBindings.FANCYTRINKETS_KEY_TOGGLE_8, "Fancy Trinkets Toggle 8");
        add(KeyBindings.KEY_CATEGORIES_FANCYTRINKETS, "Fancy Trinkets");
        add(XpRecipeCategory.KEY_XP_RECIPE_CATEGORY, "Experience Crafter");

        add("block." + XpCrafterModule.EXPERIENCE_CRAFTER.getId().getNamespace() + "." + XpCrafterModule.EXPERIENCE_CRAFTER.getId().getPath(), "Experience Crafter");

        for (Registration.TrinketInfo info : Registration.TRINKET_ITEMS.values()) {
            add("item." + info.id().getNamespace() + "." + info.id().getPath(), info.description());
        }
        for (Map.Entry<ResourceLocation, TrinketsModule.TrinketInfo> entry : TrinketsModule.TRINKETS.entrySet()) {
            ResourceLocation key = entry.getKey();
            add("trinket." + key.getNamespace() + "." + key.getPath() + ".name", entry.getValue().name());
            add("trinket." + key.getNamespace() + "." + key.getPath() + ".description", entry.getValue().description());
        }
        for (Map.Entry<ResourceLocation, EffectsModule.EffectInfo> entry : EffectsModule.EFFECTS.entrySet()) {
            add("effect.fancytrinkets." + entry.getKey().getPath(), entry.getValue().description());
        }
    }
}
