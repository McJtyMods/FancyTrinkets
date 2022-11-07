package com.mcjty.fancytrinkets.modules.effects;

import com.mcjty.fancytrinkets.FancyTrinkets;
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

    public static final Map<ResourceLocation, IEffect> effectMap = new HashMap<>();

    public EffectsModule() {
        register(MobEffectEffect.builder(EFFECT_REGENERATION).effect(MobEffects.REGENERATION).build());
        register(MobEffectEffect.builder(EFFECT_DAMAGE_BOOST).effect(MobEffects.DAMAGE_BOOST).build());
        register(MobEffectEffect.builder(EFFECT_ABSORPTION).effect(MobEffects.ABSORPTION).build());
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

    private static void register(IEffect effect) {
        effectMap.put(effect.getId(), effect);
    }
}
