package com.mcjty.fancytrinkets.keys;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.setup.Registration;
import mcjty.lib.network.CustomPacketPayload;
import mcjty.lib.network.PlayPayloadContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;

public record PacketSendKey(Integer key) implements CustomPacketPayload {

    public static final ResourceLocation ID = new ResourceLocation(FancyTrinkets.MODID, "send_key");

    public static PacketSendKey create(int key) {
        return new PacketSendKey(key);
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeInt(key);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    public static PacketSendKey create(FriendlyByteBuf buf) {
        return new PacketSendKey(buf.readInt());
    }

    public void handle(PlayPayloadContext ctx) {
        ctx.workHandler().submitAsync(() -> {
            ctx.player().ifPresent(player -> {
                for (SlotResult slot : CuriosApi.getCuriosHelper().findCurios(player, stack -> stack.getCapability(Registration.TRINKET_ITEM_CAPABILITY).isPresent())) {
                    ItemStack stack = slot.stack();
                    stack.getCapability(Registration.TRINKET_ITEM_CAPABILITY).ifPresent(trinket -> {
                        SlotContext context = slot.slotContext();
                        String slotId = context.identifier() + context.index() + "_";
                        trinket.forAllEffects(player.level(), stack, (effect, idx) -> {
                            effect.onHotkey(stack, (ServerPlayer) player, slotId + idx, key);
                        });
                    });
                }
            });
        });
    }
}
