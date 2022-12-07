package com.mcjty.fancytrinkets.datapack;

import com.mcjty.fancytrinkets.FancyTrinkets;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

public class CustomRegistries {

    public static final ResourceKey<Registry<TrinketDescription>> TRINKET_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(FancyTrinkets.MODID, "trinkets"));
    public static final DeferredRegister<TrinketDescription> TRINKET_DEFERRED_REGISTER = DeferredRegister.create(TRINKET_REGISTRY_KEY, FancyTrinkets.MODID);
    public static final Supplier<IForgeRegistry<TrinketDescription>> TRINKET_REGISTRY = TRINKET_DEFERRED_REGISTER.makeRegistry(() -> new RegistryBuilder<TrinketDescription>().dataPackRegistry(TrinketDescription.CODEC));

    public static final ResourceKey<Registry<EffectDescription>> EFFECT_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(FancyTrinkets.MODID, "effects"));
    public static final DeferredRegister<EffectDescription> EFFECT_DEFERRED_REGISTER = DeferredRegister.create(EFFECT_REGISTRY_KEY, FancyTrinkets.MODID);
    public static final Supplier<IForgeRegistry<EffectDescription>> EFFECT_REGISTRY = EFFECT_DEFERRED_REGISTER.makeRegistry(() -> new RegistryBuilder<EffectDescription>().dataPackRegistry(EffectDescription.CODEC));

    public static final ResourceKey<Registry<BonusTable>> BONUS_TABLE_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(FancyTrinkets.MODID, "bonustables"));
    public static final DeferredRegister<BonusTable> BONUS_TABLE_DEFERRED_REGISTER = DeferredRegister.create(BONUS_TABLE_REGISTRY_KEY, FancyTrinkets.MODID);
    public static final Supplier<IForgeRegistry<BonusTable>> BONUS_TABLE_REGISTRY = BONUS_TABLE_DEFERRED_REGISTER.makeRegistry(() -> new RegistryBuilder<BonusTable>().dataPackRegistry(BonusTable.CODEC));

    public static final ResourceKey<Registry<TrinketSet>> TRINKET_SET_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation(FancyTrinkets.MODID, "trinketsets"));
    public static final DeferredRegister<TrinketSet> TRINKET_SET_DEFERRED_REGISTER = DeferredRegister.create(TRINKET_SET_REGISTRY_KEY, FancyTrinkets.MODID);
    public static final Supplier<IForgeRegistry<TrinketSet>> TRINKET_SET_REGISTRY = TRINKET_SET_DEFERRED_REGISTER.makeRegistry(() -> new RegistryBuilder<TrinketSet>().dataPackRegistry(TrinketSet.CODEC));

    public static void init() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        TRINKET_DEFERRED_REGISTER.register(bus);
        EFFECT_DEFERRED_REGISTER.register(bus);
        BONUS_TABLE_DEFERRED_REGISTER.register(bus);
        TRINKET_SET_DEFERRED_REGISTER.register(bus);
    }

}
