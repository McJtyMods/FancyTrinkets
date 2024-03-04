package com.mcjty.fancytrinkets.setup;


import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.fml.ModLoadingContext;
import net.neoforged.neoforge.fml.config.ModConfig;

public class Config {

    public static ModConfigSpec.IntValue MAXEXPERIENCE;
    public static ModConfigSpec.DoubleValue CHANCE_BONUS_EFFECT1;
    public static ModConfigSpec.DoubleValue CHANCE_BONUS_EFFECT2;
    public static ModConfigSpec.DoubleValue CHANCE_BONUS_EFFECT3;
    public static ModConfigSpec.DoubleValue CHANCE_BONUS_EFFECT4;
    public static ModConfigSpec.IntValue EXPERIENCE_OFFSET;

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


        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, builder.build());
    }
}
