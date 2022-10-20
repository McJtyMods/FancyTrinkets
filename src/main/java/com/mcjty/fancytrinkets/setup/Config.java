package com.mcjty.fancytrinkets.setup;


import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class Config {

    public static final Builder CLIENT_BUILDER = new Builder();

    public static ForgeConfigSpec CLIENT_CONFIG;

    public static ForgeConfigSpec.IntValue ICONS;
    public static ForgeConfigSpec.IntValue ICON_SIZE;
    public static ForgeConfigSpec.IntValue HORIZONTAL_ICONS;
    public static ForgeConfigSpec.IntValue VERTICAL_ICONS;

    public static void register() {
        CLIENT_BUILDER.comment("General settings").push("general");

        ICONS = CLIENT_BUILDER
                .comment("Number of icons on the icon sheet (not including the empty icon at 0,0)")
                .defineInRange("icons", 28, 1, 1024);
        ICON_SIZE = CLIENT_BUILDER
                .comment("Size of an individual icon")
                .defineInRange("iconSize", 32, 2, 512);
        HORIZONTAL_ICONS = CLIENT_BUILDER
                .comment("Horizontal numbers of icons on the signs.png icon sheet")
                .defineInRange("horizontalIcons", 8, 1, 128);
        VERTICAL_ICONS = CLIENT_BUILDER
                .comment("Vertical numbers of icons on the signs.png icon sheet")
                .defineInRange("verticalIcons", 8, 1, 128);

        CLIENT_CONFIG = CLIENT_BUILDER.build();

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_CONFIG);
    }
}
