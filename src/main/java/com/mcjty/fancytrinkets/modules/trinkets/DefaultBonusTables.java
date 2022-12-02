package com.mcjty.fancytrinkets.modules.trinkets;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.datapack.BonusTable;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultBonusTables {
    public static final Map<ResourceLocation, BonusTableInfo> DEFAULT_BONUS_TABLES = new HashMap<>();

    static void init() {
        DEFAULT_BONUS_TABLES.clear();
        register("standard", bonusTable(
                effect("wither", 0.0f),
                effect("poison", 0.0f),
                effect("nausea", 2.0f),
                effect("blindness", 4.0f),
                effect("mining_fatigue", 8.0f),
                effect("slowness", 8.0f),
                effect("dmg_generic_debuff", 10.0f),
                effect("max_health_debuff", 10.0f),
                effect("dmg_wither_debuff", 10.0f),
                effect("attack_damage_debuff", 15.0f),
                effect("dmg_fall_debuff", 15.0f),
                effect("attack_range_debuff", 15.0f),
                effect("reach_distance_debuff", 15.0f),
                effect("attack_speed_debuff", 20.0f),
                effect("dmg_magic_debuff", 20.0f),
                effect("swim_speed_debuff", 30.0f),

                effect("luck", 50.0f),
                effect("saturation", 60.0f),
                effect("dmg_magic_50", 60.0f),
                effect("dmg_wither_50", 60.0f),
                effect("dmg_fall_50", 60.0f),
                effect("night_vision", 70.0f),
                effect("nausea_resist", 70.0f),
                effect("blindness_resist", 70.0f),
                effect("dmg_magic_75", 70.0f),
                effect("dmg_fall_50", 70.0f),
                effect("swim_speed", 70.0f),
                effect("speed", 75.0f),
                effect("movement_speed", 75.0f),
                effect("knockback_resistance", 75.0f),
                effect("jump_boost", 75.0f),
                effect("water_breathing", 75.0f),
                effect("reach_distance", 80.0f),
                effect("dmg_generic_50", 80.0f),
                effect("dmg_wither_75", 80.0f),
                effect("dmg_fall_75", 80.0f),
                effect("poison_resist", 80.0f),
                effect("weakness_resist", 80.0f),
                effect("attack_speed", 85.0f),
                effect("regeneration", 85.0f),
                effect("fire_resistance", 85.0f),
                effect("minor_max_health", 85.0f),
                effect("attack_damage", 90.0f),
                effect("wither_resist", 90.0f),
                effect("strength", 90.0f),
                effect("absorption", 90.0f),
                effect("attack_range", 90.0f),
                effect("dmg_magic_100", 90.0f),
                effect("max_health", 90.0f),
                effect("dmg_generic_75", 95.0f),
                effect("health_boost", 95.0f),
                effect("dmg_wither_100", 95.0f),
                effect("major_max_health", 100.0f),
                effect("flight", 100.0f),
                effect("cure", 100.0f),
                effect("dmg_generic_100", 100.0f),
                effect("dmg_fall_100", 100.0f)
        ), "Standard");
    }

    private static BonusTable.EffectRef effect(String id, float quality) {
        return new BonusTable.EffectRef(new ResourceLocation(FancyTrinkets.MODID, id), quality);
    }

    private static void register(String id, BonusTable bonusTable, String name) {
        DEFAULT_BONUS_TABLES.put(new ResourceLocation(FancyTrinkets.MODID, id), new BonusTableInfo(bonusTable, name));
    }

    private static BonusTable bonusTable(BonusTable.EffectRef... effects) {
        return new BonusTable(List.of(effects));
    }

    public static record BonusTableInfo(BonusTable bonusTable, String name) {
    }
}
