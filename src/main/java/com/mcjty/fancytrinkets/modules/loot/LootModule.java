package com.mcjty.fancytrinkets.modules.loot;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mojang.serialization.Codec;
import mcjty.lib.modules.IModule;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
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
    public static final Map<String, EssenceGLM> ESSENCE_GLMS = new HashMap<>();

    public static final RegistryObject<Item> ZOMBIE_ESSENCE = createBasicItem("zombie_essence", "item/essence/zombie_essence", "Zombie Essence");
    public static final RegistryObject<Item> WITHER_SKELETON_ESSENCE = createBasicItem("wither_skeleton_essence", "item/essence/wither_skeleton_essence", "Wither Skeleton Essence");
    public static final RegistryObject<Item> WITHER_ESSENCE = createBasicItem("wither_essence", "item/essence/wither_essence", "Wither Essence");
    public static final RegistryObject<Item> SKELETON_ESSENCE = createBasicItem("skeleton_essence", "item/essence/skeleton_essence", "Skeleton Essence");
    public static final RegistryObject<Item> DRAGON_ESSENCE = createBasicItem("dragon_essence", "item/essence/dragon_essence", "Enderdragon Essence");
    public static final RegistryObject<Item> ENDERMAN_ESSENCE = createBasicItem("enderman_essence", "item/essence/enderman_essence", "Enderman Essence");
    public static final RegistryObject<Item> GHAST_ESSENCE = createBasicItem("ghast_essence", "item/essence/ghast_essence", "Ghast Essence");

    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ESSENCE_LOOT_MODIFIER = LOOT_MODIFIER_SERIALIZERS.register("essence_loot", () -> EssenceLootModifier.CODEC);
    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> TRINKET_LOOT_MODIFIER = LOOT_MODIFIER_SERIALIZERS.register("trinket_loot", () -> TrinketLootModifier.CODEC);

    public static final EssenceGLM ZOMBIE_LOOT_MODIFIER = createGlm("zombie_essence", EntityType.ZOMBIE, 0.1f, 1, 2, .3f);
    public static final EssenceGLM WITHER_SKELETON_LOOT_MODIFIER = createGlm("wither_skeleton_essence", EntityType.WITHER_SKELETON, 0.1f, 1, 2, .3f);
    public static final EssenceGLM WITHER_LOOT_MODIFIER = createGlm("wither_essence", EntityType.WITHER, 1.0f, 1, 1, .3f);
    public static final EssenceGLM SKELETON_LOOT_MODIFIER = createGlm("skeleton_essence", EntityType.SKELETON, 0.1f, 1, 2, .3f);
    public static final EssenceGLM DRAGON_LOOT_MODIFIER = createGlm("dragon_essence", EntityType.ENDER_DRAGON, 1.0f, 1, 1, .3f);
    public static final EssenceGLM ENDERMAN_LOOT_MODIFIER = createGlm("enderman_essence", EntityType.ENDERMAN, 0.1f, 1, 2, .3f);
    public static final EssenceGLM GHAST_LOOT_MODIFIER = createGlm("ghast_essence", EntityType.GHAST, 0.4f, 1, 2, .3f);

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
    public static record EssenceGLM(ResourceLocation itemId, EntityType<?> type, float chance, int min, int max, float looting) {}

    @Nonnull
    private static RegistryObject<Item> createBasicItem(String id, String texture, String description) {
        RegistryObject<Item> object = ITEMS.register(id, () -> new Item(new Item.Properties().tab(FancyTrinkets.setup.getTab()).stacksTo(64)));
        ESSENCE_ITEMS.put(id, new Essence(object, texture, description));
        return object;
    }

    private static EssenceGLM createGlm(String id, EntityType<?> type, float chance, int min, int max, float looting) {
        EssenceGLM glm = new EssenceGLM(new ResourceLocation(FancyTrinkets.MODID, id), type, chance, min, max, looting);
        ESSENCE_GLMS.put(id, glm);
        return glm;
    }

}
