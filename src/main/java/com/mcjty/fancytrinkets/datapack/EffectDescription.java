package com.mcjty.fancytrinkets.datapack;

import com.mcjty.fancytrinkets.modules.effects.IEffect;
import com.mcjty.fancytrinkets.modules.effects.imp.*;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Supplier;

public record EffectDescription(Integer hotkey, String toggle, IEffectParameters params) {

    public static final Codec<IEffectParameters> PARAMS_CODEC = ExtraCodecs.lazyInitializedCodec(() -> Codec.STRING.dispatch("type",
            s -> s.getType().name().toLowerCase(),
            EffectDescription::getParameterCodec));

    public static final Codec<EffectDescription> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.optionalFieldOf("hotkey").forGetter(l -> Optional.ofNullable(l.hotkey)),
                    Codec.STRING.optionalFieldOf("toggle").forGetter(l -> Optional.ofNullable(l.toggle)),
                    PARAMS_CODEC.fieldOf("params").forGetter(l -> l.params)
            ).apply(instance, (hotkey, toggle, params) -> new EffectDescription(
                    hotkey.orElse(null),
                    toggle.orElse(null),
                    params)));

    private static Codec<IEffectParameters> getParameterCodec(String stype) {
        EffectType type = EffectType.valueOf(stype.toUpperCase());
        return type.getCodecSupplier().get();
    }

    public enum EffectType {
        MOBEFFECT(() -> MobEffectEffect.CODEC),
        POTIONRESISTANCE(() -> PotionResistanceEffect.CODEC),
        DAMAGEREDUCTION(() -> DamageReductionEffect.CODEC),
        FLIGHT(() -> FlightEffect.CODEC),
        CURE(() -> CureEffect.CODEC),
        ATTRIBUTE(() -> AttributeModifierEffect.CODEC)
        ;
        private final Supplier<Codec<IEffectParameters>> codecSupplier;

        EffectType(Supplier<Codec<IEffectParameters>> codecSupplier) {
            this.codecSupplier = codecSupplier;
        }

        public Supplier<Codec<IEffectParameters>> getCodecSupplier() {
            return codecSupplier;
        }
    }

    public IEffect build() {
        return switch (params.getType()) {
            case MOBEFFECT -> getMobEffectEffect();
            case POTIONRESISTANCE -> getPotionResistanceEffect();
            case DAMAGEREDUCTION -> getDamageReductionEffect();
            case FLIGHT -> new FlightEffect(hotkey, toggle);
            case CURE -> new CureEffect(hotkey, toggle);
            case ATTRIBUTE -> getAttributeEffect();
        };
    }

    @NotNull
    private AttributeModifierEffect getAttributeEffect() {
        AttributeModifierEffect.Params p = AttributeModifierEffect.Params.cast(params);
        String effName = p.effect();
        Supplier<Attribute> attributeSupplier = switch (effName) {
            case "step_assist" -> ForgeMod.STEP_HEIGHT_ADDITION;
            case "swim_speed" -> ForgeMod.SWIM_SPEED;
            case "attack_range" -> ForgeMod.ATTACK_RANGE;
            case "reach_distance" -> ForgeMod.REACH_DISTANCE;
            case "max_health" -> () -> Attributes.MAX_HEALTH;
            case "knockback_resistance" -> () -> Attributes.KNOCKBACK_RESISTANCE;
            case "movement_speed" -> () -> Attributes.MOVEMENT_SPEED;
            case "attack_speed" -> () -> Attributes.ATTACK_SPEED;
            case "attack_damage" -> () -> Attributes.ATTACK_DAMAGE;
            case "luck" -> () -> Attributes.LUCK;
            default -> throw new RuntimeException("Bad attribute effect '" + effName + "'!");
        };
        return new AttributeModifierEffect(hotkey, toggle, effName, attributeSupplier, p.operation(), p.amount());
    }

    @NotNull
    private MobEffectEffect getMobEffectEffect() {
        MobEffectEffect.Params p = MobEffectEffect.Params.cast(params);
        String effName = p.effect();
        MobEffect effect = ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation(effName));
        if (effect == null) {
            throw new RuntimeException("Can't find effect '" + effName + "'!");
        }
        return new MobEffectEffect(hotkey, toggle, effect, p.strength() - 1);
    }

    @NotNull
    private PotionResistanceEffect getPotionResistanceEffect() {
        PotionResistanceEffect.Params p = PotionResistanceEffect.Params.cast(params);
        String effName = p.effect();
        MobEffect effect = ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation(effName));
        if (effect == null) {
            throw new RuntimeException("Can't find effect '" + effName + "'!");
        }
        return new PotionResistanceEffect(hotkey, toggle, effect);
    }

    @NotNull
    private DamageReductionEffect getDamageReductionEffect() {
        DamageReductionEffect.Params p = DamageReductionEffect.Params.cast(params);
        String dmgId = p.dmgId();
        float factor = p.factor();
        return new DamageReductionEffect(hotkey, toggle, dmgId, factor);
    }

}
