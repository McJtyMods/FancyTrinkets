package com.mcjty.fancytrinkets.datapack;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.datapack.TrinketDescription;
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

    public static void init() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        TRINKET_DEFERRED_REGISTER.register(bus);
    }

}