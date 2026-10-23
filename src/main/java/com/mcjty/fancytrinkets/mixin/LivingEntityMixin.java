package com.mcjty.fancytrinkets.mixin;
import com.mcjty.fancytrinkets.setup.Registration;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
@Mixin(LivingEntity.class)
abstract class LivingEntityMixin {
    @ModifyArg(method = "travelInWater", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;moveRelative(FLnet/minecraft/world/phys/Vec3;)V"), index = 0)
    private float fancytrinkets$swimSpeed(float speed) {
        return (Object) this instanceof Player player ? speed * (float) player.getAttributeValue(Registration.SWIM_SPEED) : speed;
    }
}
