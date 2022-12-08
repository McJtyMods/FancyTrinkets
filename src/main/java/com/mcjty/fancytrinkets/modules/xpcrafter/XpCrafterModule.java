package com.mcjty.fancytrinkets.modules.xpcrafter;

import com.mcjty.fancytrinkets.modules.xpcrafter.blocks.ExperienceCrafterBE;
import com.mcjty.fancytrinkets.modules.xpcrafter.blocks.ExperienceCrafterBlock;
import com.mcjty.fancytrinkets.modules.xpcrafter.client.GuiExperienceCrafter;
import com.mcjty.fancytrinkets.modules.xpcrafter.recipe.XpRecipe;
import com.mcjty.fancytrinkets.modules.xpcrafter.recipe.XpRecipeSerializer;
import com.mcjty.fancytrinkets.modules.xpcrafter.recipe.XpRecipeType;
import mcjty.lib.container.GenericContainer;
import mcjty.lib.modules.IModule;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.RegistryObject;

import static com.mcjty.fancytrinkets.setup.Registration.*;

public class XpCrafterModule implements IModule {

//    public static final RegistryObject<RecipeType<XpRecipe>> XP_RECIPE_TYPE = RECIPE_TYPES.register("xprecipe", XpRecipeType::new);
    public static final RegistryObject<RecipeSerializer<XpRecipe>> XP_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("xprecipe", XpRecipeSerializer::new);

    public static final RegistryObject<ExperienceCrafterBlock> EXPERIENCE_CRAFTER = BLOCKS.register("experience_crafter", ExperienceCrafterBlock::new);
    public static final RegistryObject<BlockItem> EXPERIENCE_CRAFTER_ITEM = ITEMS.register("experience_crafter", () -> new BlockItem(EXPERIENCE_CRAFTER.get(), createStandardProperties()));
    public static final RegistryObject<BlockEntityType<?>> TYPE_EXPERIENCE_CRAFTER = TILES.register("experience_crafter", () -> BlockEntityType.Builder.of(ExperienceCrafterBE::new, EXPERIENCE_CRAFTER.get()).build(null));
    public static final RegistryObject<MenuType<GenericContainer>> CONTAINER_EXPERIENCE_CRAFTER = CONTAINERS.register("experience_crafter", GenericContainer::createContainerType);

    public static final RecipeType<XpRecipe> XP_RECIPE_TYPE = new XpRecipeType();

    public XpCrafterModule() {
    }

    @Override
    public void init(FMLCommonSetupEvent event) {
    }

    @Override
    public void initClient(FMLClientSetupEvent event) {
        GuiExperienceCrafter.register();
    }

    @Override
    public void initConfig() {
    }
}
