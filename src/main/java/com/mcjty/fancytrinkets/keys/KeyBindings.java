package com.mcjty.fancytrinkets.keys;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.settings.KeyConflictContext;

public class KeyBindings {

    public static final String FANCYTRINKETS_KEY_TOGGLE_1 = "fancytrinkets.key.toggle1";
    public static final String FANCYTRINKETS_KEY_TOGGLE_2 = "fancytrinkets.key.toggle2";
    public static final String FANCYTRINKETS_KEY_TOGGLE_3 = "fancytrinkets.key.toggle3";
    public static final String FANCYTRINKETS_KEY_TOGGLE_4 = "fancytrinkets.key.toggle4";
    public static final String FANCYTRINKETS_KEY_TOGGLE_5 = "fancytrinkets.key.toggle5";
    public static final String FANCYTRINKETS_KEY_TOGGLE_6 = "fancytrinkets.key.toggle6";
    public static final String FANCYTRINKETS_KEY_TOGGLE_7 = "fancytrinkets.key.toggle7";
    public static final String FANCYTRINKETS_KEY_TOGGLE_8 = "fancytrinkets.key.toggle8";
    public static final String KEY_CATEGORIES_FANCYTRINKETS = "key.categories.fancytrinkets";

    public static KeyMapping toggle1;
    public static KeyMapping toggle2;
    public static KeyMapping toggle3;
    public static KeyMapping toggle4;
    public static KeyMapping toggle5;
    public static KeyMapping toggle6;
    public static KeyMapping toggle7;
    public static KeyMapping toggle8;

    public static KeyMapping[] toggles;

    public static void init() {
        toggle1 = new KeyMapping(FANCYTRINKETS_KEY_TOGGLE_1, KeyConflictContext.IN_GAME, InputConstants.UNKNOWN, KEY_CATEGORIES_FANCYTRINKETS);
        toggle2 = new KeyMapping(FANCYTRINKETS_KEY_TOGGLE_2, KeyConflictContext.IN_GAME, InputConstants.UNKNOWN, KEY_CATEGORIES_FANCYTRINKETS);
        toggle3 = new KeyMapping(FANCYTRINKETS_KEY_TOGGLE_3, KeyConflictContext.IN_GAME, InputConstants.UNKNOWN, KEY_CATEGORIES_FANCYTRINKETS);
        toggle4 = new KeyMapping(FANCYTRINKETS_KEY_TOGGLE_4, KeyConflictContext.IN_GAME, InputConstants.UNKNOWN, KEY_CATEGORIES_FANCYTRINKETS);
        toggle5 = new KeyMapping(FANCYTRINKETS_KEY_TOGGLE_5, KeyConflictContext.IN_GAME, InputConstants.UNKNOWN, KEY_CATEGORIES_FANCYTRINKETS);
        toggle6 = new KeyMapping(FANCYTRINKETS_KEY_TOGGLE_6, KeyConflictContext.IN_GAME, InputConstants.UNKNOWN, KEY_CATEGORIES_FANCYTRINKETS);
        toggle7 = new KeyMapping(FANCYTRINKETS_KEY_TOGGLE_7, KeyConflictContext.IN_GAME, InputConstants.UNKNOWN, KEY_CATEGORIES_FANCYTRINKETS);
        toggle8 = new KeyMapping(FANCYTRINKETS_KEY_TOGGLE_8, KeyConflictContext.IN_GAME, InputConstants.UNKNOWN, KEY_CATEGORIES_FANCYTRINKETS);
        toggles = new KeyMapping[] { toggle1, toggle2, toggle3, toggle4, toggle5, toggle6, toggle7, toggle8 };
        for (KeyMapping toggle : toggles) {
            ClientRegistry.registerKeyBinding(toggle);
        }
    }
}
