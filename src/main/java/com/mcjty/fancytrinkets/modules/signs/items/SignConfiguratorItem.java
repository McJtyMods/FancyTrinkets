package com.mcjty.fancytrinkets.modules.signs.items;

import mcjty.lib.builder.TooltipBuilder;
import mcjty.lib.tooltips.ITooltipSettings;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

import static mcjty.lib.builder.TooltipBuilder.*;

public class SignConfiguratorItem extends Item implements ITooltipSettings {

    private final Lazy<TooltipBuilder> tooltipBuilder = () -> new TooltipBuilder()
            .info(key("message.fancytrinkets.shiftmessage"))
            .infoShift(header(), gold());

    public SignConfiguratorItem() {
        super(new Properties()
                .stacksTo(1)
                .tab(com.mcjty.fancytrinkets.FancyTrinkets.setup.getTab()));
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Level world, List<Component> list, TooltipFlag flags) {
        super.appendHoverText(itemStack, world, list, flags);
        tooltipBuilder.get().makeTooltip(ForgeRegistries.ITEMS.getKey(this), itemStack, list, flags);
    }

}
