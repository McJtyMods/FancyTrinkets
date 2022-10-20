package com.mcjty.fancytrinkets.modules.signs.network;

import com.mcjty.fancytrinkets.modules.signs.TextureType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class PacketUpdateSignData {

    private final BlockPos pos;
    private final Integer backColor;
    private final int textColor;
    private final boolean bright;
    private final boolean transparent;
    private final boolean large;
    private final int imageIndex;
    private final List<String> lines;
    private final TextureType textureType;

    public PacketUpdateSignData(BlockPos pos, List<String> lines, Integer backColor, int textColor,
                                boolean bright, boolean transparent, boolean large, TextureType textureType,
                                int imageIndex) {
        this.pos = pos;
        this.lines = lines;
        this.backColor = backColor;
        this.textColor = textColor;
        this.bright = bright;
        this.large = large;
        this.transparent = transparent;
        this.textureType = textureType;
        this.imageIndex = imageIndex;
    }

    public PacketUpdateSignData(FriendlyByteBuf buf) {
        pos = buf.readBlockPos();
        if (buf.readBoolean()) {
            backColor = buf.readInt();
        } else {
            backColor = null;
        }
        textColor = buf.readInt();
        bright = buf.readBoolean();
        transparent = buf.readBoolean();
        large = buf.readBoolean();
        textureType = TextureType.values()[buf.readInt()];
        imageIndex = buf.readInt();
        int s = buf.readInt();
        lines = new ArrayList<>();
        for (int i = 0; i < s; i++) {
            lines.add(buf.readUtf(32767));
        }
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        if (backColor != null) {
            buf.writeBoolean(true);
            buf.writeInt(backColor);
        } else {
            buf.writeBoolean(false);
        }
        buf.writeInt(textColor);
        buf.writeBoolean(bright);
        buf.writeBoolean(transparent);
        buf.writeBoolean(large);
        buf.writeInt(textureType.ordinal());
        buf.writeInt(imageIndex);
        buf.writeInt(lines.size());
        lines.forEach(buf::writeUtf);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
        });
        return true;
    }
}
