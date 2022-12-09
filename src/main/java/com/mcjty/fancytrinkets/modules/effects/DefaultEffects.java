package com.mcjty.fancytrinkets.modules.effects;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.datapack.EffectDescription;
import com.mcjty.fancytrinkets.modules.effects.imp.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.HashMap;
import java.util.Map;

public class DefaultEffects {

    public static final Map<ResourceLocation, EffectInfo> DEFAULT_EFFECTS = new HashMap<>();

    static void init() {
        DEFAULT_EFFECTS.clear();

        // Negative effects
        register("weakness", mobEffect("minecraft:weakness", 1, true), "Weakness");
        register("blindness", mobEffect("minecraft:blindness", 1, true), "Blindness");
        register("slowness", mobEffect("minecraft:slowness", 1, true), "Slowness");
        register("mining_fatigue", mobEffect("minecraft:mining_fatigue", 1, true), "Mining Fatigue");
        register("nausea", mobEffect("minecraft:nausea", 1, true), "Nausea");
        register("hunger", mobEffect("minecraft:hunger", 1, true), "Hunger");
        register("poison", mobEffect("minecraft:poison", 1, true), "Poison");
        register("wither", mobEffect("minecraft:wither", 1, true), "Wither");
        register("bad_omen", mobEffect("minecraft:bad_omen", 1, true), "Bad Omen");

        register("dmg_infire_debuff", damageModificationEffect("inFire", 1.25f), "In Fire Damage Weakness (+25%)");
        register("dmg_lightningbolt_debuff", damageModificationEffect("lightningBolt", 1.25f), "Lightning Bolt Damage Weakness (+25%)");
        register("dmg_onfire_debuff", damageModificationEffect("onFire", 1.25f), "On Fire Damage Weakness (+25%)");
        register("dmg_lava_debuff", damageModificationEffect("lava", 1.25f), "Lava Damage Weakness (+25%)");
        register("dmg_hotfloor_debuff", damageModificationEffect("hotFloor", 1.25f), "Hot Floor Damage Weakness (+25%)");
        register("dmg_inwall_debuff", damageModificationEffect("inWall", 1.25f), "Suffocation Damage Weakness (+25%)");
        register("dmg_cramming_debuff", damageModificationEffect("cramming", 1.25f), "Cramming Damage Weakness (+25%)");
        register("dmg_drown_debuff", damageModificationEffect("drown", 1.25f), "Drowning Damage Weakness (+25%)");
        register("dmg_starve_debuff", damageModificationEffect("starve", 1.25f), "Starving Damage Weakness (+25%)");
        register("dmg_cactus_debuff", damageModificationEffect("cactus", 1.25f), "Cactus Damage Weakness (+25%)");
        register("dmg_fall_debuff", damageModificationEffect("fall", 1.25f), "Fall Damage Weakness (+25%)");
        register("dmg_flyintowall_debuff", damageModificationEffect("flyIntoWall", 1.25f), "Fly Into Wall Damage Weakness (+25%)");
        register("dmg_outofworld_debuff", damageModificationEffect("outOfWorld", 1.25f), "Out Of World Damage Weakness (+25%)");
        register("dmg_generic_debuff", damageModificationEffect("generic", 1.25f), "Generic Damage Weakness (+25%)");
        register("dmg_magic_debuff", damageModificationEffect("magic", 1.25f), "Magic Damage Weakness (+25%)");
        register("dmg_wither_debuff", damageModificationEffect("wither", 1.25f), "Wither Damage Weakness (+25%)");
        register("dmg_anvil_debuff", damageModificationEffect("anvil", 1.25f), "Anvil Damage Weakness (+25%)");
        register("dmg_fallingblock_debuff", damageModificationEffect("fallingBlock", 1.25f), "Falling Block Damage Weakness (+25%)");
        register("dmg_dragonbreath_debuff", damageModificationEffect("dragonBreath", 1.25f), "Dragon Breath Damage Weakness (+25%)");
        register("dmg_dryout_debuff", damageModificationEffect("dryout", 1.25f), "Dryout Damage Weakness (+25%)");
        register("dmg_sweetberrybush_debuff", damageModificationEffect("sweetBerryBush", 1.25f), "Sweet Berry Bush Damage Weakness (+25%)");
        register("dmg_freeze_debuff", damageModificationEffect("freeze", 1.25f), "Freeze Damage Weakness (+25%)");
        register("dmg_fallingstalactite_debuff", damageModificationEffect("fallingStalactite", 1.25f), "Falling Stalactite Damage Weakness (+25%)");
        register("dmg_stalagmite_debuff", damageModificationEffect("stalagmite", 1.25f), "Stalagmite Damage Weakness (+25%)");

        register("swim_speed_debuff", attributeEffect("swim_speed", AttributeModifier.Operation.MULTIPLY_TOTAL, .9), "Swim Speed Reduction (-10%)");
        register("attack_range_debuff", attributeEffect("attack_range", AttributeModifier.Operation.ADDITION, -.5), "Attack Range Reduction (-.5)");
        register("reach_distance_debuff", attributeEffect("reach_distance", AttributeModifier.Operation.ADDITION, -.5), "Reach Distance Reduction (-.5)");
        register("max_health_debuff", attributeEffect("max_health", AttributeModifier.Operation.ADDITION, -1), "Max Health Reduction (-1)");
        register("movement_speed_debuff", attributeEffect("movement_speed", AttributeModifier.Operation.MULTIPLY_TOTAL, .9), "Movement Speed Reduction (-10%)");
        register("attack_speed_debuff", attributeEffect("attack_speed", AttributeModifier.Operation.MULTIPLY_TOTAL, .8), "Attack Speed Reduction (-20%)");
        register("attack_damage_debuff", attributeEffect("attack_damage", AttributeModifier.Operation.MULTIPLY_TOTAL, .75), "Attack Damage Reduction (-25%)");

        // Positive effects
        register("regeneration", mobEffect("minecraft:regeneration", 1, false), "Regeneration");
        register("strength", mobEffect("minecraft:strength", 1, false), "Strength");
        register("absorption", mobEffect("minecraft:absorption", 1, false), "Absorption");
        register("damage_resistance", mobEffect("minecraft:resistance", 1, false), "Damage Resistance");
        register("haste", mobEffect("minecraft:haste", 1, false), "Haste");
        register("health_boost", mobEffect("minecraft:health_boost", 1, false), "Health Boost");
        register("invisibility", mobEffect("minecraft:invisibility", 1, false), "Invisibility");
        register("night_vision", mobEffect("minecraft:night_vision", 1, false), "Nightvision");
        register("night_vision_hotkey", mobEffect("minecraft:night_vision", 1, 1, "night_vision"), "Nightvision");
        register("slow_falling", mobEffect("minecraft:slow_falling", 1, false), "Slow Falling");
        register("speed", mobEffect("minecraft:speed", 1, false), "Speed");
        register("jump_boost", mobEffect("minecraft:jump_boost", 1, false), "Jump Boost");
        register("fire_resistance", mobEffect("minecraft:fire_resistance", 1, false), "Fire Resistance");
        register("water_breathing", mobEffect("minecraft:water_breathing", 1, false), "Water Breathing");
        register("saturation", mobEffect("minecraft:saturation", 1, false), "Saturation");
        register("levitation", mobEffect("minecraft:levitation", 1, false), "Levitation");
        register("luck_potion", mobEffect("minecraft:luck", 1, false), "Luck");
        register("conduit_power", mobEffect("minecraft:conduit_power", 1, false), "Conduit Power");

        register("flight", flightEffect(), "Flight");
        register("warp", warpEffect(2, 40), "Warp");
        register("cure", cureEffect(), "Cure");
        register("growtick", growtickEffect(), "Grow Tick Increase");

        register("poison_resist", potionResistEffect("minecraft:poison"), "Poison Resistance");
        register("weakness_resist", potionResistEffect("minecraft:weakness"), "Weakness Resistance");
        register("wither_resist", potionResistEffect("minecraft:wither"), "Wither Resistance");
        register("bad_omen_resist", potionResistEffect("minecraft:bad_omen"), "Bad Omen Resistance");
        register("nausea_resist", potionResistEffect("minecraft:nausea"), "Nausea Resistance");
        register("blindness_resist", potionResistEffect("minecraft:blindness"), "Blindness Resistance");

        register("dmg_infire_100", damageModificationEffect("inFire", 0.0f), "In Fire Damage Reduction (100%)");
        register("dmg_lightningbolt_100", damageModificationEffect("lightningBolt", 0.0f), "Lightning Bolt Damage Reduction (100%)");
        register("dmg_onfire_100", damageModificationEffect("onFire", 0.0f), "On Fire Damage Reduction (100%)");
        register("dmg_lava_100", damageModificationEffect("lava", 0.0f), "Lava Damage Reduction (100%)");
        register("dmg_hotfloor_100", damageModificationEffect("hotFloor", 0.0f), "Hot Floor Damage Reduction (100%)");
        register("dmg_inwall_100", damageModificationEffect("inWall", 0.0f), "Suffocation Damage Reduction (100%)");
        register("dmg_cramming_100", damageModificationEffect("cramming", 0.0f), "Cramming Damage Reduction (100%)");
        register("dmg_drown_100", damageModificationEffect("drown", 0.0f), "Drowning Damage Reduction (100%)");
        register("dmg_starve_100", damageModificationEffect("starve", 0.0f), "Starving Damage Reduction (100%)");
        register("dmg_cactus_100", damageModificationEffect("cactus", 0.0f), "Cactus Damage Reduction (100%)");
        register("dmg_fall_100", damageModificationEffect("fall", 0.0f), "Fall Damage Reduction (100%)");
        register("dmg_flyintowall_100", damageModificationEffect("flyIntoWall", 0.0f), "Fly Into Wall Damage Reduction (100%)");
        register("dmg_outofworld_100", damageModificationEffect("outOfWorld", 0.0f), "Out Of World Damage Reduction (100%)");
        register("dmg_generic_100", damageModificationEffect("generic", 0.0f), "Generic Damage Reduction (100%)");
        register("dmg_magic_100", damageModificationEffect("magic", 0.0f), "Magic Damage Reduction (100%)");
        register("dmg_wither_100", damageModificationEffect("wither", 0.0f), "Wither Damage Reduction (100%)");
        register("dmg_anvil_100", damageModificationEffect("anvil", 0.0f), "Anvil Damage Reduction (100%)");
        register("dmg_fallingblock_100", damageModificationEffect("fallingBlock", 0.0f), "Falling Block Damage Reduction (100%)");
        register("dmg_dragonbreath_100", damageModificationEffect("dragonBreath", 0.0f), "Dragon Breath Damage Reduction (100%)");
        register("dmg_dryout_100", damageModificationEffect("dryout", 0.0f), "Dryout Damage Reduction (100%)");
        register("dmg_sweetberrybush_100", damageModificationEffect("sweetBerryBush", 0.0f), "Sweet Berry Bush Damage Reduction (100%)");
        register("dmg_freeze_100", damageModificationEffect("freeze", 0.0f), "Freeze Damage Reduction (100%)");
        register("dmg_fallingstalactite_100", damageModificationEffect("fallingStalactite", 0.0f), "Falling Stalactite Damage Reduction (100%)");
        register("dmg_stalagmite_100", damageModificationEffect("stalagmite", 0.0f), "Stalagmite Damage Reduction (100%)");

        register("dmg_infire_75", damageModificationEffect("inFire", 0.25f), "In Fire Damage Reduction (75%)");
        register("dmg_lightningbolt_75", damageModificationEffect("lightningBolt", 0.25f), "Lightning Bolt Damage Reduction (75%)");
        register("dmg_onfire_75", damageModificationEffect("onFire", 0.25f), "On Fire Damage Reduction (75%)");
        register("dmg_lava_75", damageModificationEffect("lava", 0.25f), "Lava Damage Reduction (75%)");
        register("dmg_hotfloor_75", damageModificationEffect("hotFloor", 0.25f), "Hot Floor Damage Reduction (75%)");
        register("dmg_inwall_75", damageModificationEffect("inWall", 0.25f), "Suffocation Damage Reduction (75%)");
        register("dmg_cramming_75", damageModificationEffect("cramming", 0.25f), "Cramming Damage Reduction (75%)");
        register("dmg_drown_75", damageModificationEffect("drown", 0.25f), "Drowning Damage Reduction (75%)");
        register("dmg_starve_75", damageModificationEffect("starve", 0.25f), "Starving Damage Reduction (75%)");
        register("dmg_cactus_75", damageModificationEffect("cactus", 0.25f), "Cactus Damage Reduction (75%)");
        register("dmg_fall_75", damageModificationEffect("fall", 0.25f), "Fall Damage Reduction (75%)");
        register("dmg_flyintowall_75", damageModificationEffect("flyIntoWall", 0.25f), "Fly Into Wall Damage Reduction (75%)");
        register("dmg_outofworld_75", damageModificationEffect("outOfWorld", 0.25f), "Out Of World Damage Reduction (75%)");
        register("dmg_generic_75", damageModificationEffect("generic", 0.25f), "Generic Damage Reduction (75%)");
        register("dmg_magic_75", damageModificationEffect("magic", 0.25f), "Magic Damage Reduction (75%)");
        register("dmg_wither_75", damageModificationEffect("wither", 0.25f), "Wither Damage Reduction (75%)");
        register("dmg_anvil_75", damageModificationEffect("anvil", 0.25f), "Anvil Damage Reduction (75%)");
        register("dmg_fallingblock_75", damageModificationEffect("fallingBlock", 0.25f), "Falling Block Damage Reduction (75%)");
        register("dmg_dragonbreath_75", damageModificationEffect("dragonBreath", 0.25f), "Dragon Breath Damage Reduction (75%)");
        register("dmg_dryout_75", damageModificationEffect("dryout", 0.25f), "Dryout Damage Reduction (75%)");
        register("dmg_sweetberrybush_75", damageModificationEffect("sweetBerryBush", 0.25f), "Sweet Berry Bush Damage Reduction (75%)");
        register("dmg_freeze_75", damageModificationEffect("freeze", 0.25f), "Freeze Damage Reduction (75%)");
        register("dmg_fallingstalactite_75", damageModificationEffect("fallingStalactite", 0.25f), "Falling Stalactite Damage Reduction (75%)");
        register("dmg_stalagmite_75", damageModificationEffect("stalagmite", 0.25f), "Stalagmite Damage Reduction (75%)");

        register("dmg_infire_50", damageModificationEffect("inFire", 0.5f), "In Fire Damage Reduction (50%)");
        register("dmg_lightningbolt_50", damageModificationEffect("lightningBolt", 0.5f), "Lightning Bolt Damage Reduction (50%)");
        register("dmg_onfire_50", damageModificationEffect("onFire", 0.5f), "On Fire Damage Reduction (50%)");
        register("dmg_lava_50", damageModificationEffect("lava", 0.5f), "Lava Damage Reduction (50%)");
        register("dmg_hotfloor_50", damageModificationEffect("hotFloor", 0.5f), "Hot Floor Damage Reduction (50%)");
        register("dmg_inwall_50", damageModificationEffect("inWall", 0.5f), "Suffocation Damage Reduction (50%)");
        register("dmg_cramming_50", damageModificationEffect("cramming", 0.5f), "Cramming Damage Reduction (50%)");
        register("dmg_drown_50", damageModificationEffect("drown", 0.5f), "Drowning Damage Reduction (50%)");
        register("dmg_starve_50", damageModificationEffect("starve", 0.5f), "Starving Damage Reduction (50%)");
        register("dmg_cactus_50", damageModificationEffect("cactus", 0.5f), "Cactus Damage Reduction (50%)");
        register("dmg_fall_50", damageModificationEffect("fall", 0.5f), "Fall Damage Reduction (50%)");
        register("dmg_flyintowall_50", damageModificationEffect("flyIntoWall", 0.5f), "Fly Into Wall Damage Reduction (50%)");
        register("dmg_outofworld_50", damageModificationEffect("outOfWorld", 0.5f), "Out Of World Damage Reduction (50%)");
        register("dmg_generic_50", damageModificationEffect("generic", 0.5f), "Generic Damage Reduction (50%)");
        register("dmg_magic_50", damageModificationEffect("magic", 0.5f), "Magic Damage Reduction (50%)");
        register("dmg_wither_50", damageModificationEffect("wither", 0.5f), "Wither Damage Reduction (50%)");
        register("dmg_anvil_50", damageModificationEffect("anvil", 0.5f), "Anvil Damage Reduction (50%)");
        register("dmg_fallingblock_50", damageModificationEffect("fallingBlock", 0.5f), "Falling Block Damage Reduction (50%)");
        register("dmg_dragonbreath_50", damageModificationEffect("dragonBreath", 0.5f), "Dragon Breath Damage Reduction (50%)");
        register("dmg_dryout_50", damageModificationEffect("dryout", 0.5f), "Dryout Damage Reduction (50%)");
        register("dmg_sweetberrybush_50", damageModificationEffect("sweetBerryBush", 0.5f), "Sweet Berry Bush Damage Reduction (50%)");
        register("dmg_freeze_50", damageModificationEffect("freeze", 0.5f), "Freeze Damage Reduction (50%)");
        register("dmg_fallingstalactite_50", damageModificationEffect("fallingStalactite", 0.5f), "Falling Stalactite Damage Reduction (50%)");
        register("dmg_stalagmite_50", damageModificationEffect("stalagmite", 0.5f), "Stalagmite Damage Reduction (50%)");

        register("step_assist", attributeEffect("step_assist", AttributeModifier.Operation.ADDITION, .5), "Step assist");
        register("swim_speed", attributeEffect("swim_speed", AttributeModifier.Operation.MULTIPLY_TOTAL, 2), "Swim speed");
        register("attack_range", attributeEffect("attack_range", AttributeModifier.Operation.ADDITION, 1), "Attack Range");
        register("reach_distance", attributeEffect("reach_distance", AttributeModifier.Operation.ADDITION, 2), "Reach Distance");
        register("minor_max_health", attributeEffect("max_health", AttributeModifier.Operation.ADDITION, 2), "Minor Max Health");
        register("max_health", attributeEffect("max_health", AttributeModifier.Operation.ADDITION, 4), "Max Health");
        register("major_max_health", attributeEffect("max_health", AttributeModifier.Operation.ADDITION, 8), "Major Max Health");
        register("knockback_resistance", attributeEffect("knockback_resistance", AttributeModifier.Operation.MULTIPLY_TOTAL, 2), "Knockback Resistance");
        register("movement_speed", attributeEffect("movement_speed", AttributeModifier.Operation.MULTIPLY_TOTAL, 2), "Movement Speed");
        register("attack_speed", attributeEffect("attack_speed", AttributeModifier.Operation.MULTIPLY_TOTAL, 2), "Attack Speed");
        register("attack_damage", attributeEffect("attack_damage", AttributeModifier.Operation.MULTIPLY_TOTAL, 2), "Attack Damage");
        register("luck", attributeEffect("luck", AttributeModifier.Operation.MULTIPLY_TOTAL, 2), "Luck");
    }

