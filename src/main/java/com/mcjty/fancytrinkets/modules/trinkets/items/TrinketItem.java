package com.mcjty.fancytrinkets.modules.trinkets.items;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.modules.effects.EffectsModule;
import com.mcjty.fancytrinkets.modules.effects.IEffect;
import mcjty.lib.builder.TooltipBuilder;
import mcjty.lib.tooltips.ITooltipSettings;
import mcjty.lib.varia.ComponentFactory;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

import static mcjty.lib.builder.TooltipBuilder.*;

public class TrinketItem extends Item implements ITooltipSettings {

    public static final String MESSAGE_FANCYTRINKETS_SHIFTMESSAGE = "message.fancytrinkets.shiftmessage";
    public static final String MESSAGE_EFFECT_HEADER = "effect.fancytrinkets.header";

    private final ResourceLocation[] effectIds;

    private final Lazy<TooltipBuilder> tooltipBuilder = () -> new TooltipBuilder()
            .info(key(MESSAGE_FANCYTRINKETS_SHIFTMESSAGE))
            .infoShift(header(), gold());

    public TrinketItem(ResourceLocation... effectIds) {
        super(new Properties()
                .stacksTo(1)
                .tab(FancyTrinkets.setup.getTab()));
        this.effectIds = effectIds;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, Level world, List<Component> list, TooltipFlag flags) {
        super.appendHoverText(itemStack, world, list, flags);
        tooltipBuilder.get().makeTooltip(ForgeRegistries.ITEMS.getKey(this), itemStack, list, flags);
        for (ResourceLocation id : effectIds) {
            IEffect effect = EffectsModule.EFFECTS.get(id).effect();
            list.add(ComponentFactory.translatable(MESSAGE_EFFECT_HEADER).withStyle(ChatFormatting.AQUA)
                    .append(((MutableComponent)effect.getDescription()).withStyle(ChatFormatting.WHITE)));
        }
    }

}
