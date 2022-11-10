package com.mcjty.fancytrinkets.datagen;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.curios.CuriosSetup;
import com.mcjty.fancytrinkets.setup.Registration;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypePreset;

import javax.annotation.Nonnull;
import java.util.Map;

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
        for (Map.Entry<ResourceLocation, Registration.TrinketInfo> entry : Registration.TRINKETS.entrySet()) {
            Registration.TrinketInfo info = entry.getValue();
            if (info.hasSlot(CuriosSetup.SLOT_RING)) {
                tag(RING_TAG).add(info.item().get());
            }
            if (info.hasSlot(CuriosSetup.SLOT_BELT)) {
                tag(BELT_TAG).add(info.item().get());
            }
            if (info.hasSlot(CuriosSetup.SLOT_BRACELET)) {
                tag(BRACELET_TAG).add(info.item().get());
            }
            if (info.hasSlot(CuriosSetup.SLOT_CHARM)) {
                tag(CHARM_TAG).add(info.item().get());
            }
            if (info.hasSlot(CuriosSetup.SLOT_NECKLACE)) {
                tag(NECKLACE_TAG).add(info.item().get());
            }
            if (info.hasSlot(CuriosSetup.SLOT_HEAD)) {
                tag(HEAD_TAG).add(info.item().get());
            }
            if (info.hasSlot(CuriosSetup.SLOT_BODY)) {
                tag(BODY_TAG).add(info.item().get());
            }
        }
    }

    @Nonnull
    @Override
    public String getName() {
        return "FancyTrinkets Tags";
    }
}
