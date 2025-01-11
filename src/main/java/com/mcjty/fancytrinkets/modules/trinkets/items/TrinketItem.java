package com.mcjty.fancytrinkets.modules.trinkets.items;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.modules.trinkets.TrinketInstance;
import mcjty.lib.items.BaseItem;
import mcjty.lib.tooltips.ITooltipSettings;
import mcjty.lib.varia.SafeClientTools;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TrinketItem extends BaseItem implements ITooltipSettings {

    private final TrinketItemData tid = new TrinketItemData();

    public TrinketItem() {
        super(FancyTrinkets.setup.defaultProperties()
                .stacksTo(1));
    }

    @Override
    public List<ItemStack> getItemsForTab() {
        List<ItemStack> list = new ArrayList<>();
        for (TrinketInstance trinket : tid.getTrinkets(SafeClientTools.getClientWorld()).values()) {
            ItemStack stack = new ItemStack(this);
            toNBT(stack, trinket);
            list.add(stack);
        }
        return list;
    }

    public static void toNBT(ItemStack stack, TrinketInstance trinket) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putString("id", trinket.id().toString());
    }

//    @Override
//    public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flags) {
//        tid.appendHoverText(stack, world, list, flags);
//    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new TrinketItemCapabilityProvider(stack, () -> this.tid);
    }
}
