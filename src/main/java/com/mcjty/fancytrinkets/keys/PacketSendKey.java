package com.mcjty.fancytrinkets.keys;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.api.ITrinketItem;
import com.mcjty.fancytrinkets.setup.Registration;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public record PacketSendKey(Integer key) implements CustomPacketPayload {

    public static final Identifier ID = Identifier.fromNamespaceAndPath(FancyTrinkets.MODID, "send_key");
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

    public void handle(ServerPlayer player) {
        if (key < 1 || key > 8) return;
        eu.pb4.trinkets.api.TrinketsApi.getAttachment(player).forEach((slot, stack) -> {
            ITrinketItem trinket = Registration.TRINKET_ITEM.find(stack, null);
            if (trinket != null && slot.canApplyEffects(stack)) {
                trinket.forAllEffects(player.level(), stack, (effect, index) -> effect.onHotkey(stack, player, slot.getAsIdentifierPath() + "_" + index, key));
            }
        });
    }
}
