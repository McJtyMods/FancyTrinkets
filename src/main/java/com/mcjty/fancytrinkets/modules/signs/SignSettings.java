package com.mcjty.fancytrinkets.modules.signs;

import net.minecraft.nbt.CompoundTag;

public class SignSettings {

    private boolean transparent = false;
    private Integer backColor = null;       // Color of background
    private int textColor = 0xffffff;       // Color of text
    private boolean bright = false;         // True if the screen contents is full bright
    private TextureType type = TextureType.OAK;
    private boolean large = false;
    private int iconIndex = 0;

    public boolean isTransparent() {
        return transparent;
    }

    public void setTransparent(boolean transparent) {
        this.transparent = transparent;
    }

    public Integer getBackColor() {
        return backColor;
    }

    public void setBackColor(Integer backColor) {
        this.backColor = backColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public boolean isBright() {
        return bright;
    }

    public void setBright(boolean bright) {
        this.bright = bright;
    }

    public TextureType getTextureType() {
        return type;
    }

    public void setTextureType(TextureType type) {
        this.type = type;
    }

    public boolean isLarge() {
        return large;
    }

    public void setLarge(boolean large) {
        this.large = large;
    }

    public int getIconIndex() {
        return iconIndex;
    }

    public void setIconIndex(int iconIndex) {
        this.iconIndex = iconIndex;
    }

    public void read(CompoundTag tag) {
        transparent = tag.getBoolean("transparent");
        if (tag.contains("backColor")) {
            backColor = tag.getInt("backColor");
        } else {
            backColor = null;
        }
        textColor = tag.getInt("textColor");
        bright = tag.getBoolean("bright");
        large = tag.getBoolean("large");
        iconIndex = tag.getInt("image");
        type = TextureType.getByName(tag.getString("ttype"));
        if (type == null) {
            type = TextureType.OAK;
        }
    }

    public void write(CompoundTag tag) {
        tag.putBoolean("transparent", transparent);
        if (backColor != null) {
            tag.putInt("backColor", backColor);
        }
        tag.putInt("textColor", textColor);
        tag.putBoolean("bright", bright);
        tag.putBoolean("large", large);
        tag.putInt("image", iconIndex);
        tag.putString("ttype", type.name().toLowerCase());
    }

}
