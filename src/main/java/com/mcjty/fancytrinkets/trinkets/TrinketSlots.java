package com.mcjty.fancytrinkets.trinkets;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
public final class TrinketSlots {
    public static final TagKey<Item> RING = TagKey.create(Registries.ITEM, Identifier.parse("trinkets:hand/ring"));
    public static final TagKey<Item> BELT = TagKey.create(Registries.ITEM, Identifier.parse("trinkets:legs/belt"));
    public static final TagKey<Item> BRACELET = TagKey.create(Registries.ITEM, Identifier.parse("trinkets:hand/bracelet"));
    public static final TagKey<Item> CHARM = TagKey.create(Registries.ITEM, Identifier.parse("trinkets:chest/charm"));
    public static final TagKey<Item> NECKLACE = TagKey.create(Registries.ITEM, Identifier.parse("trinkets:chest/necklace"));
    public static final TagKey<Item> HEAD = TagKey.create(Registries.ITEM, Identifier.parse("trinkets:head/hat"));
    public static final TagKey<Item> BODY = TagKey.create(Registries.ITEM, Identifier.parse("trinkets:chest/body"));
}
