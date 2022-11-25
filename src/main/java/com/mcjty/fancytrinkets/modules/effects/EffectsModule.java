package com.mcjty.fancytrinkets.modules.effects;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.datapack.EffectDescription;
import com.mcjty.fancytrinkets.modules.effects.imp.AttributeModifierEffect;
import com.mcjty.fancytrinkets.modules.effects.imp.FlightEffect;
import com.mcjty.fancytrinkets.modules.effects.imp.MobEffectEffect;
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
        register("night_vision_hotkey", mobEffect("minecraft:night_vision", 1, 1), "Nightvision");
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

    private EffectDescription mobEffect(String effect, int level) {
        return new EffectDescription(null, new MobEffectEffect.Params(effect, level));
    }

    private EffectDescription mobEffect(String effect, int level, Integer hotkey) {
        return new EffectDescription(hotkey, new MobEffectEffect.Params(effect, level));
    }

    private EffectDescription attributeEffect(String effect, AttributeModifier.Operation operation, double amount) {
        return new EffectDescription(null, new AttributeModifierEffect.Params(effect, operation, amount));
    }

    private EffectDescription flightEffect() {
        return new EffectDescription(null, FlightEffect.Params.EMPTY);
    }

    private static void register(String id, EffectDescription effect, String description) {
        EFFECTS.put(new ResourceLocation(FancyTrinkets.MODID, id), new EffectInfo(effect, description));
    }

    public static record EffectInfo(EffectDescription effect, String description) {
    }
}
