package com.mcjty.fancytrinkets.setup;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.api.ITrinketItem;
import com.mcjty.fancytrinkets.modules.trinkets.TrinketsModule;
import com.mcjty.fancytrinkets.modules.trinkets.items.TrinketItem;
import com.mcjty.fancytrinkets.playerdata.PlayerEffectData;
import com.mojang.serialization.MapCodec;
import mcjty.lib.blocks.RBlockRegistry;
import mcjty.lib.setup.DeferredItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import mcjty.lib.setup.DeferredRegister;

import java.util.function.Supplier;

import static com.mcjty.fancytrinkets.FancyTrinkets.MODID;

public class Registration {
    public static final net.minecraft.core.Holder<net.minecraft.world.entity.ai.attributes.Attribute> SWIM_SPEED =
            net.minecraft.core.Registry.registerForHolder(BuiltInRegistries.ATTRIBUTE, Identifier.parse("fancytrinkets:swim_speed"),
                    new net.minecraft.world.entity.ai.attributes.RangedAttribute("attribute.fancytrinkets.swim_speed", 1, 0, 1024).setSyncable(true));

    public static final net.fabricmc.fabric.api.lookup.v1.item.ItemApiLookup<ITrinketItem, Void> TRINKET_ITEM =
            net.fabricmc.fabric.api.lookup.v1.item.ItemApiLookup.get(Identifier.fromNamespaceAndPath(MODID, "trinket_item"), ITrinketItem.class, Void.class);
    public static final net.fabricmc.fabric.api.attachment.v1.AttachmentType<PlayerEffectData> PLAYER_EFFECTS =
            net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry.create(Identifier.fromNamespaceAndPath(MODID, "player_effects"),
                    builder -> builder.initializer(() -> PlayerEffectData.DEFAULT).persistent(PlayerEffectData.CODEC).copyOnDeath());


    public static final RBlockRegistry RBLOCKS = new RBlockRegistry(MODID, FancyTrinkets.setup::addTabItem);
    public static final DeferredItems ITEMS = DeferredItems.create(MODID);
    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MODID);
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(Registries.MENU, MODID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, MODID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, MODID);
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final DeferredRegister.DataComponents COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, MODID);

    public static void register() {
        RBLOCKS.register();
        ITEMS.register();
        TILES.register();
        CONTAINERS.register();
        RECIPE_TYPES.register();
        RECIPE_SERIALIZERS.register();
        TABS.register();
        COMPONENTS.register();
        TRINKET_ITEM.registerFallback((stack, context) -> stack.getItem() instanceof ITrinketItem trinket ? trinket : null);
    }

    public static Item.Properties createStandardProperties() {
        return FancyTrinkets.setup.defaultProperties();
    }

    public static Supplier<CreativeModeTab> TAB = TABS.register("fancytrinkets", () -> net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + MODID))
            .icon(() -> new ItemStack(TrinketsModule.GOLD_RING.get()))
            .displayItems((featureFlags, output) -> {
                FancyTrinkets.setup.populateTab(output);
            })
            .build());

}
