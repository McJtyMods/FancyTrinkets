package com.mcjty.fancytrinkets.modules.trinkets.items;

import com.mcjty.fancytrinkets.modules.trinkets.TrinketsModule;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;

public class CuriosCapabilityProvider implements ICapabilityProvider {

    private final ItemStack itemStack;
    private final TrinketItem trinketItem;

    private final LazyOptional<ICurio> curio = LazyOptional.of(this::createCurio);

    public CuriosCapabilityProvider(ItemStack itemStack, TrinketItem trinketItem) {
        this.itemStack = itemStack;
        this.trinketItem = trinketItem;
    }

    @Nonnull
    private ICurio createCurio() {
        return new ICurio() {
            @Override
            public ItemStack getStack() {
                return itemStack;
            }

            @Override
            public void curioTick(SlotContext slotContext) {
                trinketItem.forAllEffects(itemStack, effect -> effect.tick(itemStack, slotContext.entity(), slotContext.index()));
            }

            @Override
            public void onEquip(SlotContext slotContext, ItemStack prevStack) {
                trinketItem.forAllEffects(itemStack, effect -> effect.onEquip(itemStack, slotContext.entity(), slotContext.index()));
            }

            @Override
            public void onUnequip(SlotContext slotContext, ItemStack newStack) {
                trinketItem.forAllEffects(itemStack, effect -> effect.onUnequip(itemStack, slotContext.entity(), slotContext.index()));
            }
        };
    }

    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == TrinketsModule.CURIOS_CAPABILITY) {
            return curio.cast();
        }
        return LazyOptional.empty();
    }
}
