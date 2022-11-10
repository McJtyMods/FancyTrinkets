package com.mcjty.fancytrinkets.modules.trinkets;

import com.mcjty.fancytrinkets.modules.trinkets.items.TrinketItem;
import com.mcjty.fancytrinkets.setup.Registration;
import mcjty.lib.modules.IModule;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.RegistryObject;

import static com.mcjty.fancytrinkets.curios.CuriosSetup.SLOT_ANY;
import static com.mcjty.fancytrinkets.curios.CuriosSetup.SLOT_RING;
import static com.mcjty.fancytrinkets.modules.effects.EffectsModule.*;

public class TrinketsModule implements IModule {

    public static final RegistryObject<TrinketItem> REGENERATION_RING = Registration.trinket("regeneration_ring", "item/gold_ring",
            "Potion Ring of Regeneration", "Potion Ring of Regeneration", "Applies regeneration 1 while this item is in a bauble slot",
            SLOT_RING, EFFECT_REGENERATION);
    public static final RegistryObject<TrinketItem> ABSORPTION_RING = Registration.trinket("absorption_ring", "item/gold_ring",
            "Potion Ring of Absorption", "Potion Ring of Absorption", "Applies absoption while this item is in a bauble slot",
            SLOT_RING, EFFECT_ABSORPTION);
    public static final RegistryObject<TrinketItem> DAMAGE_BOOST_RING = Registration.trinket("damage_boost_ring", "item/gold_ring",
            "Potion Ring of Damage Boost", "Potion Ring of Damage Boost", "Applies damage boost while this item is in a bauble slot",
            SLOT_RING, EFFECT_DAMAGE_BOOST);
    public static final RegistryObject<TrinketItem> DAMAGE_RESISTANCE_RING = Registration.trinket("damage_resistance_ring", "item/gold_ring",
            "Potion Ring of Damage Resistance", "Potion Ring of Damage Resistance", "Applies damage resistance while this item is in a bauble slot",
            SLOT_RING, EFFECT_DAMAGE_RESISTANCE);
    public static final RegistryObject<TrinketItem> HASTE_RING = Registration.trinket("haste_ring", "item/gold_ring",
            "Potion Ring of Haste", "Potion Ring of Haste", "Applies haste while this item is in a bauble slot",
            SLOT_RING, EFFECT_HASTE);
    public static final RegistryObject<TrinketItem> HEALTH_BOOST_RING = Registration.trinket("health_boost_ring", "item/gold_ring",
            "Potion Ring of Health Boost", "Potion Ring of Health Boost", "Applies health boost while this item is in a bauble slot",
            SLOT_RING, EFFECT_HEALTH_BOOST);
    public static final RegistryObject<TrinketItem> INVISIBILITY_RING = Registration.trinket("invisibility_ring", "item/gold_ring",
            "Potion Ring of Invisibility", "Potion Ring of Invisibility", "Applies invisibility while this item is in a bauble slot",
            SLOT_RING, EFFECT_INVISIBILITY);


    public static final RegistryObject<TrinketItem> STAR = Registration.trinket("star", "item/star",
            "The Star", "The Star", "This item allows you to gather the energy of the stars",
            SLOT_ANY, EFFECT_ABSORPTION, EFFECT_REGENERATION, EFFECT_DAMAGE_BOOST);

    public TrinketsModule() {
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
}
