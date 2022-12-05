package com.mcjty.fancytrinkets.modules.trinkets;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.datapack.TrinketDescription;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultTrinkets {
    public static final Map<ResourceLocation, TrinketInfo> DEFAULT_TRINKETS = new HashMap<>();

    static void init() {
        DEFAULT_TRINKETS.clear();
        register("base_star", trinket("base_star", "star"), "Base Star", "Crafting ingredient to make star trinkets");
        register("flight_star", trinket("flight_star", "star",
                effect("flight")), "Sky Star", "This star gives you the freedom of flight");
        register("power_star", trinket("power_star", "star",
                effect("attack_range"), effect("attack_speed"), effect("attack_damage"), effect("reach_distance")), "Power Star", "You feel the power surging through you!");
        register("swift_star", trinket("swift_star", "star",
                effect("movement_speed"), effect("knockback_resistance"), effect("swim_speed"), effect("step_assist")), "Star of Swiftness", "Feel the freedom of swift and flexible movement");

        register("base_feather", trinket("base_feather", "feather"), "Base Feather", "Crafting ingredient to make feather trinkets");
        register("slowfalling_feather", trinket("slowfalling_feather", "feather",
                effect("slow_falling")), "Golden Feather", "Gravity seems to have less effectId on you");

        register("base_gold_ring", trinket("base_gold_ring", "gold_ring"), "Base Golden Ring", "Crafting ingredient to make golden ring trinkets");
        register("regeneration_ring", trinket("regeneration_ring", "gold_ring",
                effect("regeneration")), "Regeneration Ring", "Slowly get your health back");
        register("strength_ring", trinket("strength_ring", "gold_ring",
                effect("strength")), "Strength Ring", "Your attacks seem to have more effectId");
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

        register("warp_pearl", trinket("warp_pearl", "blue_pearl", effect("warp")), "Warp Pearl", "Warp Pearl");
    }

    private static TrinketDescription.EffectRef effect(String id) {
        return new TrinketDescription.EffectRef(new ResourceLocation(FancyTrinkets.MODID, id), false);
    }

    private static TrinketDescription.EffectRef hidden(String id) {
        return new TrinketDescription.EffectRef(new ResourceLocation(FancyTrinkets.MODID, id), true);
    }

    private static void register(String id, TrinketDescription trinket, String name, String description) {
        DEFAULT_TRINKETS.put(new ResourceLocation(FancyTrinkets.MODID, id), new TrinketInfo(trinket, name, description));
    }

    private static TrinketDescription trinket(String id, String itemId, TrinketDescription.EffectRef... effects) {
        return new TrinketDescription(new ResourceLocation(FancyTrinkets.MODID, itemId),
                new ResourceLocation(FancyTrinkets.MODID, "standard"),
                "trinket.fancytrinkets." + id + ".name",
                "trinket.fancytrinkets." + id + ".description",
                List.of(effects));
    }

    public static record TrinketInfo(TrinketDescription trinketDescription, String name, String description) {
    }
}
