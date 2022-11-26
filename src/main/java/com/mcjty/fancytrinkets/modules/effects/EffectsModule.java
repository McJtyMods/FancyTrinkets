package com.mcjty.fancytrinkets.modules.effects;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.datapack.EffectDescription;
import com.mcjty.fancytrinkets.modules.effects.imp.*;
import mcjty.lib.modules.IModule;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.HashMap;
import java.util.Map;

public class EffectsModule implements IModule {

    public EffectsModule() {
        EFFECTS.clear();
        register("regeneration", mobEffect("minecraft:regeneration", 1), "Regeneration");
        register("strength", mobEffect("minecraft:strength", 1), "Strength");
        register("absorption", mobEffect("minecraft:absorption", 1), "Absorption");
        register("damage_resistance", mobEffect("minecraft:resistance", 1), "Damage Resistance");
        register("haste", mobEffect("minecraft:haste", 1), "Haste");
        register("health_boost", mobEffect("minecraft:health_boost", 1), "Health Boost");
        register("invisibility", mobEffect("minecraft:invisibility", 1), "Invisibility");
        register("night_vision", mobEffect("minecraft:night_vision", 1), "Nightvision");
        register("night_vision_hotkey", mobEffect("minecraft:night_vision", 1, 1, "night_vision"), "Nightvision");
        register("slow_falling", mobEffect("minecraft:slow_falling", 1), "Slow Falling");
        register("speed", mobEffect("minecraft:speed", 1), "Speed");
        register("jump_boost", mobEffect("minecraft:jump_boost", 1), "Jump Boost");
        register("fire_resistance", mobEffect("minecraft:fire_resistance", 1), "Fire Resistance");
        register("water_breathing", mobEffect("minecraft:water_breathing", 1), "Water Breathing");
        register("saturation", mobEffect("minecraft:saturation", 1), "Saturation");
        register("levitation", mobEffect("minecraft:levitation", 1), "Levitation");
        register("luck_potion", mobEffect("minecraft:luck", 1), "Luck");
        register("conduit_power", mobEffect("minecraft:conduit_power", 1), "Conduit Power");

        register("flight", flightEffect(), "Flight");
        register("cure", cureEffect(), "Cure");

        register("poison_resist", potionResistEffect("minecraft:poison"), "Poison Resistance");
        register("weakness_resist", potionResistEffect("minecraft:weakness"), "Weakness Resistance");
        register("wither_resist", potionResistEffect("minecraft:wither"), "Wither Resistance");
        register("bad_omen_resist", potionResistEffect("minecraft:bad_omen"), "Bad Omen Resistance");
        register("nausea_resist", potionResistEffect("minecraft:nausea"), "Nausea Resistance");
        register("blindness_resist", potionResistEffect("minecraft:blindness"), "Blindness Resistance");

        register("dmg_infire_100", damageReductionEffect("inFire", 0.0f), "In Fire Damage Reduction (100%)");
        register("dmg_lightningbolt_100", damageReductionEffect("lightningBolt", 0.0f), "Lightning Bolt Damage Reduction (100%)");
        register("dmg_onfire_100", damageReductionEffect("onFire", 0.0f), "On Fire Damage Reduction (100%)");
        register("dmg_lava_100", damageReductionEffect("lava", 0.0f), "Lava Damage Reduction (100%)");
        register("dmg_hotfloor_100", damageReductionEffect("hotFloor", 0.0f), "Hot Floor Damage Reduction (100%)");
        register("dmg_inwall_100", damageReductionEffect("inWall", 0.0f), "Suffocation Damage Reduction (100%)");
        register("dmg_cramming_100", damageReductionEffect("cramming", 0.0f), "Cramming Damage Reduction (100%)");
        register("dmg_drown_100", damageReductionEffect("drown", 0.0f), "Drowning Damage Reduction (100%)");
        register("dmg_starve_100", damageReductionEffect("starve", 0.0f), "Starving Damage Reduction (100%)");
        register("dmg_cactus_100", damageReductionEffect("cactus", 0.0f), "Cactus Damage Reduction (100%)");
        register("dmg_fall_100", damageReductionEffect("fall", 0.0f), "Fall Damage Reduction (100%)");
        register("dmg_flyintowall_100", damageReductionEffect("flyIntoWall", 0.0f), "Fly Into Wall Damage Reduction (100%)");
        register("dmg_outofworld_100", damageReductionEffect("outOfWorld", 0.0f), "Out Of World Damage Reduction (100%)");
        register("dmg_generic_100", damageReductionEffect("generic", 0.0f), "Generic Damage Reduction (100%)");
        register("dmg_magic_100", damageReductionEffect("magic", 0.0f), "Magic Damage Reduction (100%)");
        register("dmg_wither_100", damageReductionEffect("wither", 0.0f), "Wither Damage Reduction (100%)");
        register("dmg_anvil_100", damageReductionEffect("anvil", 0.0f), "Anvil Damage Reduction (100%)");
        register("dmg_fallingblock_100", damageReductionEffect("fallingBlock", 0.0f), "Falling Block Damage Reduction (100%)");
        register("dmg_dragonbreath_100", damageReductionEffect("dragonBreath", 0.0f), "Dragon Breath Damage Reduction (100%)");
        register("dmg_dryout_100", damageReductionEffect("dryout", 0.0f), "Dryout Damage Reduction (100%)");
        register("dmg_sweetberrybush_100", damageReductionEffect("sweetBerryBush", 0.0f), "Sweet Berry Bush Damage Reduction (100%)");
        register("dmg_freeze_100", damageReductionEffect("freeze", 0.0f), "Freeze Damage Reduction (100%)");
        register("dmg_fallingstalactite_100", damageReductionEffect("fallingStalactite", 0.0f), "Falling Stalactite Damage Reduction (100%)");
        register("dmg_stalagmite_100", damageReductionEffect("stalagmite", 0.0f), "Stalagmite Damage Reduction (100%)");

        register("dmg_infire_75", damageReductionEffect("inFire", 0.25f), "In Fire Damage Reduction (75%)");
        register("dmg_lightningbolt_75", damageReductionEffect("lightningBolt", 0.25f), "Lightning Bolt Damage Reduction (75%)");
        register("dmg_onfire_75", damageReductionEffect("onFire", 0.25f), "On Fire Damage Reduction (75%)");
        register("dmg_lava_75", damageReductionEffect("lava", 0.25f), "Lava Damage Reduction (75%)");
        register("dmg_hotfloor_75", damageReductionEffect("hotFloor", 0.25f), "Hot Floor Damage Reduction (75%)");
        register("dmg_inwall_75", damageReductionEffect("inWall", 0.25f), "Suffocation Damage Reduction (75%)");
        register("dmg_cramming_75", damageReductionEffect("cramming", 0.25f), "Cramming Damage Reduction (75%)");
        register("dmg_drown_75", damageReductionEffect("drown", 0.25f), "Drowning Damage Reduction (75%)");
        register("dmg_starve_75", damageReductionEffect("starve", 0.25f), "Starving Damage Reduction (75%)");
        register("dmg_cactus_75", damageReductionEffect("cactus", 0.25f), "Cactus Damage Reduction (75%)");
        register("dmg_fall_75", damageReductionEffect("fall", 0.25f), "Fall Damage Reduction (75%)");
        register("dmg_flyintowall_75", damageReductionEffect("flyIntoWall", 0.25f), "Fly Into Wall Damage Reduction (75%)");
        register("dmg_outofworld_75", damageReductionEffect("outOfWorld", 0.25f), "Out Of World Damage Reduction (75%)");
        register("dmg_generic_75", damageReductionEffect("generic", 0.25f), "Generic Damage Reduction (75%)");
        register("dmg_magic_75", damageReductionEffect("magic", 0.25f), "Magic Damage Reduction (75%)");
        register("dmg_wither_75", damageReductionEffect("wither", 0.25f), "Wither Damage Reduction (75%)");
        register("dmg_anvil_75", damageReductionEffect("anvil", 0.25f), "Anvil Damage Reduction (75%)");
        register("dmg_fallingblock_75", damageReductionEffect("fallingBlock", 0.25f), "Falling Block Damage Reduction (75%)");
        register("dmg_dragonbreath_75", damageReductionEffect("dragonBreath", 0.25f), "Dragon Breath Damage Reduction (75%)");
        register("dmg_dryout_75", damageReductionEffect("dryout", 0.25f), "Dryout Damage Reduction (75%)");
        register("dmg_sweetberrybush_75", damageReductionEffect("sweetBerryBush", 0.25f), "Sweet Berry Bush Damage Reduction (75%)");
        register("dmg_freeze_75", damageReductionEffect("freeze", 0.25f), "Freeze Damage Reduction (75%)");
        register("dmg_fallingstalactite_75", damageReductionEffect("fallingStalactite", 0.25f), "Falling Stalactite Damage Reduction (75%)");
        register("dmg_stalagmite_75", damageReductionEffect("stalagmite", 0.25f), "Stalagmite Damage Reduction (75%)");

        register("dmg_infire_50", damageReductionEffect("inFire", 0.5f), "In Fire Damage Reduction (50%)");
        register("dmg_lightningbolt_50", damageReductionEffect("lightningBolt", 0.5f), "Lightning Bolt Damage Reduction (50%)");
        register("dmg_onfire_50", damageReductionEffect("onFire", 0.5f), "On Fire Damage Reduction (50%)");
        register("dmg_lava_50", damageReductionEffect("lava", 0.5f), "Lava Damage Reduction (50%)");
        register("dmg_hotfloor_50", damageReductionEffect("hotFloor", 0.5f), "Hot Floor Damage Reduction (50%)");
        register("dmg_inwall_50", damageReductionEffect("inWall", 0.5f), "Suffocation Damage Reduction (50%)");
        register("dmg_cramming_50", damageReductionEffect("cramming", 0.5f), "Cramming Damage Reduction (50%)");
        register("dmg_drown_50", damageReductionEffect("drown", 0.5f), "Drowning Damage Reduction (50%)");
        register("dmg_starve_50", damageReductionEffect("starve", 0.5f), "Starving Damage Reduction (50%)");
        register("dmg_cactus_50", damageReductionEffect("cactus", 0.5f), "Cactus Damage Reduction (50%)");
        register("dmg_fall_50", damageReductionEffect("fall", 0.5f), "Fall Damage Reduction (50%)");
        register("dmg_flyintowall_50", damageReductionEffect("flyIntoWall", 0.5f), "Fly Into Wall Damage Reduction (50%)");
        register("dmg_outofworld_50", damageReductionEffect("outOfWorld", 0.5f), "Out Of World Damage Reduction (50%)");
        register("dmg_generic_50", damageReductionEffect("generic", 0.5f), "Generic Damage Reduction (50%)");
        register("dmg_magic_50", damageReductionEffect("magic", 0.5f), "Magic Damage Reduction (50%)");
        register("dmg_wither_50", damageReductionEffect("wither", 0.5f), "Wither Damage Reduction (50%)");
        register("dmg_anvil_50", damageReductionEffect("anvil", 0.5f), "Anvil Damage Reduction (50%)");
        register("dmg_fallingblock_50", damageReductionEffect("fallingBlock", 0.5f), "Falling Block Damage Reduction (50%)");
        register("dmg_dragonbreath_50", damageReductionEffect("dragonBreath", 0.5f), "Dragon Breath Damage Reduction (50%)");
        register("dmg_dryout_50", damageReductionEffect("dryout", 0.5f), "Dryout Damage Reduction (50%)");
        register("dmg_sweetberrybush_50", damageReductionEffect("sweetBerryBush", 0.5f), "Sweet Berry Bush Damage Reduction (50%)");
        register("dmg_freeze_50", damageReductionEffect("freeze", 0.5f), "Freeze Damage Reduction (50%)");
        register("dmg_fallingstalactite_50", damageReductionEffect("fallingStalactite", 0.5f), "Falling Stalactite Damage Reduction (50%)");
        register("dmg_stalagmite_50", damageReductionEffect("stalagmite", 0.5f), "Stalagmite Damage Reduction (50%)");

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

    @Override
    public void init(FMLCommonSetupEvent event) {
    }

    @Override
    public void initClient(FMLClientSetupEvent event) {
    }

    @Override
    public void initConfig() {
    }

    public static final Map<ResourceLocation, EffectInfo> EFFECTS = new HashMap<>();

    private EffectDescription damageReductionEffect(String dmgId, float factor) {
        return new EffectDescription(null, null, new DamageReductionEffect.Params(dmgId, factor));
    }

    private EffectDescription potionResistEffect(String effect) {
        return new EffectDescription(null, null, new PotionResistanceEffect.Params(effect));
    }

    private EffectDescription mobEffect(String effect, int level) {
        return new EffectDescription(null, null, new MobEffectEffect.Params(effect, level));
    }

    private EffectDescription mobEffect(String effect, int level, Integer hotkey, String toggle) {
        return new EffectDescription(hotkey, toggle, new MobEffectEffect.Params(effect, level));
    }

    private EffectDescription attributeEffect(String effect, AttributeModifier.Operation operation, double amount) {
        return new EffectDescription(null, null, new AttributeModifierEffect.Params(effect, operation, amount));
    }

    private EffectDescription flightEffect() {
        return new EffectDescription(null, null, FlightEffect.Params.EMPTY);
    }

    private EffectDescription cureEffect() {
        return new EffectDescription(null, null, CureEffect.Params.EMPTY);
    }

    private static void register(String id, EffectDescription effect, String description) {
        EFFECTS.put(new ResourceLocation(FancyTrinkets.MODID, id), new EffectInfo(effect, description));
    }

    public static record EffectInfo(EffectDescription effect, String description) {
    }
}
