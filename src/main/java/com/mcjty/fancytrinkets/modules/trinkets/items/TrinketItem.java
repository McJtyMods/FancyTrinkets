package com.mcjty.fancytrinkets.modules.trinkets.items;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.datapack.TrinketDescription;
import com.mcjty.fancytrinkets.modules.effects.IEffect;
import com.mcjty.fancytrinkets.modules.effects.EffectInstance;
import com.mcjty.fancytrinkets.modules.trinkets.ITrinketItem;
import com.mcjty.fancytrinkets.modules.trinkets.TrinketInstance;
import mcjty.lib.tooltips.ITooltipSettings;
import mcjty.lib.varia.ComponentFactory;
import mcjty.lib.varia.Tools;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrinketItem extends Item implements ITooltipSettings, ITrinketItem {

    public static final String MESSAGE_FANCYTRINKETS_SHIFTMESSAGE = "message.fancytrinkets.shiftmessage";
    public static final String MESSAGE_EFFECT_HEADER = "effect.fancytrinkets.header";

    private final Map<ResourceLocation, TrinketInstance> trinkets = new HashMap<>();

    public TrinketItem() {
        super(new Properties()
                .stacksTo(1)
                .tab(FancyTrinkets.setup.getTab()));
    }

    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> list) {
        if (allowedIn(tab)) {
            for (TrinketInstance trinket : trinkets.values()) {
                ItemStack stack = new ItemStack(this);
                toNBT(stack, trinket);
                list.add(stack);
            }
        }
    }

    public static void toNBT(ItemStack stack, TrinketInstance trinket) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putString("id", trinket.id().toString());
    }

    public static ResourceLocation getTrinketId(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains("id")) {
            return new ResourceLocation(tag.getString("id"));
        } else {
            return null;
        }
    }

    @Override
    public void registerTrinketInstance(TrinketDescription description) {
        trinkets.put(description.id(), description.build());
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean isSelected) {
        ResourceLocation trinketId = getTrinketId(stack);
        if (trinketId != null) {
            TrinketInstance instance = trinkets.get(trinketId);
            if (instance != null) {
                for (EffectInstance effect : instance.effects()) {
                    effect.effect().tick(stack, level, entity);
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flags) {
        ResourceLocation trinketId = getTrinketId(stack);
        if (trinketId != null) {
            TrinketInstance instance = trinkets.get(trinketId);
            if (instance != null) {
                list.add(ComponentFactory.translatable(instance.nameKey()).withStyle(ChatFormatting.AQUA));
                list.add(ComponentFactory.translatable(instance.descriptionKey()));
                for (EffectInstance effectInstance : instance.effects()) {
                    IEffect effect  = effectInstance.effect();
                    list.add(ComponentFactory.translatable(MESSAGE_EFFECT_HEADER).withStyle(ChatFormatting.AQUA)
                            .append(((MutableComponent)effect.getDescription()).withStyle(ChatFormatting.WHITE)));
                }
                return;
            }
        }
        ResourceLocation id = Tools.getId(this);
        String namespace = id.getNamespace();
        String path = id.getPath();
        String prefix = "message." + namespace + "." + path + ".name";
        list.add(ComponentFactory.translatable(prefix).withStyle(ChatFormatting.AQUA));
    }

}
