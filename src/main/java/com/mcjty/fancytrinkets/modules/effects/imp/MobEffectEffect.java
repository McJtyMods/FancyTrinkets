package com.mcjty.fancytrinkets.modules.effects.imp;

import com.mcjty.fancytrinkets.datapack.EffectDescription;
import com.mcjty.fancytrinkets.datapack.IEffectParameters;
import com.mcjty.fancytrinkets.modules.effects.IEffect;
import com.mcjty.fancytrinkets.playerdata.PlayerEffects;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class MobEffectEffect implements IEffect {

    private final MobEffect effect;
    private final int strengthModifier;
    private final Integer hotkey;
    private final String toggle;

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
                    Codec.STRING.fieldOf("effect").forGetter(l -> ((Params)l).effect),
                    Codec.INT.fieldOf("strength").forGetter(l -> ((Params)l).strength)
            ).apply(instance, Params::new));

    public MobEffectEffect(Integer hotkey, String toggle, MobEffect effect, int strengthModifier) {
        this.hotkey = hotkey;
        this.toggle =toggle;
        this.effect = effect;
        this.strengthModifier = strengthModifier;
    }

    @Override
    public void tick(ItemStack stack, Entity entity, String slotId) {
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.getCapability(PlayerEffects.PLAYER_EFFECTS).ifPresent(playerEffects -> {
                if (toggle != null) {
                    if (!playerEffects.isToggleOn(toggle)) {
                        return;
                    }
                }
                long gameTime = livingEntity.level.getGameTime();
                playerEffects.registerEffect(slotId, this, gameTime + 4*20);
            });
        }
    }

    @Override
    public void onHotkey(ItemStack stack, Entity entity, String slotId, int key) {
        if (toggle != null) {
            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.getCapability(PlayerEffects.PLAYER_EFFECTS).ifPresent(playerEffects -> {
                    if (!playerEffects.toggle(toggle)) {
                        playerEffects.unregisterEffect(slotId);
                    }
                });
            }
        }
    }

    @Override
    public void perform(ServerPlayer player, int strength) {
        player.addEffect(new MobEffectInstance(effect, 20*2, strengthModifier + strength-1));
    }
}
