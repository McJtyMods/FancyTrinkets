package com.mcjty.fancytrinkets.keys;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.api.ITrinketItem;
import com.mcjty.fancytrinkets.setup.Registration;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;

public record PacketSendKey(Integer key) implements CustomPacketPayload {

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(FancyTrinkets.MODID, "send_key");
    public static final CustomPacketPayload.Type<PacketSendKey> TYPE = new Type<>(ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, PacketSendKey> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, PacketSendKey::key,
            PacketSendKey::create);
    
    public static PacketSendKey create(int key) {
        return new PacketSendKey(key);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static PacketSendKey create(FriendlyByteBuf buf) {
        return new PacketSendKey(buf.readInt());
    }

    public void handle(IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            Player player = ctx.player();
            for (SlotResult slot : CuriosApi.getCuriosHelper().findCurios(player, stack -> stack.getCapability(Registration.TRINKET_ITEM_CAPABILITY) != null)) {
                ItemStack stack = slot.stack();
                ITrinketItem trinket = stack.getCapability(Registration.TRINKET_ITEM_CAPABILITY);
                if (trinket != null) {
                    SlotContext context = slot.slotContext();
                    String slotId = context.identifier() + context.index() + "_";
                    trinket.forAllEffects(player.level(), stack, (effect, idx) -> {
                        effect.onHotkey(stack, (ServerPlayer) player, slotId + idx, key);
                    });
                }
            }
        });
    }
}
