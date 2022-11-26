package com.mcjty.fancytrinkets.keys;

import com.mcjty.fancytrinkets.modules.trinkets.ITrinketItem;
import com.mcjty.fancytrinkets.modules.trinkets.items.TrinketItem;
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
        ctx.enqueueWork(() -> {
            for (SlotResult slot : CuriosApi.getCuriosHelper().findCurios(ctx.getSender(), stack -> stack.getItem() instanceof TrinketItem)) {
                ItemStack stack = slot.stack();
                if (stack.getItem() instanceof ITrinketItem trinketItem) {
                    trinketItem.forAllEffects(stack, iEffect -> {
                        SlotContext context = slot.slotContext();
                        iEffect.onHotkey(stack, ctx.getSender(), context.identifier() + context.index(), key);
                    });
                }
            }
        });
        ctx.setPacketHandled(true);
    }
}
