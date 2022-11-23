package com.mcjty.fancytrinkets.modules.effects.imp;

import com.mcjty.fancytrinkets.modules.effects.IEffect;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;
import java.util.function.Supplier;

public class AttributeModifierEffect implements IEffect {

    private final ResourceLocation id;
    private final UUID uuid;
    private final AttributeModifier modifier;
    private final Supplier<Attribute> attribute;

    public AttributeModifierEffect(ResourceLocation id, String name, Supplier<Attribute> attibute, AttributeModifier.Operation operation, double amount) {
        this.id = id;
        this.attribute = attibute;
        this.uuid = UUID.randomUUID();
        modifier = new AttributeModifier(uuid, name, amount, operation);
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public void tick(ItemStack stack, Entity entity, String slotId) {
        if (entity instanceof ServerPlayer serverPlayer) {
            if (!serverPlayer.isCreative()) {
                AttributeInstance instance = serverPlayer.getAttribute(attribute.get());
                if (instance != null && instance.getModifier(uuid) == null) {
                    instance.addPermanentModifier(modifier);
                }
            }
        }
    }

    @Override
    public void onUnequip(ItemStack stack, Entity entity, String slotId) {
        if (entity instanceof ServerPlayer serverPlayer) {
            if (!serverPlayer.isCreative()) {
                AttributeInstance instance = serverPlayer.getAttribute(attribute.get());
                if (instance != null) {
                    instance.removePermanentModifier(uuid);
                }
            }
        }
    }
}
