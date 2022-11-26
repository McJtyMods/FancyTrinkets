package com.mcjty.fancytrinkets.modules.effects.imp;

import com.mcjty.fancytrinkets.datapack.EffectDescription;
import com.mcjty.fancytrinkets.datapack.IEffectParameters;
import com.mcjty.fancytrinkets.modules.effects.DefaultEffect;
import com.mojang.serialization.Codec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Collection;

public class CureEffect extends DefaultEffect {

    public static record Params() implements IEffectParameters {
        public static final Params EMPTY = new Params();

        @Override
        public EffectDescription.EffectType getType() {
            return EffectDescription.EffectType.CURE;
        }
    }

    public static final Codec<IEffectParameters> CODEC = Codec.unit(Params.EMPTY);

    public CureEffect(Integer hotkey, String toggle) {
        super(hotkey, toggle);
    }

    @Override
    public void tick(ItemStack stack, ServerPlayer player, String slotId) {
        executeIfEnabled(player, () -> {
            Collection<MobEffectInstance> activeEffects = new ArrayList<>(player.getActiveEffects());
            for (MobEffectInstance effect : activeEffects) {
                if (!effect.getEffect().isBeneficial()) {
                    player.removeEffect(effect.getEffect());
                }
            }
        });
    }
}
