package com.mcjty.fancytrinkets.keys;

import com.mcjty.fancytrinkets.modules.trinkets.ITrinketItem;
import com.mcjty.fancytrinkets.modules.trinkets.items.TrinketItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import top.theillusivec4.curios.api.CuriosApi;

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
            CuriosApi.getCuriosHelper().getEquippedCurios(ctx.getSender()).ifPresent(handler -> {
                for (int i = 0 ; i < handler.getSlots() ; i++) {
                    ItemStack stack = handler.getStackInSlot(i);
                    if (stack.getItem() instanceof TrinketItem trinketItem) {
                        trinketItem.forAllEffects(stack, iEffect -> {
                            iEffect.onHotkey(stack, ctx.getSender(), );
                        });
                    }
                }
            });
        });
        ctx.setPacketHandled(true);
    }
}
