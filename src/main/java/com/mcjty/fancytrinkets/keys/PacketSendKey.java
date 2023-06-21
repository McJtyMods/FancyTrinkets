package com.mcjty.fancytrinkets.keys;

import com.mcjty.fancytrinkets.api.ITrinketItem;
import com.mcjty.fancytrinkets.modules.trinkets.items.TrinketItem;
import com.mcjty.fancytrinkets.setup.Registration;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;

import java.util.function.Supplier;

public class PacketSendKey {

    private final int key;

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(key);
    }

    public PacketSendKey(FriendlyByteBuf buf) {
        key = buf.readInt();
    }

    public PacketSendKey(int key) {
        this.key = key;
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        for (SlotResult slot : CuriosApi.getCuriosHelper().findCurios(ctx.getSender(), stack -> stack.getCapability(Registration.TRINKET_ITEM_CAPABILITY).isPresent())) {
            ItemStack stack = slot.stack();
            stack.getCapability(Registration.TRINKET_ITEM_CAPABILITY).ifPresent(trinket -> {
                SlotContext context = slot.slotContext();
                String slotId = context.identifier() + context.index() + "_";
                trinket.forAllEffects(ctx.getSender().level(), stack, (effect, idx) -> {
                    effect.onHotkey(stack, ctx.getSender(), slotId + idx, key);
                });
            });
        }
    }
}
