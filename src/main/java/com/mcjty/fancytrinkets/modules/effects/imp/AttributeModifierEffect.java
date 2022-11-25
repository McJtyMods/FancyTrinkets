package com.mcjty.fancytrinkets.modules.effects.imp;

import com.mcjty.fancytrinkets.datapack.EffectDescription;
import com.mcjty.fancytrinkets.datapack.IEffectParameters;
import com.mcjty.fancytrinkets.modules.effects.IEffect;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

public class AttributeModifierEffect implements IEffect {

    private final UUID uuid;
    private final AttributeModifier modifier;
    private final Supplier<Attribute> attribute;
    private final Integer hotkey;
    private final String toggle;

    public static record Params(String effect, AttributeModifier.Operation operation, Double amount) implements IEffectParameters {
        @Override
        public EffectDescription.EffectType getType() {
            return EffectDescription.EffectType.ATTRIBUTE;
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
                    Codec.STRING.fieldOf("operation").forGetter(l -> ((Params)l).operation.name().toLowerCase()),
                    Codec.DOUBLE.fieldOf("amount").forGetter(l -> ((Params)l).amount)
            ).apply(instance, (effect, operation, amount) -> new Params(effect, AttributeModifier.Operation.valueOf(operation.toUpperCase()), amount)));


    public AttributeModifierEffect(Integer hotkey, String toggle, String name, Supplier<Attribute> attibute, AttributeModifier.Operation operation, double amount) {
        this.hotkey = hotkey;
        this.toggle = toggle;
        this.attribute = attibute;
        this.uuid = UUID.randomUUID();
        modifier = new AttributeModifier(uuid, name, amount, operation);
    }

    @Override
    public void tick(ItemStack stack, Entity entity, String slotId) {
        if (entity instanceof ServerPlayer serverPlayer) {
            if (!serverPlayer.isCreative()) {
                AttributeInstance instance = serverPlayer.getAttribute(attribute.get());
                if (instance != null && instance.getModifier(uuid) == null) {
                    instance.addPermanentModifier(modifier);
                }
            }
        }
    }

    @Override
    public void onUnequip(ItemStack stack, Entity entity, String slotId) {
        if (entity instanceof ServerPlayer serverPlayer) {
            if (!serverPlayer.isCreative()) {
                AttributeInstance instance = serverPlayer.getAttribute(attribute.get());
                if (instance != null) {
                    instance.removePermanentModifier(uuid);
                }
            }
        }
    }
}
