package com.mcjty.fancytrinkets.modules.xpcrafter;

import com.mcjty.fancytrinkets.modules.xpcrafter.blocks.ExperienceCrafterBE;
import com.mcjty.fancytrinkets.modules.xpcrafter.blocks.ExperienceCrafterBlock;
import com.mcjty.fancytrinkets.modules.xpcrafter.client.GuiExperienceCrafter;
import com.mcjty.fancytrinkets.modules.xpcrafter.data.ExperienceCrafterData;
import com.mcjty.fancytrinkets.modules.xpcrafter.recipe.XpRecipe;
import com.mcjty.fancytrinkets.modules.xpcrafter.recipe.XpRecipeSerializer;
import com.mcjty.fancytrinkets.modules.xpcrafter.recipe.XpRecipeType;
import mcjty.lib.blocks.BaseBlock;
import mcjty.lib.blocks.RBlock;
import mcjty.lib.container.GenericContainer;
import mcjty.lib.datagen.DataGen;
import mcjty.lib.datagen.Dob;
import mcjty.lib.modules.IModule;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Supplier;

import static com.mcjty.fancytrinkets.setup.Registration.*;
import static mcjty.lib.datagen.DataGen.has;

public class XpCrafterModule implements IModule {

    public static final Supplier<RecipeType<XpRecipe>> XP_RECIPE_TYPE = RECIPE_TYPES.register("xprecipe", XpRecipeType::new);
    public static final Supplier<RecipeSerializer<XpRecipe>> XP_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("xprecipe", XpRecipeSerializer::new);

    public static final RBlock<BaseBlock, BlockItem, ExperienceCrafterBE> EXPERIENCE_CRAFTER = RBLOCKS.registerBlock("experience_crafter",
            ExperienceCrafterBE.class,
            ExperienceCrafterBlock::new,
            block -> new BlockItem(block.get(), createStandardProperties()),
            ExperienceCrafterBE::new
    );
    public static final Supplier<MenuType<GenericContainer>> CONTAINER_EXPERIENCE_CRAFTER = CONTAINERS.register("experience_crafter", GenericContainer::createContainerType);

    public static final Supplier<AttachmentType<ExperienceCrafterData>> EXPERIENCE_CRAFTER_DATA = ATTACHMENT_TYPES.register(
            "experience_crafter_data", () -> AttachmentType.builder(() -> ExperienceCrafterData.DEFAULT)
                    .serialize(ExperienceCrafterData.CODEC)
                    .build());
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ExperienceCrafterData>> ITEM_EXPERIENCE_CRAFTER_DATA = COMPONENTS.registerComponentType(
            "experience_crafter_data",
            builder -> builder
                    .persistent(ExperienceCrafterData.CODEC)
                    .networkSynchronized(ExperienceCrafterData.STREAM_CODEC));

    public XpCrafterModule(IEventBus bus) {
        bus.addListener(this::registerMenuScreens);
    }

    @Override
    public void init(FMLCommonSetupEvent event) {
    }

    @Override
    public void initClient(FMLClientSetupEvent event) {
    }

    public void registerMenuScreens(RegisterMenuScreensEvent event) {
        GuiExperienceCrafter.register(event);
    }

    @Override
    public void initConfig(IEventBus bus) {
    }

    @Override
    public void initDatagen(DataGen dataGen, HolderLookup.Provider lookupProvider) {
        dataGen.add(
                Dob.blockBuilder(EXPERIENCE_CRAFTER)
                        .name("Experience Crafter")
                        .keyedMessage("header", "Craft trinkets using experience. With low experience you'll potentially get low quality trinkets")
                        .ironPickaxeTags()
                        .parentedItem("block/experience_crafter")
                        .standardLoot(ITEM_EXPERIENCE_CRAFTER_DATA.get())
                        .shaped(builder -> builder
                                .define('C', Items.CRAFTING_TABLE)
                                .define('g', Items.GLOWSTONE_DUST)
                                .unlockedBy("redstone", has(Items.REDSTONE)),
                                "iii", "gCg", "iii"
                        )
                        .blockState(p -> {
                            p.simpleBlock(XpCrafterModule.EXPERIENCE_CRAFTER.block().get(), p.topBasedModel("experience_crafter",
                                    p.modLoc("block/experience_crafter_top"),
                                    p.modLoc("block/experience_crafter_side"),
                                    p.modLoc("block/experience_crafter_bottom")));
                        })
        );
    }
}
