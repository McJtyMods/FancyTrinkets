package com.mcjty.fancytrinkets.modules.effects.imp;

import com.mcjty.fancytrinkets.modules.effects.IEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class FlightEffect implements IEffect {

    private final ResourceLocation id;

    public FlightEffect(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public void tick(ItemStack stack, Entity entity, String slotId) {
        if (entity instanceof ServerPlayer serverPlayer) {
            if (!serverPlayer.isCreative()) {
                if (!serverPlayer.getAbilities().mayfly) {
                    serverPlayer.getAbilities().mayfly = true;
                    serverPlayer.onUpdateAbilities();
                }
            }
        }
    }

    @Override
    public void onUnequip(ItemStack stack, Entity entity, String slotId) {
        if (entity instanceof ServerPlayer serverPlayer) {
            if (!serverPlayer.isCreative()) {
                serverPlayer.getAbilities().flying = false;
                serverPlayer.getAbilities().mayfly = false;
                serverPlayer.onUpdateAbilities();
            }
        }
    }
}
