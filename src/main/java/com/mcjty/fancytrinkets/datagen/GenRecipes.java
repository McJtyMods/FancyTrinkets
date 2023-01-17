package com.mcjty.fancytrinkets.datagen;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.modules.loot.LootModule;
import com.mcjty.fancytrinkets.modules.trinkets.DefaultTrinkets;
import com.mcjty.fancytrinkets.modules.trinkets.items.TrinketItem;
import com.mcjty.fancytrinkets.modules.xpcrafter.recipe.XpRecipeBuilder;
import mcjty.lib.datagen.BaseRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.PartialNBTIngredient;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class GenRecipes extends BaseRecipeProvider {

    public GenRecipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {


        build(consumer, trinket("base_star"), XpRecipeBuilder.shapedRecipe(createTrinketStack("base_star"))
                        .define('g', Tags.Items.DUSTS_PRISMARINE),
                "g g g", "  g  ", "ggggg", "  g  ", "g g g");

        build(consumer, trinket("flight_star"), XpRecipeBuilder.shapedRecipe(createTrinketStack("flight_star"))
                        .define('f', Tags.Items.FEATHERS)
                        .define('t', Items.GHAST_TEAR)
                        .define('g', LootModule.GHAST_ESSENCE.get())
                        .define('S', createTrinketIngredient("base_star")),
                "gfgfg", "fgtgf", "gtStg", "fgtgf", "gfgfg");
        build(consumer, trinket("flight_star_reforge"), XpRecipeBuilder.shapedRecipe(createTrinketStack("flight_star"))
                        .define('g', LootModule.GHAST_ESSENCE.get())
                        .define('S', createTrinketIngredient("flight_star")),
                "     ", " ggg ", " gSg ", " ggg ", "     ");

        build(consumer, trinket("power_star"), XpRecipeBuilder.shapedRecipe(createTrinketStack("power_star"))
                        .define('t', Items.DRAGON_HEAD)
                        .define('g', LootModule.DRAGON_ESSENCE.get())
                        .define('w', LootModule.WITHER_ESSENCE.get())
                        .define('S', createTrinketIngredient("base_star")),
                " iii ", "iwtwi", "igSgi", "iwgwi", " iii ");
        build(consumer, trinket("power_star_reforge"), XpRecipeBuilder.shapedRecipe(createTrinketStack("power_star"))
                        .define('g', LootModule.WITHER_ESSENCE.get())
                        .define('S', createTrinketIngredient("power_star")),
                "     ", " ggg ", " gSg ", " ggg ", "     ");

        build(consumer, trinket("swift_star"), XpRecipeBuilder.shapedRecipe(createTrinketStack("swift_star"))
                        .define('t', Tags.Items.GEMS_QUARTZ)
                        .define('g', LootModule.SPIDER_ESSENCE.get())
                        .define('S', createTrinketIngredient("base_star")),
                "ttgtt", "tgggt", "ggSgg", "tgggt", "ttgtt");
        build(consumer, trinket("swift_star_reforge"), XpRecipeBuilder.shapedRecipe(createTrinketStack("swift_star"))
                        .define('g', LootModule.SPIDER_ESSENCE.get())
                        .define('S', createTrinketIngredient("swift_star")),
                "     ", " ggg ", " gSg ", " ggg ", "     ");

        build(consumer, trinket("grow_charm"), XpRecipeBuilder.shapedRecipe(createTrinketStack("grow_charm"))
                        .define('f', Tags.Items.BONES)
                        .define('t', Items.BONE_MEAL)
                        .define('w', Items.DIAMOND_BLOCK)
                        .define('g', LootModule.SKELETON_ESSENCE.get()),
                " fwf ", "fgggf", "tgggt", "fgggf", " fwf ");
        build(consumer, trinket("grow_charm_reforge"), XpRecipeBuilder.shapedRecipe(createTrinketStack("grow_charm"))
                        .define('g', LootModule.SKELETON_ESSENCE.get())
                        .define('S', createTrinketIngredient("grow_charm")),
                "     ", " ggg ", " gSg ", " ggg ", "     ");

        build(consumer, trinket("slowfalling_feather"), XpRecipeBuilder.shapedRecipe(createTrinketStack("slowfalling_feather"))
                        .define('f', Tags.Items.FEATHERS)
                        .define('g', LootModule.CHICKEN_ESSENCE.get()),
                "ooofg", "oofgg", "ogggo", "fggoo", "gfooo");
        build(consumer, trinket("slowfalling_feather_reforge"), XpRecipeBuilder.shapedRecipe(createTrinketStack("slowfalling_feather"))
                        .define('g', LootModule.CHICKEN_ESSENCE.get())
                        .define('S', createTrinketIngredient("slowfalling_feather")),
                "     ", " ggg ", " gSg ", " ggg ", "     ");

        build(consumer, trinket("base_gold_ring"), XpRecipeBuilder.shapedRecipe(createTrinketStack("base_gold_ring"))
                        .define('g', Tags.Items.INGOTS_GOLD),
                " ggg ", "g   g", "g   g", "g   g", " ggg ");

        build(consumer, trinket("lightness_ring"), XpRecipeBuilder.shapedRecipe(createTrinketStack("lightness_ring"))
                        .define('t', Items.DIAMOND_BLOCK)
                        .define('w', Items.AMETHYST_BLOCK)
                        .define('f', LootModule.IRON_GOLEM_ESSENCE.get())
                        .define('g', LootModule.CHICKEN_ESSENCE.get())
                        .define('S', createTrinketIngredient("base_gold_ring")),
                " wtw ", "wfgfw", "tgSgt", "wfgfw", " wtw ");
        build(consumer, trinket("lightness_ring_reforge"), XpRecipeBuilder.shapedRecipe(createTrinketStack("lightness_ring"))
                        .define('g', LootModule.CHICKEN_ESSENCE.get())
                        .define('S', createTrinketIngredient("lightness_ring")),
                "     ", " ggg ", " gSg ", " ggg ", "     ");

        build(consumer, trinket("fireresist_ring"), XpRecipeBuilder.shapedRecipe(createTrinketStack("fireresist_ring"))
                        .define('t', Items.GHAST_TEAR)
                        .define('f', LootModule.IRON_GOLEM_ESSENCE.get())
                        .define('g', LootModule.BLAZE_ESSENCE.get())
                        .define('S', createTrinketIngredient("base_gold_ring")),
                "gfgfg", "ftgtf", "ggSgg", "ftgtf", "gfgfg");
        build(consumer, trinket("fireresist_ring_reforge"), XpRecipeBuilder.shapedRecipe(createTrinketStack("fireresist_ring"))
                        .define('g', LootModule.BLAZE_ESSENCE.get())
                        .define('S', createTrinketIngredient("fireresist_ring")),
                "     ", " ggg ", " gSg ", " ggg ", "     ");

        build(consumer, trinket("nightvision_ring"), XpRecipeBuilder.shapedRecipe(createTrinketStack("nightvision_ring"))
                        .define('t', Items.SPIDER_EYE)
                        .define('w', Items.ENDER_EYE)
                        .define('f', Items.FERMENTED_SPIDER_EYE)
                        .define('g', LootModule.SPIDER_ESSENCE.get())
                        .define('S', createTrinketIngredient("base_gold_ring")),
                "tfwft", "wtgfw", "wgSgt", "fwgtf", "twfwf");
        build(consumer, trinket("nightvision_ring_reforge"), XpRecipeBuilder.shapedRecipe(createTrinketStack("nightvision_ring"))
                        .define('g', LootModule.SPIDER_ESSENCE.get())
                        .define('S', createTrinketIngredient("nightvision_ring")),
                "     ", " ggg ", " gSg ", " ggg ", "     ");

        build(consumer, trinket("stepassist_ring"), XpRecipeBuilder.shapedRecipe(createTrinketStack("stepassist_ring"))
                        .define('t', ItemTags.STAIRS)
                        .define('w', ItemTags.WOODEN_STAIRS)
                        .define('g', LootModule.SPIDER_ESSENCE.get())
                        .define('S', createTrinketIngredient("base_gold_ring")),
                "twtwt", "wtgtw", "tgSgt", "wtgtw", "twtwt");
        build(consumer, trinket("stepassist_ring_reforge"), XpRecipeBuilder.shapedRecipe(createTrinketStack("stepassist_ring"))
                        .define('g', LootModule.SPIDER_ESSENCE.get())
                        .define('S', createTrinketIngredient("stepassist_ring")),
                "     ", " ggg ", " gSg ", " ggg ", "     ");

        build(consumer, trinket("base_heart"), XpRecipeBuilder.shapedRecipe(createTrinketStack("base_heart"))
                        .define('g', Items.REDSTONE_BLOCK),
                " ggg ", "ggggg", "ggggg", " ggg ", "  g  ");

        build(consumer, trinket("super_health"), XpRecipeBuilder.shapedRecipe(createTrinketStack("super_health"))
                        .define('t', Items.GHAST_TEAR)
                        .define('w', Items.NETHER_STAR)
                        .define('f', LootModule.ENDERMAN_ESSENCE.get())
                        .define('g', LootModule.IRON_GOLEM_ESSENCE.get())
                        .define('S', createTrinketIngredient("base_heart")),
                "tffft", "fgwgf", "fgSgf", "fgwgf", "tffft");
        build(consumer, trinket("super_health_reforge"), XpRecipeBuilder.shapedRecipe(createTrinketStack("super_health"))
                        .define('g', LootModule.IRON_GOLEM_ESSENCE.get())
                        .define('S', createTrinketIngredient("super_health")),
                "     ", " ggg ", " gSg ", " ggg ", "     ");

        build(consumer, trinket("cure"), XpRecipeBuilder.shapedRecipe(createTrinketStack("cure"))
                        .define('t', Items.END_CRYSTAL)
                        .define('w', Items.NETHER_STAR)
                        .define('f', LootModule.IRON_GOLEM_ESSENCE.get())
                        .define('g', LootModule.ENDERMAN_ESSENCE.get())
                        .define('S', createTrinketIngredient("base_heart")),
                "tffft", "fgwgf", "fgSgf", "fgwgf", "tffft");
        build(consumer, trinket("cure_reforge"), XpRecipeBuilder.shapedRecipe(createTrinketStack("cure"))
                        .define('g', LootModule.IRON_GOLEM_ESSENCE.get())
                        .define('S', createTrinketIngredient("cure")),
                "     ", " ggg ", " gSg ", " ggg ", "     ");

        build(consumer, trinket("base_gold_ring_diamond"), XpRecipeBuilder.shapedRecipe(createTrinketStack("base_gold_ring_diamond"))
                        .define('g', Tags.Items.INGOTS_GOLD),
                " gdg ", "g   g", "g   g", "g   g", " ggg ");

        build(consumer, trinket("absorption_ring"), XpRecipeBuilder.shapedRecipe(createTrinketStack("absorption_ring"))
                        .define('t', Items.GHAST_TEAR)
                        .define('f', LootModule.IRON_GOLEM_ESSENCE.get())
                        .define('g', LootModule.WITHER_ESSENCE.get())
                        .define('S', createTrinketIngredient("base_gold_ring_diamond")),
                "gfgfg", "ftgtf", "ggSgg", "ftgtf", "gfgfg");
        build(consumer, trinket("absorption_ring_reforge"), XpRecipeBuilder.shapedRecipe(createTrinketStack("absorption_ring"))
                        .define('g', LootModule.WITHER_ESSENCE.get())
                        .define('S', createTrinketIngredient("absorption_ring")),
                "     ", " ggg ", " gSg ", " ggg ", "     ");

        build(consumer, trinket("regeneration_ring"), XpRecipeBuilder.shapedRecipe(createTrinketStack("regeneration_ring"))
                        .define('t', Items.GHAST_TEAR)
                        .define('w', LootModule.WITHER_ESSENCE.get())
                        .define('g', LootModule.ZOMBIE_ESSENCE.get())
                        .define('S', createTrinketIngredient("base_gold_ring_diamond")),
                "ggggg", "gwtwg", "gtStg", "gwtwg", "ggggg");
        build(consumer, trinket("regeneration_ring_reforge"), XpRecipeBuilder.shapedRecipe(createTrinketStack("regeneration_ring"))
                        .define('g', LootModule.WITHER_ESSENCE.get())
                        .define('S', createTrinketIngredient("regeneration_ring")),
                "     ", " ggg ", " gSg ", " ggg ", "     ");

        build(consumer, trinket("strength_ring"), XpRecipeBuilder.shapedRecipe(createTrinketStack("strength_ring"))
                        .define('t', Items.NETHER_STAR)
                        .define('I', Items.IRON_BLOCK)
                        .define('w', LootModule.WITHER_ESSENCE.get())
                        .define('g', LootModule.IRON_GOLEM_ESSENCE.get())
                        .define('S', createTrinketIngredient("base_gold_ring_diamond")),
                "IgggI", "gwtwg", "gtStg", "gwtwg", "IgggI");
        build(consumer, trinket("strength_ring_reforge"), XpRecipeBuilder.shapedRecipe(createTrinketStack("strength_ring"))
                        .define('g', LootModule.IRON_GOLEM_ESSENCE.get())
                        .define('S', createTrinketIngredient("strength_ring")),
                "     ", " ggg ", " gSg ", " ggg ", "     ");


        build(consumer, trinket("warp_pearl"), XpRecipeBuilder.shapedRecipe(createTrinketStack("warp_pearl"))
                        .define('g', LootModule.ENDERMAN_ESSENCE.get()),
                " ooo ", "ogggo", "ogggo", "ogggo", " ooo ");
        build(consumer, trinket("warp_pearl_reforge"), XpRecipeBuilder.shapedRecipe(createTrinketStack("warp_pearl"))
                        .define('g', LootModule.ENDERMAN_ESSENCE.get())
                        .define('S', createTrinketIngredient("warp_pearl")),
                "     ", " ggg ", " gSg ", " ggg ", "     ");
    }

    private ResourceLocation trinket(String id) {
        return new ResourceLocation(FancyTrinkets.MODID, id);
    }

    private ItemStack createTrinketStack(String id) {
        ResourceLocation trinkedId = trinket(id);
        return TrinketItem.createTrinketStack(DefaultTrinkets.DEFAULT_TRINKETS.get(trinkedId).trinketDescription(),
                trinkedId);
    }

    @NotNull
    private PartialNBTIngredient createTrinketIngredient(String id) {
        ItemStack stack = createTrinketStack(id);
        return PartialNBTIngredient.of(stack.getItem(), stack.getTag());
    }

}
