package com.mcjty.fancytrinkets.datagen;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.modules.trinkets.TrinketsModule;
import com.mcjty.fancytrinkets.modules.trinkets.items.TrinketItem;
import com.mcjty.fancytrinkets.setup.Registration;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypePreset;

import javax.annotation.Nonnull;

public class ItemTags extends ItemTagsProvider {

    public ItemTags(DataGenerator generator, BlockTagsProvider blockTags, ExistingFileHelper helper) {
        super(generator, blockTags, FancyTrinkets.MODID, helper);
    }

    public static final TagKey<Item> RING_TAG = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(CuriosApi.MODID, SlotTypePreset.RING.getIdentifier()));
    public static final TagKey<Item> BELT_TAG = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(CuriosApi.MODID, SlotTypePreset.BELT.getIdentifier()));
    public static final TagKey<Item> BRACELET_TAG = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(CuriosApi.MODID, SlotTypePreset.BRACELET.getIdentifier()));
    public static final TagKey<Item> CHARM_TAG = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(CuriosApi.MODID, SlotTypePreset.CHARM.getIdentifier()));
    public static final TagKey<Item> NECKLACE_TAG = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(CuriosApi.MODID, SlotTypePreset.NECKLACE.getIdentifier()));
    public static final TagKey<Item> HEAD_TAG = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(CuriosApi.MODID, SlotTypePreset.HEAD.getIdentifier()));
    public static final TagKey<Item> BODY_TAG = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(CuriosApi.MODID, SlotTypePreset.BODY.getIdentifier()));

    @Override
    protected void addTags() {
        tag(RING_TAG).add(TrinketsModule.GOLD_RING.get(),
                TrinketsModule.GOLD_RING_BLUE.get(),
                TrinketsModule.GOLD_RING_GREEN.get(),
                TrinketsModule.GOLD_RING_RED.get(),
                TrinketsModule.GOLD_RING_DIAMOND.get(),
                TrinketsModule.SILVER_RING.get(),
                TrinketsModule.SILVER_RING_BLUE.get(),
                TrinketsModule.SILVER_RING_GREEN.get(),
                TrinketsModule.SILVER_RING_RED.get(),
                TrinketsModule.SILVER_RING_DIAMOND.get(),
                TrinketsModule.OBSIDIAN_RING.get(),
                TrinketsModule.OBSIDIAN_RING_DIAMOND.get());

        tag(RING_TAG).add(TrinketsModule.STAR.get());
        tag(BELT_TAG).add(TrinketsModule.STAR.get());
        tag(BRACELET_TAG).add(TrinketsModule.STAR.get());
        tag(CHARM_TAG).add(TrinketsModule.STAR.get());
        tag(NECKLACE_TAG).add(TrinketsModule.STAR.get());
        tag(HEAD_TAG).add(TrinketsModule.STAR.get());
        tag(BODY_TAG).add(TrinketsModule.STAR.get());
    }

    @Nonnull
    @Override
    public String getName() {
        return "FancyTrinkets Tags";
    }
}
