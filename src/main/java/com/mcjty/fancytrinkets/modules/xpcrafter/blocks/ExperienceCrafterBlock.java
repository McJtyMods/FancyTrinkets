package com.mcjty.fancytrinkets.modules.xpcrafter.blocks;

import com.mcjty.fancytrinkets.modules.trinkets.items.TrinketItem;
import mcjty.lib.blocks.BaseBlock;
import mcjty.lib.builder.BlockBuilder;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import static mcjty.lib.builder.TooltipBuilder.header;
import static mcjty.lib.builder.TooltipBuilder.key;

public class ExperienceCrafterBlock extends BaseBlock {

    public ExperienceCrafterBlock(BlockBehaviour.Properties properties) {
        super(new BlockBuilder().properties(properties.mapColor(MapColor.METAL).strength(2.0f)
                .requiresCorrectToolForDrops().sound(SoundType.METAL))
//                .topDriver(RFToolsUtilityTOPDriver.DRIVER)
                .info(key(TrinketItem.MESSAGE_FANCYTRINKETS_SHIFTMESSAGE))
                .infoShift(header())
                .tileEntitySupplier(ExperienceCrafterBE::new));
    }
}
