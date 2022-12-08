package com.mcjty.fancytrinkets.datagen;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.modules.loot.EssenceLootModifier;
import com.mcjty.fancytrinkets.modules.loot.LootModule;
import com.mcjty.fancytrinkets.modules.loot.TrinketLootModifier;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GenGLM extends GlobalLootModifierProvider {

    public GenGLM(DataGenerator gen) {
        super(gen, FancyTrinkets.MODID);
    }

    @Override
    protected void start() {
        for (Map.Entry<String, LootModule.EssenceGLM> entry : LootModule.ESSENCE_GLMS.entrySet()) {
            LootModule.EssenceGLM glm = entry.getValue();
            add(entry.getKey(), EssenceLootModifier.Serializer.INSTANCE, new EssenceLootModifier(new LootItemCondition[]{
                    LootTableIdCondition.builder(glm.lootTable()).build(),
                    LootItemKilledByPlayerCondition.killedByPlayer().build()
            }, glm.itemId(), glm.chance(), glm.min(), glm.max(), glm.looting()));
        }

        add("wither_trinket", TrinketLootModifier.Serializer.INSTANCE, new TrinketLootModifier(new LootItemCondition[]{
                LootTableIdCondition.builder(EntityType.WITHER.getDefaultLootTable()).build()
        }, List.of(new ResourceLocation(FancyTrinkets.MODID, "regeneration_ring")), 0.5f, 1, 1, 0, 60, 70));

        add("dragon_trinket", TrinketLootModifier.Serializer.INSTANCE, new TrinketLootModifier(new LootItemCondition[]{
                LootTableIdCondition.builder(EntityType.ENDER_DRAGON.getDefaultLootTable()).build()
        }, List.of(new ResourceLocation(FancyTrinkets.MODID, "power_star")), 1.0f, 1, 1, 0, 90, 100));

        add("enderman_trinket", TrinketLootModifier.Serializer.INSTANCE, new TrinketLootModifier(new LootItemCondition[]{
                LootTableIdCondition.builder(EntityType.ENDERMAN.getDefaultLootTable()).build()
        }, List.of(new ResourceLocation(FancyTrinkets.MODID, "warp_pearl")), 0.02f, 1, 1, 0, 90, 100));

        ResourceLocation[] goodChests = new ResourceLocation[]{
                BuiltInLootTables.END_CITY_TREASURE,
                BuiltInLootTables.STRONGHOLD_LIBRARY,
                BuiltInLootTables.DESERT_PYRAMID,
                BuiltInLootTables.WOODLAND_MANSION,
                BuiltInLootTables.BASTION_TREASURE
        };
        for (ResourceLocation chest : goodChests) {
            add(chest.getPath() + "_trinket", TrinketLootModifier.Serializer.INSTANCE, new TrinketLootModifier(new LootItemCondition[]{
                    LootTableIdCondition.builder(chest).build()
            }, Collections.emptyList(), 0.1f, 1, 1, 0, 20, 70));
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
                BuiltInLootTables.RUINED_PORTAL
        };
        for (ResourceLocation chest : otherChests) {
            add(chest.getPath() + "_trinket", TrinketLootModifier.Serializer.INSTANCE, new TrinketLootModifier(new LootItemCondition[]{
                    LootTableIdCondition.builder(chest).build()
            }, Collections.emptyList(), 0.02f, 1, 1, 0, 5, 10));
        }

    }
}
