package com.mcjty.fancytrinkets.setup;


import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.modules.trinkets.items.TrinketItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

import static com.mcjty.fancytrinkets.FancyTrinkets.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Registration {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID);

    public static void register() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(bus);
        ITEMS.register(bus);
        TILES.register(bus);
        CONTAINERS.register(bus);
    }

    public static Item.Properties createStandardProperties() {
        return new Item.Properties().tab(FancyTrinkets.setup.getTab());
    }

    public static final Map<ResourceLocation, TrinketInfo> TRINKETS = new HashMap<>();

    public static RegistryObject<TrinketItem> trinket(String id, String texture, String description, String header, String extraInformation,
                                                      int slots, ResourceLocation... effects) {
        RegistryObject<TrinketItem> object = ITEMS.register(id, () -> new TrinketItem(effects));
        TRINKETS.put(object.getId(), new TrinketInfo(new ResourceLocation(MODID, id), texture, description, header, extraInformation, slots, object));
        return object;
    }

    public static record TrinketInfo(ResourceLocation id, String texture,
                                     String description, String header, String extraInformation,
                                     int slots,
                                     RegistryObject<TrinketItem> item) {

        public boolean hasSlot(int slot) {
            return (slots & slot) != 0;
        }
    }
}