    private static void register(String id, EffectDescription effect, String description) {
        DEFAULT_EFFECTS.put(new ResourceLocation(FancyTrinkets.MODID, id), new EffectInfo(effect, description));
    }

    private static EffectDescription damageModificationEffect(String dmgId, float factor) {
        return EffectDescription.create(null, null, factor > 1, new DamageReductionEffect.Params(dmgId, factor));
    }

    private static EffectDescription potionResistEffect(String effect) {
        return EffectDescription.create(null, null, false, new PotionResistanceEffect.Params(effect));
    }

    private static EffectDescription mobEffect(String effect, int level, boolean harmful) {
        return EffectDescription.create(null, null, harmful, new MobEffectEffect.Params(effect, level));
    }

    private static EffectDescription mobEffect(String effect, int level, Integer hotkey, String toggle) {
        return EffectDescription.create(hotkey, toggle, false, new MobEffectEffect.Params(effect, level));
    }

    private static EffectDescription attributeEffect(String effect, AttributeModifier.Operation operation, double amount) {
        boolean harmful = false;
        if (operation == AttributeModifier.Operation.MULTIPLY_TOTAL && amount < 1) {
            harmful = true;
        } else if (operation == AttributeModifier.Operation.ADDITION && amount < 0) {
            harmful = true;
        }
        return EffectDescription.create(null, null, harmful, new AttributeModifierEffect.Params(effect, operation, amount));
    }

    private static EffectDescription flightEffect() {
        return EffectDescription.create(null, null, false, FlightEffect.Params.EMPTY);
    }

    private static EffectDescription warpEffect(int hotkey, int maxdist) {
        return EffectDescription.create(hotkey, null, false, new WarpEffect.Params(maxdist));
    }

    private static EffectDescription cureEffect() {
        return EffectDescription.create(null, null, false, CureEffect.Params.EMPTY);
    }

    private static EffectDescription growtickEffect() {
        return EffectDescription.create(null, null, false, new GrowTickEffect.Params(6, 15));
    }

    public static record EffectInfo(EffectDescription effect, String description) {
    }
}
