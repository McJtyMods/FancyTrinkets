package com.mcjty.fancytrinkets.modules.xpcrafter.blocks;

import com.mcjty.fancytrinkets.modules.trinkets.items.TrinketItem;
import mcjty.lib.blocks.BaseBlock;
import mcjty.lib.builder.BlockBuilder;

import static mcjty.lib.builder.TooltipBuilder.header;
import static mcjty.lib.builder.TooltipBuilder.key;

public class ExperienceCrafterBlock extends BaseBlock {

    public ExperienceCrafterBlock() {
        super(new BlockBuilder()
//                .topDriver(RFToolsUtilityTOPDriver.DRIVER)
                .info(key(TrinketItem.MESSAGE_FANCYTRINKETS_SHIFTMESSAGE))
                .infoShift(header())
                .tileEntitySupplier(ExperienceCrafterBE::new));
    }
}
