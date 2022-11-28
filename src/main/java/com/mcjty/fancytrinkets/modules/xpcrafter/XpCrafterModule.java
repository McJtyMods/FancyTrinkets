package com.mcjty.fancytrinkets.modules.xpcrafter;

import com.mcjty.fancytrinkets.modules.xpcrafter.recipe.XpRecipe;
import com.mcjty.fancytrinkets.modules.xpcrafter.recipe.XpRecipeSerializer;
import com.mcjty.fancytrinkets.modules.xpcrafter.recipe.XpRecipeType;
import com.mcjty.fancytrinkets.setup.Registration;
import mcjty.lib.modules.IModule;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.RegistryObject;

public class XpCrafterModule implements IModule {

    public static final RegistryObject<RecipeType<XpRecipe>> XP_RECIPE_TYPE = Registration.RECIPE_TYPES.register("xprecipe", XpRecipeType::new);
    public static final RegistryObject<RecipeSerializer<XpRecipe>> XP_RECIPE_SERIALIZER = Registration.RECIPE_SERIALIZERS.register("xprecipe", XpRecipeSerializer::new);

    public XpCrafterModule() {
    }

    @Override
    public void init(FMLCommonSetupEvent event) {
    }

    @Override
    public void initClient(FMLClientSetupEvent event) {
    }

    @Override
    public void initConfig() {
    }
}
