package com.mcjty.fancytrinkets.modules.trinkets;

import com.mcjty.fancytrinkets.datapack.CustomRegistries;
import com.mcjty.fancytrinkets.datapack.TrinketDescription;
import com.mcjty.fancytrinkets.modules.trinkets.items.TrinketItem;
import com.mcjty.fancytrinkets.setup.Registration;
import mcjty.lib.modules.IModule;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.Map;

public class TrinketsModule implements IModule {

    public static final TagKey<Item> RING_TAG = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(CuriosApi.MODID, SlotTypePreset.RING.getIdentifier()));
    public static final TagKey<Item> BELT_TAG = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(CuriosApi.MODID, SlotTypePreset.BELT.getIdentifier()));
    public static final TagKey<Item> BRACELET_TAG = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(CuriosApi.MODID, SlotTypePreset.BRACELET.getIdentifier()));
    public static final TagKey<Item> CHARM_TAG = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(CuriosApi.MODID, SlotTypePreset.CHARM.getIdentifier()));
    public static final TagKey<Item> NECKLACE_TAG = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(CuriosApi.MODID, SlotTypePreset.NECKLACE.getIdentifier()));
    public static final TagKey<Item> HEAD_TAG = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(CuriosApi.MODID, SlotTypePreset.HEAD.getIdentifier()));
    public static final TagKey<Item> BODY_TAG = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(CuriosApi.MODID, SlotTypePreset.BODY.getIdentifier()));

    public static final RegistryObject<TrinketItem> GOLD_RING = Registration.trinket("gold_ring", "item/gold_ring", "Base golden ring item", RING_TAG);
    public static final RegistryObject<TrinketItem> GOLD_RING_BLUE = Registration.trinket("gold_ring_blue", "item/gold_ring_blue", "Base golden ring item", RING_TAG);
    public static final RegistryObject<TrinketItem> GOLD_RING_GREEN = Registration.trinket("gold_ring_green", "item/gold_ring_green", "Base golden ring item", RING_TAG);
    public static final RegistryObject<TrinketItem> GOLD_RING_RED = Registration.trinket("gold_ring_red", "item/gold_ring_red", "Base golden ring item", RING_TAG);
    public static final RegistryObject<TrinketItem> GOLD_RING_DIAMOND = Registration.trinket("gold_ring_diamond", "item/gold_ring_diamond", "Base golden ring item", RING_TAG);
    public static final RegistryObject<TrinketItem> SILVER_RING = Registration.trinket("silver_ring", "item/silver_ring", "Base silver ring item", RING_TAG);
    public static final RegistryObject<TrinketItem> SILVER_RING_BLUE = Registration.trinket("silver_ring_blue", "item/silver_ring_blue", "Base silver ring item", RING_TAG);
    public static final RegistryObject<TrinketItem> SILVER_RING_GREEN = Registration.trinket("silver_ring_green", "item/silver_ring_green", "Base silver ring item", RING_TAG);
    public static final RegistryObject<TrinketItem> SILVER_RING_RED = Registration.trinket("silver_ring_red", "item/silver_ring_red", "Base silver ring item", RING_TAG);
    public static final RegistryObject<TrinketItem> SILVER_RING_DIAMOND = Registration.trinket("silver_ring_diamond", "item/silver_ring_diamond", "Base silver ring item", RING_TAG);
    public static final RegistryObject<TrinketItem> OBSIDIAN_RING = Registration.trinket("obsidian_ring", "item/obsidian_ring", "Base obsidian ring item", RING_TAG);
    public static final RegistryObject<TrinketItem> OBSIDIAN_RING_DIAMOND = Registration.trinket("obsidian_ring_diamond", "item/obsidian_ring_diamond", "Base obsidian ring item", RING_TAG);

    public static final RegistryObject<TrinketItem> STAR = Registration.trinket("star", "item/star", "Base star item",
            RING_TAG, BELT_TAG, BRACELET_TAG, CHARM_TAG, NECKLACE_TAG, HEAD_TAG, BODY_TAG);
    public static final RegistryObject<TrinketItem> HEART = Registration.trinket("heart", "item/heart", "Base heart item",
            RING_TAG, BELT_TAG, BRACELET_TAG, CHARM_TAG, NECKLACE_TAG, HEAD_TAG, BODY_TAG);
    public static final RegistryObject<TrinketItem> HEART_BODY = Registration.trinket("heart_body", "item/heart", "Base heart item (body)", BODY_TAG);
    public static final RegistryObject<TrinketItem> FEATHER = Registration.trinket("feather", "item/feather", "Feather star item",
            BELT_TAG, CHARM_TAG, NECKLACE_TAG, HEAD_TAG, BODY_TAG);

    public static final Capability<ICurio> CURIOS_CAPABILITY = CuriosCapability.ITEM;

    public TrinketsModule() {
        MinecraftForge.EVENT_BUS.addListener(this::onServerStarting);

        DefaultTrinkets.init();
        DefaultBonusTables.init();
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
                trinketItem.registerTrinketInstance(level, entry.getKey().location(), description);
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
