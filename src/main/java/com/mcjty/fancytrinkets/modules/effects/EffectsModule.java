package com.mcjty.fancytrinkets.modules.effects;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.modules.effects.imp.FlightEffect;
import com.mcjty.fancytrinkets.modules.effects.imp.MobEffectEffect;
import mcjty.lib.modules.IModule;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
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

    public static final ResourceLocation EFFECT_FLIGHT = new ResourceLocation(FancyTrinkets.MODID, "flight");

    public EffectsModule() {
        register(MobEffectEffect.builder(EFFECT_REGENERATION).effect(MobEffects.REGENERATION).build(), "Regeneration");
        register(MobEffectEffect.builder(EFFECT_DAMAGE_BOOST).effect(MobEffects.DAMAGE_BOOST).build(), "Damage Boost");
        register(MobEffectEffect.builder(EFFECT_ABSORPTION).effect(MobEffects.ABSORPTION).build(), "Absorption");
        register(MobEffectEffect.builder(EFFECT_DAMAGE_RESISTANCE).effect(MobEffects.DAMAGE_RESISTANCE).build(), "Damage Resistance");
        register(MobEffectEffect.builder(EFFECT_HASTE).effect(MobEffects.DIG_SPEED).build(), "Haste");
        register(MobEffectEffect.builder(EFFECT_HEALTH_BOOST).effect(MobEffects.HEALTH_BOOST).build(), "Health Boost");
        register(MobEffectEffect.builder(EFFECT_INVISIBILITY).effect(MobEffects.INVISIBILITY).build(), "Invisibility");

        register(new FlightEffect(EFFECT_FLIGHT), "Flight");
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
