package com.mcjty.fancytrinkets.datagen;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.compat.XpRecipeCategory;
import com.mcjty.fancytrinkets.keys.KeyBindings;
import com.mcjty.fancytrinkets.modules.effects.DefaultEffects;
import com.mcjty.fancytrinkets.modules.loot.LootModule;
import com.mcjty.fancytrinkets.modules.trinkets.DefaultTrinkets;
import com.mcjty.fancytrinkets.modules.trinkets.TrinketsModule;
import com.mcjty.fancytrinkets.modules.trinkets.items.TrinketItem;
import com.mcjty.fancytrinkets.modules.xpcrafter.XpCrafterModule;
import com.mcjty.fancytrinkets.modules.xpcrafter.blocks.ExperienceCrafterBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;

public class GenLanguage extends net.minecraftforge.common.data.LanguageProvider {

    public GenLanguage(DataGenerator gen, String locale) {
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

        RegistryObject<ExperienceCrafterBlock> b = XpCrafterModule.EXPERIENCE_CRAFTER;
        add("block." + getStringId(b), "Experience Crafter");
        add("message." + getStringId(b) + ".header", "Craft trinkets using experience. With low experience you'll potentially get low quality trinkets");

        for (TrinketsModule.TrinketInfo info : TrinketsModule.TRINKET_ITEMS.values()) {
            add("item." + getStringId(info.id()), info.description());
        }
        for (LootModule.Essence essence : LootModule.ESSENCE_ITEMS.values()) {
            add("item." + getStringItemId(essence.item()), essence.description());
        }
        for (Map.Entry<ResourceLocation, DefaultTrinkets.TrinketInfo> entry : DefaultTrinkets.DEFAULT_TRINKETS.entrySet()) {
            ResourceLocation key = entry.getKey();
            add("trinket." + key.getNamespace() + "." + key.getPath() + ".name", entry.getValue().name());
            add("trinket." + key.getNamespace() + "." + key.getPath() + ".description", entry.getValue().description());
        }
        for (Map.Entry<ResourceLocation, DefaultEffects.EffectInfo> entry : DefaultEffects.DEFAULT_EFFECTS.entrySet()) {
            add("effectId.fancytrinkets." + entry.getKey().getPath(), entry.getValue().description());
        }
    }

    private String getStringId(RegistryObject<? extends Block> b) {
        return b.getId().getNamespace() + "." + b.getId().getPath();
    }

    private String getStringItemId(RegistryObject<? extends Item> b) {
        return b.getId().getNamespace() + "." + b.getId().getPath();
    }

    private String getStringId(ResourceLocation id) {
        return id.getNamespace() + "." + id.getPath();
    }
}
