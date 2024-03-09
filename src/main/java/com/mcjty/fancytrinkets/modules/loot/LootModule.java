package com.mcjty.fancytrinkets.modules.loot;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mojang.serialization.Codec;
import mcjty.lib.datagen.DataGen;
import mcjty.lib.datagen.Dob;
import mcjty.lib.modules.IModule;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static com.mcjty.fancytrinkets.FancyTrinkets.tab;
import static com.mcjty.fancytrinkets.setup.Registration.ITEMS;
import static com.mcjty.fancytrinkets.setup.Registration.LOOT_MODIFIER_SERIALIZERS;

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

    public static final Supplier<Codec<? extends IGlobalLootModifier>> ESSENCE_LOOT_MODIFIER = LOOT_MODIFIER_SERIALIZERS.register("essence_loot", () -> EssenceLootModifier.CODEC);
    public static final Supplier<Codec<? extends IGlobalLootModifier>> TRINKET_LOOT_MODIFIER = LOOT_MODIFIER_SERIALIZERS.register("trinket_loot", () -> TrinketLootModifier.CODEC);

    public static final EssenceGLM ZOMBIE_LOOT_MODIFIER = createGlm("zombie_essence", EntityType.ZOMBIE, 0.1f, 1, 2, .3f);
    public static final EssenceGLM WITHER_SKELETON_LOOT_MODIFIER = createGlm("wither_skeleton_essence", EntityType.WITHER_SKELETON, 0.1f, 1, 2, .3f);
    public static final EssenceGLM WITHER_LOOT_MODIFIER = createGlm("wither_essence", EntityType.WITHER, 1.0f, 1, 2, .3f);
    public static final EssenceGLM SKELETON_LOOT_MODIFIER = createGlm("skeleton_essence", EntityType.SKELETON, 0.1f, 1, 2, .3f);
    public static final EssenceGLM DRAGON_LOOT_MODIFIER = createGlm("dragon_essence", EntityType.ENDER_DRAGON, 1.0f, 4, 7, .3f);
    public static final EssenceGLM ENDERMAN_LOOT_MODIFIER = createGlm("enderman_essence", EntityType.ENDERMAN, 0.1f, 1, 2, .3f);
    public static final EssenceGLM GHAST_LOOT_MODIFIER = createGlm("ghast_essence", EntityType.GHAST, 0.4f, 1, 2, .3f);
    public static final EssenceGLM SPIDER_LOOT_MODIFIER = createGlm("spider_essence", EntityType.SPIDER, 0.1f, 1, 1, .3f);
    public static final EssenceGLM CHICKEN_LOOT_MODIFIER = createGlm("chicken_essence", EntityType.CHICKEN, 0.1f, 1, 1, .3f);
    public static final EssenceGLM IRON_GOLEM_LOOT_MODIFIER = createGlm("iron_golem_essence", EntityType.IRON_GOLEM, 0.3f, 1, 2, .3f);
    public static final EssenceGLM BLAZE_LOOT_MODIFIER = createGlm("blaze_essence", EntityType.BLAZE, 0.3f, 1, 2, .3f);

    public LootModule() {
    }

    @Override
    public void init(FMLCommonSetupEvent event) {
    }

    @Override
    public void initClient(FMLClientSetupEvent event) {
    }

    @Override
    public void initConfig(IEventBus bus) {

    }

    @Override
    public void initDatagen(DataGen dataGen) {
        for (Map.Entry<String, LootModule.Essence> entry : LootModule.ESSENCE_ITEMS.entrySet()) {
            LootModule.Essence essence = entry.getValue();
            dataGen.add(
                    Dob.itemBuilder(essence.item())
                            .name(essence.description())
                            .generatedItem(essence.texture())
            );
        }

        for (Map.Entry<String, LootModule.EssenceGLM> entry : LootModule.ESSENCE_GLMS.entrySet()) {
            LootModule.EssenceGLM glm = entry.getValue();
            dataGen.add(
                    Dob.builder()
                            .glm(entry.getKey(), () -> new EssenceLootModifier(new LootItemCondition[]{
                                    LootTableIdCondition.builder(glm.lootTable()).build(),
                                    LootItemKilledByPlayerCondition.killedByPlayer().build()
                            }, glm.itemId(), glm.chance(), glm.min(), glm.max(), glm.looting()))
            );
        }

        dataGen.add(
                Dob.builder()
                        .glm("wither_trinket", () -> new TrinketLootModifier(new LootItemCondition[]{
                                LootTableIdCondition.builder(EntityType.WITHER.getDefaultLootTable()).build()
                        }, List.of(new ResourceLocation(FancyTrinkets.MODID, "regeneration_ring")), 0.5f, 1, 1, 0, 60, 70))
                        .glm("dragon_trinket", () -> new TrinketLootModifier(new LootItemCondition[]{
                                LootTableIdCondition.builder(EntityType.ENDER_DRAGON.getDefaultLootTable()).build()
                        }, List.of(new ResourceLocation(FancyTrinkets.MODID, "power_star")), 1.0f, 1, 1, 0, 90, 100))
                        .glm("enderman_trinket", () -> new TrinketLootModifier(new LootItemCondition[]{
                                LootTableIdCondition.builder(EntityType.ENDERMAN.getDefaultLootTable()).build()
                        }, List.of(new ResourceLocation(FancyTrinkets.MODID, "warp_pearl")), 0.02f, 1, 1, 0, 90, 100))
        );

        ResourceLocation[] goodChests = new ResourceLocation[]{
                BuiltInLootTables.END_CITY_TREASURE,
                BuiltInLootTables.STRONGHOLD_LIBRARY,
                BuiltInLootTables.DESERT_PYRAMID,
                BuiltInLootTables.WOODLAND_MANSION,
                BuiltInLootTables.BASTION_TREASURE,
                BuiltInLootTables.ANCIENT_CITY
        };
        for (ResourceLocation chest : goodChests) {
            dataGen.add(
                    Dob.builder()
                            .glm(chest.getPath() + "_trinket", () -> new TrinketLootModifier(new LootItemCondition[]{
                                    LootTableIdCondition.builder(chest).build()
                            }, Collections.emptyList(), 0.1f, 1, 1, 0, 20, 70))
            );
        }

        ResourceLocation[] otherChests = new ResourceLocation[]{
                BuiltInLootTables.SIMPLE_DUNGEON,
                BuiltInLootTables.VILLAGE_CARTOGRAPHER,
                BuiltInLootTables.VILLAGE_TEMPLE,
                BuiltInLootTables.ABANDONED_MINESHAFT,
                BuiltInLootTables.NETHER_BRIDGE,
                BuiltInLootTables.STRONGHOLD_CROSSING,
                BuiltInLootTables.STRONGHOLD_CORRIDOR,
                BuiltInLootTables.JUNGLE_TEMPLE,
                BuiltInLootTables.JUNGLE_TEMPLE_DISPENSER,
                BuiltInLootTables.IGLOO_CHEST,
                BuiltInLootTables.UNDERWATER_RUIN_SMALL,
                BuiltInLootTables.UNDERWATER_RUIN_BIG,
                BuiltInLootTables.BURIED_TREASURE,
                BuiltInLootTables.SHIPWRECK_MAP,
                BuiltInLootTables.SHIPWRECK_SUPPLY,
                BuiltInLootTables.SHIPWRECK_TREASURE,
                BuiltInLootTables.PILLAGER_OUTPOST,
                BuiltInLootTables.BASTION_OTHER,
                BuiltInLootTables.BASTION_BRIDGE,
                BuiltInLootTables.BASTION_HOGLIN_STABLE,
                BuiltInLootTables.ANCIENT_CITY_ICE_BOX,
                BuiltInLootTables.RUINED_PORTAL
        };
        for (ResourceLocation chest : otherChests) {
            dataGen.add(
                    Dob.builder()

                            .glm(chest.getPath() + "_trinket", () -> new TrinketLootModifier(new LootItemCondition[]{
                                    LootTableIdCondition.builder(chest).build()
                            }, Collections.emptyList(), 0.02f, 1, 1, 0, 5, 10))
            );
        }
    }

    public static record Essence(DeferredItem<Item> item, String texture, String description) {
    }

    public static record EssenceGLM(ResourceLocation itemId, ResourceLocation lootTable, float chance, int min, int max,
                                    float looting) {
    }

    @Nonnull
    private static DeferredItem<Item> createBasicItem(String id, String texture, String description) {
        DeferredItem<Item> object = ITEMS.register(id, tab(() -> new Item(FancyTrinkets.setup.defaultProperties().stacksTo(64))));
        ESSENCE_ITEMS.put(id, new Essence(object, texture, description));
        return object;
    }

    private static EssenceGLM createGlm(String id, EntityType<?> type, float chance, int min, int max, float looting) {
        EssenceGLM glm = new EssenceGLM(new ResourceLocation(FancyTrinkets.MODID, id),
                type.getDefaultLootTable(), chance, min, max, looting);
        ESSENCE_GLMS.put(id, glm);
        return glm;
    }

}
