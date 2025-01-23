package com.mcjty.fancytrinkets.setup;


import com.mcjty.fancytrinkets.FancyTrinkets;
import mcjty.lib.varia.Tools;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Config {

    public static ForgeConfigSpec.IntValue MAXEXPERIENCE;
    public static ForgeConfigSpec.DoubleValue CHANCE_BONUS_EFFECT1;
    public static ForgeConfigSpec.DoubleValue CHANCE_BONUS_EFFECT2;
    public static ForgeConfigSpec.DoubleValue CHANCE_BONUS_EFFECT3;
    public static ForgeConfigSpec.DoubleValue CHANCE_BONUS_EFFECT4;
    public static ForgeConfigSpec.IntValue EXPERIENCE_OFFSET;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> ADDITIONAL_TRINKET_ITEMS;

    private static Set<Item> TRINKET_ITEM_SET;

    public static boolean isAdditionalTrinketItem(Item item) {
        if (TRINKET_ITEM_SET == null) {
            TRINKET_ITEM_SET = new HashSet<>();
            ADDITIONAL_TRINKET_ITEMS.get().forEach(s -> {
                Item it = Tools.getItem(new ResourceLocation(s));
                if (it != null) {
                    TRINKET_ITEM_SET.add(it);
                }
            });
        }
        return TRINKET_ITEM_SET.contains(item);
    }

    public static void register() {
        Builder builder = new Builder();

        builder.comment("General settings").push("general");

        MAXEXPERIENCE = builder
                .comment("Maximum XP that can be stored in the Experience Crafter")
                .defineInRange("maxexperience", 1395, 1, Integer.MAX_VALUE);
        CHANCE_BONUS_EFFECT1 = builder
                .comment("Chance that upon crafting a trinket you get the first bonus effectId (percentage)")
                .defineInRange("chanceBonusEffect1", 100.0f, 0.0f, 100.0f);
        CHANCE_BONUS_EFFECT2 = builder
                .comment("Chance that upon crafting a trinket you get the second bonus effectId (percentage). This only applies if you already got the first bonus effectId")
                .defineInRange("chanceBonusEffect2", 55.0f, 0.0f, 100.0f);
        CHANCE_BONUS_EFFECT3 = builder
                .comment("Chance that upon crafting a trinket you get the third bonus effectId (percentage). This only applies if you already got the second bonus effectId")
                .defineInRange("chanceBonusEffect3", 30.0f, 0.0f, 100.0f);
        CHANCE_BONUS_EFFECT4 = builder
                .comment("Chance that upon crafting a trinket you get the fourth bonus effectId (percentage). This only applies if you already got the third bonus effectId")
                .defineInRange("chanceBonusEffect4", 2.0f, 0.0f, 100.0f);
        EXPERIENCE_OFFSET = builder
                .comment("This is added to the experience that you get from the input experience. This way even a craft with 0 experience can get some reasonable things")
                .defineInRange("qualityOffset", 150, 0, Integer.MAX_VALUE);
        ADDITIONAL_TRINKET_ITEMS = builder.comment("Item id's that will also be considered as trinket items (in addition to the standard items)")
                .defineList("additionalTrinketItems", Collections.emptyList(), s -> s instanceof String);

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, builder.build());
    }

    public static void onConfigLoading(ModConfigEvent.Loading event) {
        FancyTrinkets.setup.getLogger().info("Loading fancytrinkets config");
        TRINKET_ITEM_SET = null;
    }

    public static void onConfigReloading(ModConfigEvent.Reloading event) {
        FancyTrinkets.setup.getLogger().info("Reloading fancytrinkets config");
        TRINKET_ITEM_SET = null;
    }
}
