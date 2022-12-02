package com.mcjty.fancytrinkets.modules.effects.imp;

import com.mcjty.fancytrinkets.datapack.EffectDescription;
import com.mcjty.fancytrinkets.datapack.IEffectParameters;
import com.mojang.serialization.Codec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class WarpEffect extends EffectImp {

    public static record Params() implements IEffectParameters {
        public static final Params EMPTY = new Params();

        @Override
        public EffectDescription.EffectType getType() {
            return EffectDescription.EffectType.WARP;
        }
    }

    public static final Codec<IEffectParameters> CODEC = Codec.unit(Params.EMPTY);

    public WarpEffect(Integer hotkey, String toggle) {
        super(hotkey, toggle);
    }


    @Override
    public void onHotkey(ItemStack stack, ServerPlayer player, String slotId, int key) {
        executeIfEnabled(player, () -> {
            // @todo
        });
    }
}
