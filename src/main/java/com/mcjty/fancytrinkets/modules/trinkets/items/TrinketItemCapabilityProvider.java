package com.mcjty.fancytrinkets.modules.trinkets.items;

import com.mcjty.fancytrinkets.api.ITrinketItem;
import com.mcjty.fancytrinkets.modules.trinkets.TrinketsModule;
import com.mcjty.fancytrinkets.setup.Registration;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;

public class TrinketItemCapabilityProvider implements ICapabilityProvider {

    private final ItemStack itemStack;
    private final TrinketItem trinketItem;

    private final LazyOptional<ICurio> curio = LazyOptional.of(this::createCurio);
    private final LazyOptional<ITrinketItem> trinket = LazyOptional.of(this::getTrinket);

    public TrinketItemCapabilityProvider(ItemStack itemStack, TrinketItem trinketItem) {
        this.itemStack = itemStack;
        this.trinketItem = trinketItem;
    }

    @Nonnull
    private ITrinketItem getTrinket() {
        return trinketItem;
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
                if (slotContext.entity() instanceof ServerPlayer player) {
                    trinketItem.forAllEffects(player.level, itemStack, effect -> effect.tick(itemStack, player, slotContext.identifier()+slotContext.index()));
                }
            }

            @Override
            public void onEquip(SlotContext slotContext, ItemStack prevStack) {
                if (slotContext.entity() instanceof ServerPlayer player) {
                    trinketItem.forAllEffects(player.level, itemStack, effect -> effect.onEquip(itemStack, player, slotContext.identifier() + slotContext.index()));
                }
            }

            @Override
            public void onUnequip(SlotContext slotContext, ItemStack newStack) {
                if (slotContext.entity() instanceof ServerPlayer player) {
                    trinketItem.forAllEffects(player.level, itemStack, effect -> effect.onUnequip(itemStack, player, slotContext.identifier() + slotContext.index()));
                }
            }
        };
    }

    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == TrinketsModule.CURIOS_CAPABILITY) {
            return curio.cast();
        }
        if (cap == Registration.TRINKET_ITEM_CAPABILITY) {
            return trinket.cast();
        }
        return LazyOptional.empty();
    }
}
