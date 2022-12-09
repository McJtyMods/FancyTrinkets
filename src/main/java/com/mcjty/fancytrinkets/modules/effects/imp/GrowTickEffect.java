package com.mcjty.fancytrinkets.modules.effects.imp;

import com.mcjty.fancytrinkets.datapack.EffectDescription;
import com.mcjty.fancytrinkets.datapack.IEffectParameters;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.Random;

public class GrowTickEffect extends EffectImp {

    private static final Random random = new Random();
    private final int maxdist;
    private final int blocks;

    public static record Params(int maxdist, int blocks) implements IEffectParameters {
        @Override
        public EffectDescription.EffectType getType() {
            return EffectDescription.EffectType.GROWTICK;
        }

        public static GrowTickEffect.Params cast(IEffectParameters params) {
            if (params instanceof GrowTickEffect.Params p) {
                return p;
            }
            throw new RuntimeException("Bad parameter type!");
        }
    }

    public static final Codec<IEffectParameters> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("maxdist").forGetter(l -> ((GrowTickEffect.Params)l).maxdist),
                    Codec.INT.fieldOf("blocks").forGetter(l -> ((GrowTickEffect.Params)l).blocks)
            ).apply(instance, GrowTickEffect.Params::new));

    public GrowTickEffect(Integer hotkey, String toggle, int maxdist, int blocks) {
        super(hotkey, toggle);
        this.maxdist = maxdist;
        this.blocks = blocks;
    }

    @Override
    public void tick(ItemStack stack, ServerPlayer player, String slotId) {
        executeIfEnabled(player, () -> {
            BlockPos blockPos = player.blockPosition();
            for (int i = 0 ; i < blocks ; i++) {
                int dx = random.nextInt(maxdist * 2) - maxdist;
                int dy = random.nextInt(3 * 2) - 3;
                int dz = random.nextInt(maxdist * 2) - maxdist;
                BlockPos dest = blockPos.offset(dx, dy, dz);
                player.level.getBlockState(dest).randomTick((ServerLevel) player.level, dest, random);
            }
        });
    }
}
