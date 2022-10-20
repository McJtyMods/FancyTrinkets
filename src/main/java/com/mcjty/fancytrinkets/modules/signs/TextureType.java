package com.mcjty.fancytrinkets.modules.signs;

import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public enum TextureType {

    OAK("minecraft", "oak_planks"),
    ACACIA("minecraft", "acacia_planks"),
    SPRUCE("minecraft", "spruce_planks"),
    BIRCH("minecraft", "birch_planks"),
    JUNGLE("minecraft", "jungle_planks"),
    DARK_OAK("minecraft", "dark_oak_planks"),
    STONE("minecraft", "stone"),
    ANDESITE("minecraft", "andesite"),
    GRANITE("minecraft", "granite"),
    DIORITE("minecraft", "diorite"),
    WHITE("minecraft", "white_concrete"),
    BLACK("minecraft", "black_concrete"),
    IRON("minecraft", "iron_block"),
    ;

    private final ResourceLocation id;

    private static final Map<String, TextureType> MAP = new HashMap<>();
    static {
        for (TextureType type : TextureType.values()) {
            MAP.put(type.name().toLowerCase(), type);
        }
    }

    TextureType(String modid, String txt) {
        this.id = new ResourceLocation(modid, "block/" + txt);
    }

    public ResourceLocation getId() {
        return id;
    }

    public static TextureType getByName(String name) {
        return MAP.get(name.toLowerCase());
    }
}
