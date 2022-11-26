package com.mcjty.fancytrinkets.modules.effects.imp;

import com.mcjty.fancytrinkets.datapack.EffectDescription;
import com.mcjty.fancytrinkets.datapack.IEffectParameters;
import com.mcjty.fancytrinkets.modules.effects.DefaultEffect;
import com.mcjty.fancytrinkets.playerdata.PlayerEffects;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class DamageReductionEffect extends DefaultEffect {

    private final String dmgId;
    private final float factor;

    public static record Params(String dmgId, float factor) implements IEffectParameters {
        @Override
        public EffectDescription.EffectType getType() {
            return EffectDescription.EffectType.DAMAGEREDUCTION;
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
                    Codec.STRING.fieldOf("damage").forGetter(l -> ((Params)l).dmgId),
                    Codec.FLOAT.fieldOf("factor").forGetter(l -> ((Params)l).factor)
            ).apply(instance, Params::new));

    public DamageReductionEffect(Integer hotkey, String toggle, String dmgId, float factor) {
        super(hotkey, toggle);
        this.dmgId = dmgId;
        this.factor = factor;
    }

    @Override
    public void tick(ItemStack stack, ServerPlayer player, String slotId) {
        executeIfEnabled(player, (playerEffects) -> {
            playerEffects.registerDamageReduction(dmgId, factor);
        });
    }

    @Override
    protected void turnOff(ServerPlayer player) {
        player.getCapability(PlayerEffects.PLAYER_EFFECTS).ifPresent(playerEffects -> {
            playerEffects.unregisterDamageReduction(dmgId);
        });
    }
}
