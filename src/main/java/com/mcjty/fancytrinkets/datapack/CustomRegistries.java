package com.mcjty.fancytrinkets.datapack;

import com.mcjty.fancytrinkets.FancyTrinkets;
import mcjty.lib.varia.Tools;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import mcjty.lib.setup.DeferredRegister;

public class CustomRegistries {

    public static final ResourceKey<Registry<TrinketDescription>> TRINKET_REGISTRY_KEY = ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath(FancyTrinkets.MODID, "trinkets"));

    public static final ResourceKey<Registry<EffectDescription>> EFFECT_REGISTRY_KEY = ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath(FancyTrinkets.MODID, "effects"));

    public static final ResourceKey<Registry<BonusTable>> BONUS_TABLE_REGISTRY_KEY = ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath(FancyTrinkets.MODID, "bonustables"));

    public static final ResourceKey<Registry<TrinketSet>> TRINKET_SET_REGISTRY_KEY = ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath(FancyTrinkets.MODID, "trinketsets"));

    public static void init() {
        net.fabricmc.fabric.api.event.registry.DynamicRegistries.registerSynced(TRINKET_REGISTRY_KEY, TrinketDescription.CODEC);
        net.fabricmc.fabric.api.event.registry.DynamicRegistries.registerSynced(EFFECT_REGISTRY_KEY, EffectDescription.CODEC);
        net.fabricmc.fabric.api.event.registry.DynamicRegistries.registerSynced(BONUS_TABLE_REGISTRY_KEY, BonusTable.CODEC);
        net.fabricmc.fabric.api.event.registry.DynamicRegistries.registerSynced(TRINKET_SET_REGISTRY_KEY, TrinketSet.CODEC);
    }
}
