package com.mcjty.fancytrinkets.modules.effects.imp;

import com.mcjty.fancytrinkets.datapack.EffectDescription;
import com.mcjty.fancytrinkets.datapack.IEffectParameters;
import com.mcjty.fancytrinkets.modules.effects.DefaultEffect;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.ItemStack;

public class PotionResistanceEffect extends DefaultEffect {

    private final MobEffect effect;

    public static record Params(String effect) implements IEffectParameters {
        @Override
        public EffectDescription.EffectType getType() {
            return EffectDescription.EffectType.POTIONRESISTANCE;
        }

        public static Params cast(IEffectParameters params) {
            if (params instanceof Params p) {
                return p;
            }
            throw new RuntimeException("Bad parameter type!");
        }
    }

    public static final Codec<IEffectParameters> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("effect").forGetter(l -> ((Params)l).effect)
            ).apply(instance, Params::new));

    public PotionResistanceEffect(Integer hotkey, String toggle, MobEffect effect) {
        super(hotkey, toggle);
        this.effect = effect;
    }

    @Override
    public void tick(ItemStack stack, ServerPlayer player, String slotId) {
        executeIfEnabled(player, () -> {
            player.removeEffect(effect);
        });
    }
}
