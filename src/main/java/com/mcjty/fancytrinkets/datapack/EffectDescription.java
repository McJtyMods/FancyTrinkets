package com.mcjty.fancytrinkets.datapack;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.modules.effects.IEffect;
import com.mcjty.fancytrinkets.modules.effects.imp.*;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ResourceLocationException;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.common.NeoForgeMod;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.function.Supplier;

public record EffectDescription(Integer hotkey, String toggle, boolean harmful, IEffectParameters params, IEffect effect) {

    public static final Codec<IEffectParameters> PARAMS_CODEC = Codec.lazyInitialized(() -> Codec.STRING.<IEffectParameters>dispatch("type",
            s -> s.getType().name().toLowerCase(),
            EffectDescription::getParameterCodec));

    public static final Codec<EffectDescription> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.optionalFieldOf("hotkey").forGetter(l -> Optional.ofNullable(l.hotkey)),
                    Codec.STRING.optionalFieldOf("toggle").forGetter(l -> Optional.ofNullable(l.toggle)),
                    Codec.BOOL.optionalFieldOf("harmful").forGetter(l -> l.harmful ? Optional.of(l.harmful) : Optional.empty()),
                    PARAMS_CODEC.fieldOf("params").forGetter(l -> l.params)
            ).apply(instance, (hotkey, toggle, harmful, params) -> create(
                    hotkey.orElse(null),
                    toggle.orElse(null),
                    harmful.orElse(false),
                    params)));

    public static EffectDescription create(Integer hotkey, String toggle, boolean harmful, IEffectParameters params) {
        return new EffectDescription(hotkey, toggle, harmful, params, buildEffect(params, hotkey, toggle));
    }

    private static MapCodec<IEffectParameters> getParameterCodec(String stype) {
        EffectType type = EffectType.valueOf(stype.toUpperCase());
        return type.getCodecSupplier().get();
    }

    public enum EffectType {
        MOBEFFECT(() -> MobEffectEffect.CODEC),
        POTIONRESISTANCE(() -> PotionResistanceEffect.CODEC),
        DAMAGEREDUCTION(() -> DamageReductionEffect.CODEC),
        FLIGHT(() -> FlightEffect.CODEC),
        WARP(() -> WarpEffect.CODEC),
        CURE(() -> CureEffect.CODEC),
        ATTRIBUTE(() -> AttributeModifierEffect.CODEC),
        GROWTICK(() -> GrowTickEffect.CODEC)
        ;
        private final Supplier<MapCodec<IEffectParameters>> codecSupplier;

        EffectType(Supplier<MapCodec<IEffectParameters>> codecSupplier) {
            this.codecSupplier = codecSupplier;
        }

        public Supplier<MapCodec<IEffectParameters>> getCodecSupplier() {
            return codecSupplier;
        }
    }

    private static IEffect buildEffect(IEffectParameters params, Integer hotkey, String toggle) {
        return switch (params.getType()) {
            case MOBEFFECT -> getMobEffectEffect(params, hotkey, toggle);
            case POTIONRESISTANCE -> getPotionResistanceEffect(params, hotkey, toggle);
            case DAMAGEREDUCTION -> getDamageReductionEffect(params, hotkey, toggle);
            case FLIGHT -> new FlightEffect(hotkey, toggle);
            case WARP -> new WarpEffect(hotkey, toggle, WarpEffect.Params.cast(params).maxdist());
            case CURE -> new CureEffect(hotkey, toggle);
            case ATTRIBUTE -> getAttributeEffect(params, hotkey, toggle);
            case GROWTICK -> getGrowTickEffect(params, hotkey, toggle);
        };
    }

    @Nonnull
    private static GrowTickEffect getGrowTickEffect(IEffectParameters params, Integer hotkey, String toggle) {
        GrowTickEffect.Params p = GrowTickEffect.Params.cast(params);
        return new GrowTickEffect(hotkey, toggle, p.maxdist(), p.blocks());
    }

    @Nonnull
    private static AttributeModifierEffect getAttributeEffect(IEffectParameters params, Integer hotkey, String toggle) {
        AttributeModifierEffect.Params p = AttributeModifierEffect.Params.cast(params);
        String effName = p.effect();
        Holder<Attribute> attributeSupplier = switch (effName) {
            case "step_assist" -> Attributes.STEP_HEIGHT;
            case "swim_speed" -> NeoForgeMod.SWIM_SPEED;
            case "attack_range" -> Attributes.ENTITY_INTERACTION_RANGE;
            case "reach_distance" -> Attributes.BLOCK_INTERACTION_RANGE;
            case "max_health" -> Attributes.MAX_HEALTH;
            case "knockback_resistance" -> Attributes.KNOCKBACK_RESISTANCE;
            case "movement_speed" -> Attributes.MOVEMENT_SPEED;
            case "attack_speed" -> Attributes.ATTACK_SPEED;
            case "attack_damage" -> Attributes.ATTACK_DAMAGE;
            case "luck" -> Attributes.LUCK;
            default -> null;
        };
        if (attributeSupplier == null) {
            ResourceLocation attributeId;
            try {
                attributeId = ResourceLocation.parse(effName);
            } catch (ResourceLocationException e) {
                throw new RuntimeException("Bad attribute effectId '" + effName + "'!", e);
            }

            if (!BuiltInRegistries.ATTRIBUTE.containsKey(attributeId)) {
                throw new RuntimeException("Bad attribute effectId '" + effName + "'!");
            }
            Attribute attribute = BuiltInRegistries.ATTRIBUTE.get(attributeId);
            attributeSupplier = Holder.direct(attribute);
        }
        return new AttributeModifierEffect(ResourceLocation.fromNamespaceAndPath(FancyTrinkets.MODID, effName), hotkey, toggle, effName, attributeSupplier, p.operation(), p.amount());
    }

    @Nonnull
    private static MobEffectEffect getMobEffectEffect(IEffectParameters params, Integer hotkey, String toggle) {
        MobEffectEffect.Params p = MobEffectEffect.Params.cast(params);
        String effName = p.effect();
        Optional<Holder.Reference<MobEffect>> effect = BuiltInRegistries.MOB_EFFECT.getHolder(ResourceLocation.parse(effName));
        if (effect.isEmpty()) {
            throw new RuntimeException("Can't find effectId '" + effName + "'!");
        }
        return new MobEffectEffect(hotkey, toggle, effect.get(), p.strength() - 1);
    }

    @Nonnull
    private static PotionResistanceEffect getPotionResistanceEffect(IEffectParameters params, Integer hotkey, String toggle) {
        PotionResistanceEffect.Params p = PotionResistanceEffect.Params.cast(params);
        String effName = p.effect();
        Optional<Holder.Reference<MobEffect>> effect = BuiltInRegistries.MOB_EFFECT.getHolder(ResourceLocation.parse(effName));
        if (effect.isEmpty()) {
            throw new RuntimeException("Can't find effectId '" + effName + "'!");
        }
        return new PotionResistanceEffect(hotkey, toggle, effect.get());
    }

    @Nonnull
    private static DamageReductionEffect getDamageReductionEffect(IEffectParameters params, Integer hotkey, String toggle) {
        DamageReductionEffect.Params p = DamageReductionEffect.Params.cast(params);
        String dmgId = p.dmgId();
        float factor = p.factor();
        return new DamageReductionEffect(hotkey, toggle, dmgId, factor);
    }

}
