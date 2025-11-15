package com.mcjty.fancytrinkets.setup;


import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.api.ITrinketItem;
import com.mcjty.fancytrinkets.modules.trinkets.TrinketsModule;
import com.mcjty.fancytrinkets.modules.trinkets.items.TrinketItem;
import com.mcjty.fancytrinkets.playerdata.PlayerEffectData;
import com.mojang.serialization.MapCodec;
import mcjty.lib.setup.DeferredBlocks;
import mcjty.lib.setup.DeferredItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import net.neoforged.neoforge.capabilities.ItemCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.function.Supplier;

import static com.mcjty.fancytrinkets.FancyTrinkets.MODID;

public class Registration {

    public static final DeferredBlocks BLOCKS = DeferredBlocks.create(MODID);
    public static final DeferredItems ITEMS = DeferredItems.create(MODID);
    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, MODID);
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(BuiltInRegistries.MENU, MODID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, MODID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, MODID);
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MODID);
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, MODID);

    public static final ItemCapability<ITrinketItem, Void> TRINKET_ITEM_CAPABILITY = ItemCapability.createVoid(ResourceLocation.fromNamespaceAndPath(MODID, "trinket_item"), ITrinketItem.class);

    public static final Supplier<AttachmentType<PlayerEffectData>> PLAYER_EFFECTS = ATTACHMENT_TYPES.register(
            "player_effects", () -> AttachmentType.builder(() -> PlayerEffectData.DEFAULT)
                    .serialize(PlayerEffectData.CODEC)
                    .build());

    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
        ITEMS.register(bus);
        TILES.register(bus);
        CONTAINERS.register(bus);
        RECIPE_TYPES.register(bus);
        RECIPE_SERIALIZERS.register(bus);
        LOOT_MODIFIER_SERIALIZERS.register(bus);
        TABS.register(bus);
        ATTACHMENT_TYPES.register(bus);
    }

    public static Item.Properties createStandardProperties() {
        return FancyTrinkets.setup.defaultProperties();
    }

    public static Supplier<CreativeModeTab> TAB = TABS.register("fancytrinkets", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + MODID))
            .icon(() -> new ItemStack(TrinketsModule.GOLD_RING.get()))
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .displayItems((featureFlags, output) -> {
                FancyTrinkets.setup.populateTab(output);
            })
            .build());

    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.registerItem(CuriosCapability.ITEM, new ICapabilityProvider<ItemStack, Void, ICurio>() {
            @Override
            public @Nullable ICurio getCapability(ItemStack stack, Void unused) {
                if (stack.getItem() instanceof TrinketItem trinketItem) {
                    return trinketItem.createCurio(stack);
                }
                return null;
            }
        });
        event.registerItem(TRINKET_ITEM_CAPABILITY, new ICapabilityProvider<ItemStack, Void, ITrinketItem>() {
            @Override
            public @Nullable ITrinketItem getCapability(ItemStack stack, Void unused) {
                if (stack.getItem() instanceof TrinketItem trinketItem) {
                    return trinketItem;
                }
                return null;
            }
        });
    }

}
