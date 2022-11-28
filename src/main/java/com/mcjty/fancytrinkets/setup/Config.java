package com.mcjty.fancytrinkets.setup;


import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class Config {

    public static ForgeConfigSpec.IntValue MAXEXPERIENCE;

    public static void register() {
        Builder builder = new Builder();

        builder.comment("General settings").push("general");

        MAXEXPERIENCE = builder
                .comment("Maximum XP that can be stored in the Experience Crafter")
                .defineInRange("maxexperience", 5345, 1, Integer.MAX_VALUE);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, builder.build());
    }
}
