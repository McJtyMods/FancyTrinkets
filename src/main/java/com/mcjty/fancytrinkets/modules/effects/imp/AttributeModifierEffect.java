package com.mcjty.fancytrinkets.modules.effects.imp;

import com.mcjty.fancytrinkets.datapack.EffectDescription;
import com.mcjty.fancytrinkets.datapack.IEffectParameters;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

public class AttributeModifierEffect extends EffectImp {

    private final ResourceLocation id;
    private final AttributeModifier modifier;
    private final Holder<Attribute> attribute;

    public record Params(String effect, AttributeModifier.Operation operation, Double amount) implements IEffectParameters {
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

    public static final MapCodec<IEffectParameters> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.STRING.fieldOf("effectId").forGetter(l -> ((Params)l).effect),
                    Codec.STRING.fieldOf("operation").forGetter(l -> ((Params)l).operation.name().toLowerCase()),
                    Codec.DOUBLE.fieldOf("amount").forGetter(l -> ((Params)l).amount)
            ).apply(instance, (effect, operation, amount) -> new Params(effect, AttributeModifier.Operation.valueOf(operation.toUpperCase()), amount)));


    public AttributeModifierEffect(ResourceLocation id, Integer hotkey, String toggle, String name, Holder<Attribute> attibute, AttributeModifier.Operation operation, double amount) {
        super(hotkey, toggle);
        this.attribute = attibute;
        this.id = id;
        modifier = new AttributeModifier(id, amount, operation);
    }

    @Override
    public void tick(ItemStack stack, ServerPlayer player, String slotId) {
        if (!player.isCreative()) {
            executeIfEnabled(player, () -> {
                AttributeInstance instance = player.getAttribute(attribute);
                if (instance != null && instance.getModifier(id) == null) {
                    instance.addTransientModifier(modifier);
                }
            });
        }
    }


    @Override
    protected void turnOff(ServerPlayer player) {
        if (!player.isCreative()) {
            AttributeInstance instance = player.getAttribute(attribute);
            if (instance != null) {
                instance.removeModifier(id);
            }
        }
    }
}
