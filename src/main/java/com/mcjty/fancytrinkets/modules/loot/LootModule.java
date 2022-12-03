package com.mcjty.fancytrinkets.modules.loot;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mojang.serialization.Codec;
import mcjty.lib.modules.IModule;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

import static com.mcjty.fancytrinkets.setup.Registration.ITEMS;
import static com.mcjty.fancytrinkets.setup.Registration.LOOT_MODIFIER_SERIALIZERS;

public class LootModule implements IModule {

    public static final Map<String, Essence> ESSENCE_ITEMS = new HashMap<>();

    public static final RegistryObject<Item> ZOMBIE_ESSENCE = createBasicItem("zombie_essence", "item/essence/zombie_essence", "Zombie Essence");

    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ZOMBIE_LOOT_MODIFIER = LOOT_MODIFIER_SERIALIZERS.register("zombie_extra", () -> EssenceLootModifier.CODEC);

    public LootModule() {
    }

    @Override
    public void init(FMLCommonSetupEvent event) {
    }

    @Override
    public void initClient(FMLClientSetupEvent event) {
    }

    @Override
    public void initConfig() {

    }

    public static record Essence(RegistryObject<Item> item, String texture, String description) {}

    @Nonnull
    private static RegistryObject<Item> createBasicItem(String id, String texture, String description) {
        RegistryObject<Item> object = ITEMS.register(id, () -> new Item(new Item.Properties().tab(FancyTrinkets.setup.getTab()).stacksTo(64)));
        ESSENCE_ITEMS.put(id, new Essence(object, texture, description));
        return object;
    }

}
