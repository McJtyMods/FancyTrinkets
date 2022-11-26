package com.mcjty.fancytrinkets.modules.effects.imp;

import com.mcjty.fancytrinkets.datapack.EffectDescription;
import com.mcjty.fancytrinkets.datapack.IEffectParameters;
import com.mcjty.fancytrinkets.modules.effects.DefaultEffect;
import com.mcjty.fancytrinkets.playerdata.PlayerEffects;
import com.mojang.serialization.Codec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public class FlightEffect extends DefaultEffect {

    public static record Params() implements IEffectParameters {
        public static final Params EMPTY = new Params();

        @Override
        public EffectDescription.EffectType getType() {
            return EffectDescription.EffectType.FLIGHT;
        }
    }

    public static final Codec<IEffectParameters> CODEC = Codec.unit(Params.EMPTY);

    public FlightEffect(Integer hotkey, String toggle) {
        super(hotkey, toggle);
    }

    @Override
    public void tick(ItemStack stack, ServerPlayer player, String slotId) {
        if (!player.isCreative()) {
            executeIfEnabled(player, () -> {
                if (!player.getAbilities().mayfly) {
                    player.getAbilities().mayfly = true;
                    player.onUpdateAbilities();
                }
            });
        }
    }

    @Override
    public void onHotkey(ItemStack stack, ServerPlayer player, String slotId, int key) {
        if (toggle != null && Objects.equals(key, hotkey)) {
            player.getCapability(PlayerEffects.PLAYER_EFFECTS).ifPresent(playerEffects -> {
                if (!playerEffects.toggle(player, toggle)) {
                    turnOff(player);
                }
            });
        }
    }

    @Override
    protected void turnOff(ServerPlayer player) {
        if (!player.isCreative()) {
            player.getAbilities().flying = false;
            player.getAbilities().mayfly = false;
            player.onUpdateAbilities();
        }
    }
}
