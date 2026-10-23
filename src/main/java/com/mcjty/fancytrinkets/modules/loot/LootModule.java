package com.mcjty.fancytrinkets.modules.loot;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mojang.serialization.MapCodec;
import mcjty.lib.datagen.DataGen;
import mcjty.lib.datagen.Dob;
import mcjty.lib.modules.IModule;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import mcjty.lib.setup.DeferredItem;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static com.mcjty.fancytrinkets.setup.Registration.ITEMS;

public class LootModule implements IModule {

    public static final Map<String, Essence> ESSENCE_ITEMS = new HashMap<>();
    public static final Map<String, EssenceGLM> ESSENCE_GLMS = new HashMap<>();

    public static final DeferredItem<Item> ZOMBIE_ESSENCE = createBasicItem("zombie_essence", "item/essence/zombie_essence", "Zombie Essence");
    public static final DeferredItem<Item> WITHER_SKELETON_ESSENCE = createBasicItem("wither_skeleton_essence", "item/essence/wither_skeleton_essence", "Wither Skeleton Essence");
    public static final DeferredItem<Item> WITHER_ESSENCE = createBasicItem("wither_essence", "item/essence/wither_essence", "Wither Essence");
    public static final DeferredItem<Item> SKELETON_ESSENCE = createBasicItem("skeleton_essence", "item/essence/skeleton_essence", "Skeleton Essence");
    public static final DeferredItem<Item> DRAGON_ESSENCE = createBasicItem("dragon_essence", "item/essence/dragon_essence", "Enderdragon Essence");
    public static final DeferredItem<Item> ENDERMAN_ESSENCE = createBasicItem("enderman_essence", "item/essence/enderman_essence", "Enderman Essence");
    public static final DeferredItem<Item> GHAST_ESSENCE = createBasicItem("ghast_essence", "item/essence/ghast_essence", "Ghast Essence");
    public static final DeferredItem<Item> SPIDER_ESSENCE = createBasicItem("spider_essence", "item/essence/spider_essence", "Spider Essence");
    public static final DeferredItem<Item> CHICKEN_ESSENCE = createBasicItem("chicken_essence", "item/essence/chicken_essence", "Chicken Essence");
    public static final DeferredItem<Item> IRON_GOLEM_ESSENCE = createBasicItem("iron_golem_essence", "item/essence/iron_golem_essence", "Iron Golem Essence");
    public static final DeferredItem<Item> BLAZE_ESSENCE = createBasicItem("blaze_essence", "item/essence/blaze_essence", "Blaze Essence");


    public static final EssenceGLM ZOMBIE_LOOT_MODIFIER = createGlm("zombie_essence", EntityTypes.ZOMBIE, 0.1f, 1, 2, .3f);
    public static final EssenceGLM WITHER_SKELETON_LOOT_MODIFIER = createGlm("wither_skeleton_essence", EntityTypes.WITHER_SKELETON, 0.1f, 1, 2, .3f);
    public static final EssenceGLM WITHER_LOOT_MODIFIER = createGlm("wither_essence", EntityTypes.WITHER, 1.0f, 1, 2, .3f);
    public static final EssenceGLM SKELETON_LOOT_MODIFIER = createGlm("skeleton_essence", EntityTypes.SKELETON, 0.1f, 1, 2, .3f);
    public static final EssenceGLM DRAGON_LOOT_MODIFIER = createGlm("dragon_essence", EntityTypes.ENDER_DRAGON, 1.0f, 4, 7, .3f);
    public static final EssenceGLM ENDERMAN_LOOT_MODIFIER = createGlm("enderman_essence", EntityTypes.ENDERMAN, 0.1f, 1, 2, .3f);
    public static final EssenceGLM GHAST_LOOT_MODIFIER = createGlm("ghast_essence", EntityTypes.GHAST, 0.4f, 1, 2, .3f);
    public static final EssenceGLM SPIDER_LOOT_MODIFIER = createGlm("spider_essence", EntityTypes.SPIDER, 0.1f, 1, 1, .3f);
    public static final EssenceGLM CHICKEN_LOOT_MODIFIER = createGlm("chicken_essence", EntityTypes.CHICKEN, 0.1f, 1, 1, .3f);
    public static final EssenceGLM IRON_GOLEM_LOOT_MODIFIER = createGlm("iron_golem_essence", EntityTypes.IRON_GOLEM, 0.3f, 1, 2, .3f);
    public static final EssenceGLM BLAZE_LOOT_MODIFIER = createGlm("blaze_essence", EntityTypes.BLAZE, 0.3f, 1, 2, .3f);

    public LootModule() {
    }

    @Override
    public void init() {
        TrinketDrops.register();
    }

    @Override
    public void initClient() {
    }

    @Override
    public void initConfig() {

    }

