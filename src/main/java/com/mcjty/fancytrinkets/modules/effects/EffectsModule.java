package com.mcjty.fancytrinkets.modules.effects;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.modules.effects.imp.AttributeModifierEffect;
import com.mcjty.fancytrinkets.modules.effects.imp.FlightEffect;
import com.mcjty.fancytrinkets.modules.effects.imp.MobEffectEffect;
import mcjty.lib.modules.IModule;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.HashMap;
import java.util.Map;

public class EffectsModule implements IModule {

    public static final ResourceLocation EFFECT_REGENERATION = new ResourceLocation(FancyTrinkets.MODID, "regeneration");
    public static final ResourceLocation EFFECT_DAMAGE_BOOST = new ResourceLocation(FancyTrinkets.MODID, "damage_boost");
    public static final ResourceLocation EFFECT_ABSORPTION = new ResourceLocation(FancyTrinkets.MODID, "absorption");
    public static final ResourceLocation EFFECT_DAMAGE_RESISTANCE = new ResourceLocation(FancyTrinkets.MODID, "damage_resistance");
    public static final ResourceLocation EFFECT_HASTE = new ResourceLocation(FancyTrinkets.MODID, "haste");
    public static final ResourceLocation EFFECT_HEALTH_BOOST = new ResourceLocation(FancyTrinkets.MODID, "health_boost");
    public static final ResourceLocation EFFECT_INVISIBILITY = new ResourceLocation(FancyTrinkets.MODID, "invisibility");
    public static final ResourceLocation EFFECT_NIGHTVISION = new ResourceLocation(FancyTrinkets.MODID, "night_vision");
    public static final ResourceLocation EFFECT_SLOW_FALLING = new ResourceLocation(FancyTrinkets.MODID, "slow_falling");

    public static final ResourceLocation EFFECT_FLIGHT = new ResourceLocation(FancyTrinkets.MODID, "flight");
    public static final ResourceLocation EFFECT_STEPASSIST = new ResourceLocation(FancyTrinkets.MODID, "step_assist");
    public static final ResourceLocation EFFECT_SWIMSPEED = new ResourceLocation(FancyTrinkets.MODID, "swim_speed");
    public static final ResourceLocation EFFECT_ATTACK_RANGE = new ResourceLocation(FancyTrinkets.MODID, "attack_range");
    public static final ResourceLocation EFFECT_REACH_DISTANCE = new ResourceLocation(FancyTrinkets.MODID, "reach_distance");

    public static final ResourceLocation EFFECT_MAX_HEALTH = new ResourceLocation(FancyTrinkets.MODID, "max_health");
    public static final ResourceLocation EFFECT_KNOCKBACK_RESISTANCE = new ResourceLocation(FancyTrinkets.MODID, "knockback_resistance");
    public static final ResourceLocation EFFECT_MOVEMENT_SPEED = new ResourceLocation(FancyTrinkets.MODID, "movement_speed");
    public static final ResourceLocation EFFECT_ATTACK_SPEED = new ResourceLocation(FancyTrinkets.MODID, "attack_speed");
    public static final ResourceLocation EFFECT_ATTACK_DAMAGE = new ResourceLocation(FancyTrinkets.MODID, "attack_damage");
    public static final ResourceLocation EFFECT_LUCK = new ResourceLocation(FancyTrinkets.MODID, "luck");

    public EffectsModule() {
        register(MobEffectEffect.builder(EFFECT_REGENERATION).effect(MobEffects.REGENERATION).build(), "Regeneration");
        register(MobEffectEffect.builder(EFFECT_DAMAGE_BOOST).effect(MobEffects.DAMAGE_BOOST).build(), "Damage Boost");
        register(MobEffectEffect.builder(EFFECT_ABSORPTION).effect(MobEffects.ABSORPTION).build(), "Absorption");
        register(MobEffectEffect.builder(EFFECT_DAMAGE_RESISTANCE).effect(MobEffects.DAMAGE_RESISTANCE).build(), "Damage Resistance");
        register(MobEffectEffect.builder(EFFECT_HASTE).effect(MobEffects.DIG_SPEED).build(), "Haste");
        register(MobEffectEffect.builder(EFFECT_HEALTH_BOOST).effect(MobEffects.HEALTH_BOOST).build(), "Health Boost");
        register(MobEffectEffect.builder(EFFECT_INVISIBILITY).effect(MobEffects.INVISIBILITY).build(), "Invisibility");
        register(MobEffectEffect.builder(EFFECT_NIGHTVISION).effect(MobEffects.NIGHT_VISION).build(), "Nightvision");
        register(MobEffectEffect.builder(EFFECT_SLOW_FALLING).effect(MobEffects.SLOW_FALLING).strengthModifier(1).build(), "Slow Falling");

        register(new FlightEffect(EFFECT_FLIGHT), "Flight");

        register(new AttributeModifierEffect(EFFECT_STEPASSIST, "step assist", ForgeMod.STEP_HEIGHT_ADDITION, AttributeModifier.Operation.ADDITION, .5), "Step assist");
        register(new AttributeModifierEffect(EFFECT_SWIMSPEED, "swim speed", ForgeMod.SWIM_SPEED, AttributeModifier.Operation.MULTIPLY_TOTAL, 2), "Swim speed");
        register(new AttributeModifierEffect(EFFECT_ATTACK_RANGE, "attack range", ForgeMod.ATTACK_RANGE, AttributeModifier.Operation.ADDITION, 1), "Attack range");
        register(new AttributeModifierEffect(EFFECT_REACH_DISTANCE, "reach distance", ForgeMod.REACH_DISTANCE, AttributeModifier.Operation.ADDITION, 2), "Reach distance");
        register(new AttributeModifierEffect(EFFECT_MAX_HEALTH, "max health", () -> Attributes.MAX_HEALTH, AttributeModifier.Operation.ADDITION, 2), "Max health");
        register(new AttributeModifierEffect(EFFECT_KNOCKBACK_RESISTANCE, "knockback resistance", () -> Attributes.KNOCKBACK_RESISTANCE, AttributeModifier.Operation.MULTIPLY_TOTAL, 2), "Knockback resistance");
        register(new AttributeModifierEffect(EFFECT_MOVEMENT_SPEED, "movement speed", () -> Attributes.MOVEMENT_SPEED, AttributeModifier.Operation.MULTIPLY_TOTAL, 2), "Movement speed");
        register(new AttributeModifierEffect(EFFECT_ATTACK_SPEED, "attack speed", () -> Attributes.ATTACK_SPEED, AttributeModifier.Operation.MULTIPLY_TOTAL, 2), "Attack speed");
        register(new AttributeModifierEffect(EFFECT_ATTACK_DAMAGE, "attack damage", () -> Attributes.ATTACK_DAMAGE, AttributeModifier.Operation.MULTIPLY_TOTAL, 2), "Attack damage");
        register(new AttributeModifierEffect(EFFECT_LUCK, "luck", () -> Attributes.LUCK, AttributeModifier.Operation.MULTIPLY_TOTAL, 2), "Luck");
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

    private static void register(IEffect effect, String description) {
        EFFECTS.put(effect.getId(), new EffectInfo(effect.getId(), description, effect));
    }

    public static record EffectInfo(ResourceLocation id, String description, IEffect effect) {
    }
}
