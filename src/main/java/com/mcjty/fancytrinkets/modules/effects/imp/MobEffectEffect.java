package com.mcjty.fancytrinkets.modules.effects.imp;

import com.mcjty.fancytrinkets.modules.effects.IEffect;
import mcjty.lib.varia.ComponentFactory;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MobEffectEffect implements IEffect {

    private final ResourceLocation id;
    private final MobEffect effect;
    private final int strength;

    private MobEffectEffect(ResourceLocation id, MobEffect effect, int strength) {
        this.id = id;
        this.effect = effect;
        this.strength = strength;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public Component getDescription() {
        return ComponentFactory.translatable("effect." + id.getNamespace() + "." + id.getPath());
//                .append(((MutableComponent)effect.getDisplayName()).withStyle(ChatFormatting.GREEN)).append(" (" + strength + ")");
    }

    @Override
    public void tick(ItemStack stack, Level level, Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            MobEffectInstance instance = new MobEffectInstance(effect, 4);
            livingEntity.addEffect(instance);
        }
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