    @Override
    public void initDatagen(DataGen dataGen, HolderLookup.Provider lookupProvider) {
        for (Map.Entry<String, LootModule.Essence> entry : LootModule.ESSENCE_ITEMS.entrySet()) {
            LootModule.Essence essence = entry.getValue();
            dataGen.add(
                    Dob.itemBuilder(essence.item())
                            .name(essence.description())
                            .generatedItem(FancyTrinkets.MODID + ":" + essence.texture())
            );
        }

        Map<Identifier, DropModifier> modifiers = new HashMap<>();
        ESSENCE_GLMS.forEach((id, glm) -> modifiers.put(id(id), new EssenceLootModifier(glm.lootTable(),
                List.of(LootItemKilledByPlayerCondition.killedByPlayer().build()), glm.itemId(), glm.chance(), glm.min(), glm.max(), glm.looting())));
        modifiers.put(id("wither_trinket"), new TrinketLootModifier(EntityTypes.WITHER.getDefaultLootTable().orElseThrow().identifier(), List.of(), List.of(id("regeneration_ring")), .5f, 1, 1, 0, 60, 70));
        modifiers.put(id("dragon_trinket"), new TrinketLootModifier(EntityTypes.ENDER_DRAGON.getDefaultLootTable().orElseThrow().identifier(), List.of(), List.of(id("power_star")), 1, 1, 1, 0, 90, 100));
        modifiers.put(id("enderman_trinket"), new TrinketLootModifier(EntityTypes.ENDERMAN.getDefaultLootTable().orElseThrow().identifier(), List.of(), List.of(id("warp_pearl")), .02f, 1, 1, 0, 90, 100));
        Identifier[] goodChests = new Identifier[]{
                BuiltInLootTables.END_CITY_TREASURE.identifier(),
                BuiltInLootTables.STRONGHOLD_LIBRARY.identifier(),
                BuiltInLootTables.DESERT_PYRAMID.identifier(),
                BuiltInLootTables.WOODLAND_MANSION.identifier(),
                BuiltInLootTables.BASTION_TREASURE.identifier(),
                BuiltInLootTables.ANCIENT_CITY.identifier()
        };
        for (Identifier chest : goodChests) modifiers.put(id(chest.getPath() + "_trinket"), new TrinketLootModifier(chest, List.of(), List.of(), .1f, 1, 1, 0, 20, 70));
        Identifier[] otherChests = new Identifier[]{
                BuiltInLootTables.SIMPLE_DUNGEON.identifier(),
                BuiltInLootTables.VILLAGE_CARTOGRAPHER.identifier(),
                BuiltInLootTables.VILLAGE_TEMPLE.identifier(),
                BuiltInLootTables.ABANDONED_MINESHAFT.identifier(),
                BuiltInLootTables.NETHER_BRIDGE.identifier(),
                BuiltInLootTables.STRONGHOLD_CROSSING.identifier(),
                BuiltInLootTables.STRONGHOLD_CORRIDOR.identifier(),
                BuiltInLootTables.JUNGLE_TEMPLE.identifier(),
                BuiltInLootTables.JUNGLE_TEMPLE_DISPENSER.identifier(),
                BuiltInLootTables.IGLOO_CHEST.identifier(),
                BuiltInLootTables.UNDERWATER_RUIN_SMALL.identifier(),
                BuiltInLootTables.UNDERWATER_RUIN_BIG.identifier(),
                BuiltInLootTables.BURIED_TREASURE.identifier(),
                BuiltInLootTables.SHIPWRECK_MAP.identifier(),
                BuiltInLootTables.SHIPWRECK_SUPPLY.identifier(),
                BuiltInLootTables.SHIPWRECK_TREASURE.identifier(),
                BuiltInLootTables.PILLAGER_OUTPOST.identifier(),
                BuiltInLootTables.BASTION_OTHER.identifier(),
                BuiltInLootTables.BASTION_BRIDGE.identifier(),
                BuiltInLootTables.BASTION_HOGLIN_STABLE.identifier(),
                BuiltInLootTables.ANCIENT_CITY_ICE_BOX.identifier(),
                BuiltInLootTables.RUINED_PORTAL.identifier()
        };
        for (Identifier chest : otherChests) modifiers.put(id(chest.getPath() + "_trinket"), new TrinketLootModifier(chest, List.of(), List.of(), .02f, 1, 1, 0, 5, 10));
        dataGen.addCodecProvider("drops", "fancytrinkets/loot_modifiers", DropModifier.CODEC);
        dataGen.add(Dob.builder().holderLookup("drops", lookupProvider).codecObjectSupplier("drops", () -> new java.util.HashMap<Identifier, Object>(modifiers)));
    }
    private static Identifier id(String name) { return Identifier.fromNamespaceAndPath(FancyTrinkets.MODID, name); }

    public static record Essence(DeferredItem<Item> item, String texture, String description) {
    }

    public static record EssenceGLM(Identifier itemId, Identifier lootTable, float chance, int min, int max,
                                    float looting) {
    }

    @Nonnull
    private static DeferredItem<Item> createBasicItem(String id, String texture, String description) {
        DeferredItem<Item> object = ITEMS.register(id, properties -> new Item(properties.stacksTo(64)));
        FancyTrinkets.setup.addTabItem(() -> new ItemStack(object.get()));
        ESSENCE_ITEMS.put(id, new Essence(object, texture, description));
        return object;
    }

    private static EssenceGLM createGlm(String id, EntityType<?> type, float chance, int min, int max, float looting) {
        EssenceGLM glm = new EssenceGLM(Identifier.fromNamespaceAndPath(FancyTrinkets.MODID, id),
                type.getDefaultLootTable().orElseThrow().identifier(), chance, min, max, looting);
        ESSENCE_GLMS.put(id, glm);
        return glm;
    }

}
