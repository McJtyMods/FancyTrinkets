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

public class MobEffectEffect implements IEffect {

    private final ResourceLocation id;
    private final MobEffect effect;
    private final int strengthModifier;

    private MobEffectEffect(ResourceLocation id, MobEffect effect, int strengthModifier) {
        this.id = id;
        this.effect = effect;
        this.strengthModifier = strengthModifier;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public void tick(ItemStack stack, Entity entity, String slotId) {
        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.getCapability(PlayerEffects.PLAYER_EFFECTS).ifPresent(playerEffects -> {
                long gameTime = livingEntity.level.getGameTime();
                playerEffects.registerEffect(slotId, this, gameTime + 4*20);
            });
        }
    }

    @Override
    public void perform(ServerPlayer player, int strength) {
        player.addEffect(new MobEffectInstance(effect, 20*2, strengthModifier + strength-1));
    }

    public static Builder builder(ResourceLocation id) {
        return new Builder(id);
    }

    public static class Builder {
        private final ResourceLocation id;
        private MobEffect effect;
        private int strengthModifier = 0;

        public Builder(ResourceLocation id) {
            this.id = id;
        }

        public Builder effect(MobEffect effect) {
            this.effect = effect;
            return this;
        }

        public Builder strengthModifier(int strength) {
            this.strengthModifier = strength;
            return this;
        }

        public MobEffectEffect build() {
            if (effect == null) {
                throw new RuntimeException("No effect given for '"  + id + "'!");
            }
            return new MobEffectEffect(id, effect, strengthModifier);
        }
    }
}
