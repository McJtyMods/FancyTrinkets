package com.mcjty.fancytrinkets.modules.effects.imp;

import com.mcjty.fancytrinkets.datapack.EffectDescription;
import com.mcjty.fancytrinkets.datapack.IEffectParameters;
import com.mcjty.fancytrinkets.modules.effects.IEffect;
import com.mojang.serialization.Codec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class FlightEffect implements IEffect {

    private final Integer hotkey;
    private final String toggle;

    public static record Params() implements IEffectParameters {
        public static final Params EMPTY = new Params();

        @Override
        public EffectDescription.EffectType getType() {
            return EffectDescription.EffectType.FLIGHT;
        }
    }

    public static final Codec<IEffectParameters> CODEC = Codec.unit(Params.EMPTY);

    public FlightEffect(Integer hotkey, String toggle) {
        this.hotkey = hotkey;
        this.toggle = toggle;
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
