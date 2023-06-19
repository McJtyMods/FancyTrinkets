package com.mcjty.fancytrinkets.modules.effects.imp;

import com.mcjty.fancytrinkets.datapack.EffectDescription;
import com.mcjty.fancytrinkets.datapack.IEffectParameters;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import mcjty.lib.varia.SoundTools;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;

public class WarpEffect extends EffectImp {

    private final int maxdist;

    public static record Params(int maxdist) implements IEffectParameters {
        @Override
        public EffectDescription.EffectType getType() {
            return EffectDescription.EffectType.WARP;
        }

        public static WarpEffect.Params cast(IEffectParameters params) {
            if (params instanceof WarpEffect.Params p) {
                return p;
            }
            throw new RuntimeException("Bad parameter type!");
        }
    }

    public static final Codec<IEffectParameters> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("maxdist").forGetter(l -> ((WarpEffect.Params)l).maxdist)
            ).apply(instance, WarpEffect.Params::new));


    public WarpEffect(Integer hotkey, String toggle, int maxdist) {
        super(hotkey, toggle);
        this.maxdist = maxdist;
    }


    @Override
    public void onHotkey(ItemStack stack, ServerPlayer player, String slotId, int key) {
        if (Objects.equals(key, hotkey)) {
            executeIfEnabled(player, () -> {
                Vec3 lookVec = player.getLookAngle();
                Vec3 start = new Vec3(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
                int distance = maxdist;
                boolean gothrough = false;
                if (player.isShiftKeyDown()) {
                    distance /= 2;
                }

                Vec3 end = start.add(lookVec.x * distance, lookVec.y * distance, lookVec.z * distance);
                ClipContext context = new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player);
                HitResult position = player.level().clip(context);
                safeTeleport(player, (BlockHitResult) position);
            });
        }
    }

    private void safeTeleport(ServerPlayer player, BlockHitResult position) {
        Level level = player.level();
        BlockPos blockPos = position.getBlockPos();
        int x = blockPos.getX();
        int y = blockPos.getY();
        int z = blockPos.getZ();
        if (level.isEmptyBlock(blockPos.above()) && level.isEmptyBlock(blockPos.above(2))) {
            player.teleportTo(x+.5, y + 1, z+.5);
        } else {
            switch (position.getDirection()) {
                case DOWN -> player.teleportTo(x + .5, y - 2, z + .5);
                case UP -> {
                    return;
                }
                case NORTH -> player.teleportTo(x + .5, y, z - 1 + .5);
                case SOUTH -> player.teleportTo(x + .5, y, z + 1 + .5);
                case WEST -> player.teleportTo(x - 1 + .5, y, z + .5);
                case EAST -> player.teleportTo(x + 1 + .5, y, z + .5);
            }
        }
        SoundTools.playSound(player.getCommandSenderWorld(), SoundEvents.ENDER_PEARL_THROW, player.getX(), player.getY(), player.getZ(), 1.0f, 1.0f);
    }
}
