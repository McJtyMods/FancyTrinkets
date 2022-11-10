package com.mcjty.fancytrinkets.curios;

import net.minecraftforge.fml.InterModComms;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

public class CuriosSetup {

    public static final int SLOT_RING = 1;
    public static final int SLOT_BELT = 2;
    public static final int SLOT_BRACELET = 4;
    public static final int SLOT_CHARM = 8;
    public static final int SLOT_NECKLACE = 16;
    public static final int SLOT_HEAD = 32;
    public static final int SLOT_BODY = 64;
    public static final int SLOT_ANY = SLOT_RING + SLOT_BELT + SLOT_BRACELET + SLOT_CHARM + SLOT_NECKLACE + SLOT_HEAD + SLOT_BODY;

    public static void setupCurios() {
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.BELT.getMessageBuilder()
                        .build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.BRACELET.getMessageBuilder()
                        .build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.CHARM.getMessageBuilder()
                        .build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.NECKLACE.getMessageBuilder()
                        .build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.HEAD.getMessageBuilder()
                        .build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.BODY.getMessageBuilder()
                        .build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.RING.getMessageBuilder()
                        .size(2)
                        .build());
    }
}
