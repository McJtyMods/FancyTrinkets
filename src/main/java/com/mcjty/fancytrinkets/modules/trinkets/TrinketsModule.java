package com.mcjty.fancytrinkets.modules.trinkets;

import com.mcjty.fancytrinkets.FancyTrinkets;
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

import java.util.HashMap;
import java.util.List;
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
        TRINKETS.clear();
        register("base_star", trinket("base_star", "star"), "Base Star", "Crafting ingredient to make star trinkets");
        register("flight_star", trinket("flight_star", "star",
                effect("flight")), "Sky Star", "This star gives you the freedom of flight");
        register("power_star", trinket("power_star", "star",
                effect("attack_range"), effect("attack_speed"), effect("attack_damage"), effect("reach_distance")), "Power Star", "You feel the power surging through you!");
        register("swift_star", trinket("swift_star", "star",
                effect("movement_speed"), effect("knockback_resistance"), effect("swim_speed"), effect("step_assist")), "Star of Swiftness", "Feel the freedom of swift and flexible movement");

        register("base_feather", trinket("base_feather", "feather"), "Base Feather", "Crafting ingredient to make feather trinkets");
        register("slowfalling_feather", trinket("slowfalling_feather", "feather",
                effect("slow_falling")), "Golden Feather", "Gravity seems to have less effect on you");

        register("base_gold_ring", trinket("base_gold_ring", "gold_ring"), "Base Golden Ring", "Crafting ingredient to make golden ring trinkets");
        register("regeneration_ring", trinket("regeneration_ring", "gold_ring",
                effect("regeneration")), "Regeneration Ring", "Slowly get your health back");
        register("strength_ring", trinket("strength_ring", "gold_ring",
                effect("strength")), "Strength Ring", "Your attacks seem to have more effect");
        register("nightvision_ring", trinket("nightvision_ring", "gold_ring",
                effect("night_vision_hotkey")), "Night Vision", "Using a hotkey you can see clearly in the dark");
        register("stepassist_ring", trinket("stepassist_ring", "gold_ring",
                effect("step_assist")), "Step Assist Ring", "You can move around much easier now");

        register("base_heart", trinket("base_heart", "heart"), "Base Heart", "Crafting ingredient to make heart trinkets");
        register("super_health", trinket("super_health", "heart_body",
                effect("regeneration"), effect("max_health")), "Heart of Health", "You feel so much more healthy now");
        register("cure", trinket("cure", "heart",
                effect("cure")), "Heart of Curing", "Negative effects can't harm you");

        register("base_gold_ring_diamond", trinket("base_gold_ring_diamond", "gold_ring_diamond"), "Base Golden Ring with Diamond", "Crafting ingredient to make golden ring trinkets (with diamond)");
        register("lightness_ring", trinket("lightness_ring", "gold_ring_diamond",
                effect("dmg_fall_75")), "Ring of Lightness", "Reduce 75% of fall damage");
        register("fireresist_ring", trinket("fireresist_ring", "gold_ring_diamond",
                effect("dmg_infire_100"), hidden("dmg_hotfloor_100"), hidden("dmg_onfire_100"), hidden("dmg_lava_100")), "Ring of Coolness", "Reduce all heat related damage (100%)");
    }

    public static TrinketDescription.EffectRef effect(String id) {
        return new TrinketDescription.EffectRef(new ResourceLocation(FancyTrinkets.MODID, id), false);
    }

    public static TrinketDescription.EffectRef hidden(String id) {
        return new TrinketDescription.EffectRef(new ResourceLocation(FancyTrinkets.MODID, id), true);
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

    public static final Map<ResourceLocation, TrinketInfo> TRINKETS = new HashMap<>();

    private static void register(String id, TrinketDescription trinket, String name, String description) {
        TRINKETS.put(new ResourceLocation(FancyTrinkets.MODID, id), new TrinketInfo(trinket, name, description));
    }

    private TrinketDescription trinket(String id, String itemId, TrinketDescription.EffectRef... effects) {
        return new TrinketDescription(new ResourceLocation(FancyTrinkets.MODID, itemId),
                "trinket.fancytrinkets." + id + ".name",
                "trinket.fancytrinkets." + id + ".description",
                List.of(effects));
    }

    public static record TrinketInfo(TrinketDescription trinketDescription, String name, String description) {
    }
}
