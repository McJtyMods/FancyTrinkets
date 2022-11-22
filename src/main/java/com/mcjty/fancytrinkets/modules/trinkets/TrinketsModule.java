package com.mcjty.fancytrinkets.modules.trinkets;

import com.mcjty.fancytrinkets.datapack.CustomRegistries;
import com.mcjty.fancytrinkets.datapack.TrinketDescription;
import com.mcjty.fancytrinkets.modules.trinkets.items.TrinketItem;
import com.mcjty.fancytrinkets.setup.Registration;
import mcjty.lib.modules.IModule;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.Map;

public class TrinketsModule implements IModule {

    public static final RegistryObject<TrinketItem> GOLD_RING = Registration.trinket("gold_ring", "item/gold_ring", "Base golden ring item");
    public static final RegistryObject<TrinketItem> GOLD_RING_BLUE = Registration.trinket("gold_ring_blue", "item/gold_ring_blue", "Base golden ring item");
    public static final RegistryObject<TrinketItem> GOLD_RING_GREEN = Registration.trinket("gold_ring_green", "item/gold_ring_green", "Base golden ring item");
    public static final RegistryObject<TrinketItem> GOLD_RING_RED = Registration.trinket("gold_ring_red", "item/gold_ring_red", "Base golden ring item");
    public static final RegistryObject<TrinketItem> GOLD_RING_DIAMOND = Registration.trinket("gold_ring_diamond", "item/gold_ring_diamond", "Base golden ring item");
    public static final RegistryObject<TrinketItem> SILVER_RING = Registration.trinket("silver_ring", "item/silver_ring", "Base silver ring item");
    public static final RegistryObject<TrinketItem> SILVER_RING_BLUE = Registration.trinket("silver_ring_blue", "item/silver_ring_blue", "Base silver ring item");
    public static final RegistryObject<TrinketItem> SILVER_RING_GREEN = Registration.trinket("silver_ring_green", "item/silver_ring_green", "Base silver ring item");
    public static final RegistryObject<TrinketItem> SILVER_RING_RED = Registration.trinket("silver_ring_red", "item/silver_ring_red", "Base silver ring item");
    public static final RegistryObject<TrinketItem> SILVER_RING_DIAMOND = Registration.trinket("silver_ring_diamond", "item/silver_ring_diamond", "Base silver ring item");
    public static final RegistryObject<TrinketItem> OBSIDIAN_RING = Registration.trinket("obsidian_ring", "item/obsidian_ring", "Base obsidian ring item");
    public static final RegistryObject<TrinketItem> OBSIDIAN_RING_DIAMOND = Registration.trinket("obsidian_ring_diamond", "item/obsidian_ring_diamond", "Base obsidian ring item");

    public static final RegistryObject<TrinketItem> STAR = Registration.trinket("star", "item/star", "Base star item");

//    public static final Capability<ICurio> CURIOS_CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});
    public static final Capability<ICurio> CURIOS_CAPABILITY = CuriosCapability.ITEM;

    public TrinketsModule() {
        MinecraftForge.EVENT_BUS.addListener(this::onServerStarting);
    }

    @Override
    public void init(FMLCommonSetupEvent event) {
    }

    public void onServerStarting(ServerStartingEvent event) {
            registerTrinkets(event.getServer().overworld());
    }

    private void registerTrinkets(ServerLevel level) {
        for (Map.Entry<ResourceKey<TrinketDescription>, TrinketDescription> entry : level.registryAccess().registryOrThrow(CustomRegistries.TRINKET_REGISTRY_KEY).entrySet()) {
            TrinketDescription description = entry.getValue();
            ResourceLocation itemId = description.item();
            Item item = ForgeRegistries.ITEMS.getValue(itemId);
            if (item == null || item == Items.AIR) {
                throw new RuntimeException("Can't find item '" + itemId.toString() + "'!");
            }
            if (item instanceof ITrinketItem trinketItem) {
                trinketItem.registerTrinketInstance(description);
            } else {
                throw new RuntimeException("Item '" + itemId.toString() + "' is not an ITrinketItem!");
            }
        }
    }

    @Override
    public void initClient(FMLClientSetupEvent event) {
    }

    @Override
    public void initConfig() {

    }
}
