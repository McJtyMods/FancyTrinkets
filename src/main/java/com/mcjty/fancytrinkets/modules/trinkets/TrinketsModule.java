package com.mcjty.fancytrinkets.modules.trinkets;

import com.mcjty.fancytrinkets.api.ITrinketItem;
import com.mcjty.fancytrinkets.datapack.CustomRegistries;
import com.mcjty.fancytrinkets.datapack.TrinketDescription;
import com.mcjty.fancytrinkets.modules.trinkets.items.TrinketItem;
import com.mcjty.fancytrinkets.setup.Registration;
import mcjty.lib.datagen.DataGen;
import mcjty.lib.datagen.Dob;
import mcjty.lib.modules.IModule;
import net.minecraft.core.Registry;
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

import static com.mcjty.fancytrinkets.FancyTrinkets.MODID;
import static com.mcjty.fancytrinkets.FancyTrinkets.tab;

public class TrinketsModule implements IModule {

    public static final Map<ResourceLocation, TrinketInfo> TRINKET_ITEMS = new HashMap<>();

    public static final TagKey<Item> RING_TAG = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(CuriosApi.MODID, SlotTypePreset.RING.getIdentifier()));
    public static final TagKey<Item> BELT_TAG = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(CuriosApi.MODID, SlotTypePreset.BELT.getIdentifier()));
    public static final TagKey<Item> BRACELET_TAG = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(CuriosApi.MODID, SlotTypePreset.BRACELET.getIdentifier()));
    public static final TagKey<Item> CHARM_TAG = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(CuriosApi.MODID, SlotTypePreset.CHARM.getIdentifier()));
    public static final TagKey<Item> NECKLACE_TAG = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(CuriosApi.MODID, SlotTypePreset.NECKLACE.getIdentifier()));
    public static final TagKey<Item> HEAD_TAG = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(CuriosApi.MODID, SlotTypePreset.HEAD.getIdentifier()));
    public static final TagKey<Item> BODY_TAG = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(CuriosApi.MODID, SlotTypePreset.BODY.getIdentifier()));

    public static final RegistryObject<TrinketItem> GOLD_RING = trinket("gold_ring", "item/gold_ring", "Base golden ring item", RING_TAG);
    public static final RegistryObject<TrinketItem> GOLD_RING_BLUE = trinket("gold_ring_blue", "item/gold_ring_blue", "Base golden ring item", RING_TAG);
    public static final RegistryObject<TrinketItem> GOLD_RING_GREEN = trinket("gold_ring_green", "item/gold_ring_green", "Base golden ring item", RING_TAG);
    public static final RegistryObject<TrinketItem> GOLD_RING_RED = trinket("gold_ring_red", "item/gold_ring_red", "Base golden ring item", RING_TAG);
    public static final RegistryObject<TrinketItem> GOLD_RING_DIAMOND = trinket("gold_ring_diamond", "item/gold_ring_diamond", "Base golden ring item", RING_TAG);
    public static final RegistryObject<TrinketItem> SILVER_RING = trinket("silver_ring", "item/silver_ring", "Base silver ring item", RING_TAG);
    public static final RegistryObject<TrinketItem> SILVER_RING_BLUE = trinket("silver_ring_blue", "item/silver_ring_blue", "Base silver ring item", RING_TAG);
    public static final RegistryObject<TrinketItem> SILVER_RING_GREEN = trinket("silver_ring_green", "item/silver_ring_green", "Base silver ring item", RING_TAG);
    public static final RegistryObject<TrinketItem> SILVER_RING_RED = trinket("silver_ring_red", "item/silver_ring_red", "Base silver ring item", RING_TAG);
    public static final RegistryObject<TrinketItem> SILVER_RING_DIAMOND = trinket("silver_ring_diamond", "item/silver_ring_diamond", "Base silver ring item", RING_TAG);
    public static final RegistryObject<TrinketItem> OBSIDIAN_RING = trinket("obsidian_ring", "item/obsidian_ring", "Base obsidian ring item", RING_TAG);
    public static final RegistryObject<TrinketItem> OBSIDIAN_RING_DIAMOND = trinket("obsidian_ring_diamond", "item/obsidian_ring_diamond", "Base obsidian ring item", RING_TAG);

    public static final RegistryObject<TrinketItem> STAR = trinket("star", "item/star", "Base star item",
            RING_TAG, BELT_TAG, BRACELET_TAG, CHARM_TAG, NECKLACE_TAG, HEAD_TAG, BODY_TAG);
    public static final RegistryObject<TrinketItem> HEART = trinket("heart", "item/heart", "Base heart item",
            RING_TAG, BELT_TAG, BRACELET_TAG, CHARM_TAG, NECKLACE_TAG, HEAD_TAG, BODY_TAG);
    public static final RegistryObject<TrinketItem> HEART_BODY = trinket("heart_body", "item/heart", "Base heart item (body)", BODY_TAG);

    public static final RegistryObject<TrinketItem> FEATHER = trinket("feather", "item/feather", "Feather star item",
            BELT_TAG, CHARM_TAG, NECKLACE_TAG, HEAD_TAG, BODY_TAG);

    public static final RegistryObject<TrinketItem> BLACK_PEARL = trinket("black_pearl", "item/black_pearl", "Black pearl", CHARM_TAG);
    public static final RegistryObject<TrinketItem> BLUE_PEARL = trinket("blue_pearl", "item/blue_pearl", "Blue pearl", CHARM_TAG);
    public static final RegistryObject<TrinketItem> YELLOW_PEARL = trinket("yellow_pearl", "item/yellow_pearl", "Yellow pearl", CHARM_TAG);
    public static final RegistryObject<TrinketItem> PURPLE_PEARL = trinket("purple_pearl", "item/purple_pearl", "Purple pearl", CHARM_TAG);
    public static final RegistryObject<TrinketItem> SHINY_PEARL = trinket("shiny_pearl", "item/shiny_pearl", "Shiny pearl", CHARM_TAG);

    public static final RegistryObject<TrinketItem> LEATHER_BELT = trinket("leather_belt", "item/leather_belt", "Leather Belt", BELT_TAG);
    public static final RegistryObject<TrinketItem> BLUE_BELT = trinket("blue_belt", "item/blue_belt", "Blue Belt", BELT_TAG);

    public static final RegistryObject<TrinketItem> CHARM1 = trinket("charm1", "item/charm1", "Charm", CHARM_TAG);
    public static final RegistryObject<TrinketItem> CHARM2 = trinket("charm2", "item/charm2", "Charm", CHARM_TAG);

    public static final Capability<ICurio> CURIOS_CAPABILITY = CuriosCapability.ITEM;

    public TrinketsModule() {
        MinecraftForge.EVENT_BUS.addListener(this::onServerStarting);

        DefaultTrinkets.init();
        DefaultBonusTables.init();
    }

    public static RegistryObject<TrinketItem> trinket(String id, String texture, String description, TagKey... tags) {
        RegistryObject<TrinketItem> object = Registration.ITEMS.register(id, tab(TrinketItem::new));
        TRINKET_ITEMS.put(object.getId(), new TrinketInfo(new ResourceLocation(MODID, id), texture, description, object, tags));
        return object;
    }

    @Override
    public void init(FMLCommonSetupEvent event) {
    }

    public void onServerStarting(ServerStartingEvent event) {
        registerTrinkets(event.getServer().overworld());
    }

    private void registerTrinkets(ServerLevel level) {
        Registry<TrinketDescription> registry = level.registryAccess().registryOrThrow(CustomRegistries.TRINKET_REGISTRY_KEY);
        for (ResourceLocation trinket : level.registryAccess().registryOrThrow(CustomRegistries.TRINKET_SET_REGISTRY_KEY).get(new ResourceLocation(MODID, "standard")).trinkets()) {
            TrinketDescription description = registry.get(trinket);
            ResourceLocation itemId = description.item();
            Item item = ForgeRegistries.ITEMS.getValue(itemId);
            if (item == null || item == Items.AIR) {
                throw new RuntimeException("Can't find item '" + itemId.toString() + "'!");
            }
            if (item instanceof ITrinketItem trinketItem) {
                trinketItem.registerTrinketInstance(level, trinket, description);
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

    @Override
    public void initDatagen(DataGen dataGen) {
        for (Map.Entry<ResourceLocation, TrinketsModule.TrinketInfo> entry : TrinketsModule.TRINKET_ITEMS.entrySet()) {
            TrinketsModule.TrinketInfo trinket = entry.getValue();
            dataGen.add(
                    Dob.itemBuilder(trinket.item())
                            .name(trinket.description())
                            .generatedItem(trinket.texture())
                            .itemTags(List.of(trinket.tags()))
            );
        }
    }

    public static record TrinketInfo(ResourceLocation id, String texture,
                                     String description,
                                     RegistryObject<TrinketItem> item,
                                     TagKey<Item>... tags) {
    }
}
