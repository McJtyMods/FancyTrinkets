package com.mcjty.fancytrinkets.datapack;

import com.mcjty.fancytrinkets.FancyTrinkets;
import mcjty.lib.varia.Tools;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.IForgeRegistry;

import java.util.function.Supplier;

public class CustomRegistries {

    public static final ResourceKey<Registry<TrinketDescription>> TRINKET_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(FancyTrinkets.MODID, "trinkets"));
    public static final DeferredRegister<TrinketDescription> TRINKET_DEFERRED_REGISTER = DeferredRegister.create(TRINKET_REGISTRY_KEY, FancyTrinkets.MODID);

    public static final ResourceKey<Registry<EffectDescription>> EFFECT_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(FancyTrinkets.MODID, "effects"));
    public static final DeferredRegister<EffectDescription> EFFECT_DEFERRED_REGISTER = DeferredRegister.create(EFFECT_REGISTRY_KEY, FancyTrinkets.MODID);

    public static final ResourceKey<Registry<BonusTable>> BONUS_TABLE_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(FancyTrinkets.MODID, "bonustables"));
    public static final DeferredRegister<BonusTable> BONUS_TABLE_DEFERRED_REGISTER = DeferredRegister.create(BONUS_TABLE_REGISTRY_KEY, FancyTrinkets.MODID);

    public static final ResourceKey<Registry<TrinketSet>> TRINKET_SET_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(FancyTrinkets.MODID, "trinketsets"));
    public static final DeferredRegister<TrinketSet> TRINKET_SET_DEFERRED_REGISTER = DeferredRegister.create(TRINKET_SET_REGISTRY_KEY, FancyTrinkets.MODID);

    public static void init(IEventBus bus) {
        TRINKET_DEFERRED_REGISTER.register(bus);
        EFFECT_DEFERRED_REGISTER.register(bus);
        BONUS_TABLE_DEFERRED_REGISTER.register(bus);
        TRINKET_SET_DEFERRED_REGISTER.register(bus);
        Tools.onDataPackRegistry(bus, idpRegister -> {
            idpRegister.register(TRINKET_REGISTRY_KEY, TrinketDescription.CODEC);
            idpRegister.register(EFFECT_REGISTRY_KEY, EffectDescription.CODEC);
            idpRegister.register(BONUS_TABLE_REGISTRY_KEY, BonusTable.CODEC);
            idpRegister.register(TRINKET_SET_REGISTRY_KEY, TrinketSet.CODEC);
        });
    }

}
