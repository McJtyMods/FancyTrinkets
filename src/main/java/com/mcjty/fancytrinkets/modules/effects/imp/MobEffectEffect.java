package com.mcjty.fancytrinkets.modules.effects.imp;

import com.mcjty.fancytrinkets.modules.effects.IEffect;
import com.mcjty.fancytrinkets.playerdata.PlayerEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class MobEffectEffect implements IEffect {

    private final ResourceLocation id;
    private final MobEffect effect;
    private final int strength;

    private final MobEffectInstance instance;

    private MobEffectEffect(ResourceLocation id, MobEffect effect, int strength) {
        this.id = id;
        this.effect = effect;
        this.strength = strength;
        instance = new MobEffectInstance(effect, 20*4, strength-1);
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public void tick(ItemStack stack, Entity entity, int index) {
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.getCapability(PlayerEffects.PLAYER_EFFECTS).ifPresent(playerEffects -> {
                long gameTime = livingEntity.level.getGameTime();
                playerEffects.getEffectMap().put(index, new PlayerEffects.EffectHolder(this, gameTime + 4*20));
            });
        }
    }

    @Override
    public void perform(ServerPlayer player, int strength) {
        player.addEffect(new MobEffectInstance(effect, 20*4, strength-1));
    }

    public static Builder builder(ResourceLocation id) {
        return new Builder(id);
    }

    public static class Builder {
        private final ResourceLocation id;
        private MobEffect effect;
        private int strength = 1;

        public Builder(ResourceLocation id) {
            this.id = id;
        }

        public Builder effect(MobEffect effect) {
            this.effect = effect;
            return this;
        }

        public Builder strength(int strength) {
            this.strength = strength;
            return this;
        }

        public MobEffectEffect build() {
            if (effect == null) {
                throw new RuntimeException("No effect given for '"  + id + "'!");
            }
            return new MobEffectEffect(id, effect, strength);
        }
    }
}
