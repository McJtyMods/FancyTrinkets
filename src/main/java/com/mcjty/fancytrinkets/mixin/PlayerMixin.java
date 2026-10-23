package com.mcjty.fancytrinkets.mixin;
import com.mcjty.fancytrinkets.playerdata.PlayerEffects;
import com.mcjty.fancytrinkets.setup.Registration;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Mixin(Player.class)
abstract class PlayerMixin {
    @Inject(method = "createAttributes", at = @At("RETURN"))
    private static void fancytrinkets$attributes(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
        cir.getReturnValue().add(Registration.SWIM_SPEED);
    }
    @ModifyExpressionValue(method = "actuallyHurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getDamageAfterMagicAbsorb(Lnet/minecraft/world/damagesource/DamageSource;F)F"))
    private float fancytrinkets$damage(float amount, ServerLevel level, DamageSource source, float original) {
        if ((Object) this instanceof ServerPlayer player) {
            return Math.max(0, amount * PlayerEffects.getPlayerEffects(player).getDamageReduction(player, source.getMsgId()));
        }
        return amount;
    }
}
