package com.mcjty.fancytrinkets.modules.trinkets.items;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.datapack.TrinketDescription;
import com.mcjty.fancytrinkets.modules.trinkets.ITrinketItem;
import com.mcjty.fancytrinkets.modules.trinkets.TrinketInstance;
import mcjty.lib.builder.TooltipBuilder;
import mcjty.lib.tooltips.ITooltipSettings;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

import static mcjty.lib.builder.TooltipBuilder.*;

public class TrinketItem extends Item implements ITooltipSettings, ITrinketItem {

    public static final String MESSAGE_FANCYTRINKETS_SHIFTMESSAGE = "message.fancytrinkets.shiftmessage";
    public static final String MESSAGE_EFFECT_HEADER = "effect.fancytrinkets.header";

    private final List<TrinketInstance> trinkets = new ArrayList<>();

    private final Lazy<TooltipBuilder> tooltipBuilder = () -> new TooltipBuilder()
            .info(key(MESSAGE_FANCYTRINKETS_SHIFTMESSAGE))
            .infoShift(header(), gold());

    public TrinketItem() {
        super(new Properties()
                .stacksTo(1)
                .tab(FancyTrinkets.setup.getTab()));
    }

    @Override
    public void registerTrinketInstance(TrinketDescription description) {
        trinkets.add(description.build());
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Level world, List<Component> list, TooltipFlag flags) {
        super.appendHoverText(itemStack, world, list, flags);
        tooltipBuilder.get().makeTooltip(ForgeRegistries.ITEMS.getKey(this), itemStack, list, flags);
//        for (ResourceLocation id : effectIds) {
//            IEffect effect = EffectsModule.EFFECTS.get(id).effect();
//            list.add(ComponentFactory.translatable(MESSAGE_EFFECT_HEADER).withStyle(ChatFormatting.AQUA)
//                    .append(((MutableComponent)effect.getDescription()).withStyle(ChatFormatting.WHITE)));
//        }
    }

}
