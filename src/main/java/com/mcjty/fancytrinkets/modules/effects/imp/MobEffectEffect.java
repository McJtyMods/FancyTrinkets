package com.mcjty.fancytrinkets.modules.effects.imp;

import com.mcjty.fancytrinkets.datapack.EffectDescription;
import com.mcjty.fancytrinkets.datapack.IEffectParameters;
import com.mcjty.fancytrinkets.playerdata.PlayerEffects;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public class MobEffectEffect extends EffectImp {

    private final MobEffect effect;
    private final int strengthModifier;

    public static record Params(String effect, int strength) implements IEffectParameters {
        @Override
        public EffectDescription.EffectType getType() {
            return EffectDescription.EffectType.MOBEFFECT;
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
                    Codec.STRING.fieldOf("effectId").forGetter(l -> ((Params)l).effect),
                    Codec.INT.fieldOf("strength").forGetter(l -> ((Params)l).strength)
            ).apply(instance, Params::new));

    public MobEffectEffect(Integer hotkey, String toggle, MobEffect effect, int strengthModifier) {
        super(hotkey, toggle);
        this.effect = effect;
        this.strengthModifier = strengthModifier;
    }

    @Override
    public void tick(ItemStack stack, ServerPlayer player, String slotId) {
        executeIfEnabled(player, playerEffects -> {
            long gameTime = player.level.getGameTime();
            playerEffects.registerEffect(slotId, this, gameTime + 12*20);
        });
    }

    @Override
    public void onUnequip(ItemStack stack, ServerPlayer player, String slotId) {
        player.getCapability(PlayerEffects.PLAYER_EFFECTS).ifPresent(playerEffects -> {
            playerEffects.unregisterEffect(slotId);
        });
        turnOff(player);
    }

    @Override
    public void onHotkey(ItemStack stack, ServerPlayer player, String slotId, int key) {
        if (toggle != null && Objects.equals(key, hotkey)) {
            player.getCapability(PlayerEffects.PLAYER_EFFECTS).ifPresent(playerEffects -> {
                if (!playerEffects.toggle(player, toggle)) {
                    playerEffects.unregisterEffect(slotId);
                    turnOff(player);
                }
            });
        }
    }

    @Override
    protected void turnOff(ServerPlayer player) {
        MobEffectInstance instance = player.getActiveEffectsMap().get(effect);
        if (instance != null) {
            player.removeEffect(instance.getEffect());
        }
    }

    @Override
    public void perform(ServerPlayer player, int strength) {
        MobEffectInstance instance = player.getActiveEffectsMap().get(effect);
        int amplifier = strengthModifier + strength - 1;
        if (instance == null || instance.getAmplifier() != amplifier || instance.getDuration() < 11*20) {
            player.addEffect(new MobEffectInstance(effect, 20*12, amplifier));
        }
    }
}
