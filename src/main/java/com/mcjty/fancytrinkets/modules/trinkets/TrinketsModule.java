package com.mcjty.fancytrinkets.modules.trinkets;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.api.ITrinketItem;
import com.mcjty.fancytrinkets.datapack.BonusTable;
import com.mcjty.fancytrinkets.datapack.CustomRegistries;
import com.mcjty.fancytrinkets.datapack.TrinketDescription;
import com.mcjty.fancytrinkets.datapack.TrinketSet;
import com.mcjty.fancytrinkets.modules.loot.LootModule;
import com.mcjty.fancytrinkets.modules.trinkets.items.TrinketItem;
import com.mcjty.fancytrinkets.modules.xpcrafter.recipe.XpRecipeBuilder;
import com.mcjty.fancytrinkets.setup.Registration;
import mcjty.lib.datagen.DataGen;
import mcjty.lib.datagen.Dob;
import mcjty.lib.modules.IModule;
import mcjty.lib.varia.TagTools;
import mcjty.lib.varia.Tools;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.crafting.PartialNBTIngredient;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.mcjty.fancytrinkets.FancyTrinkets.MODID;
import static com.mcjty.fancytrinkets.FancyTrinkets.tab;

public class TrinketsModule implements IModule {

    public static final Map<ResourceLocation, TrinketInfo> TRINKET_ITEMS = new HashMap<>();

    public static final TagKey<Item> RING_TAG = TagTools.createItemTagKey(new ResourceLocation(CuriosApi.MODID, SlotTypePreset.RING.getIdentifier()));
    public static final TagKey<Item> BELT_TAG = TagTools.createItemTagKey(new ResourceLocation(CuriosApi.MODID, SlotTypePreset.BELT.getIdentifier()));
    public static final TagKey<Item> BRACELET_TAG = TagTools.createItemTagKey(new ResourceLocation(CuriosApi.MODID, SlotTypePreset.BRACELET.getIdentifier()));
    public static final TagKey<Item> CHARM_TAG = TagTools.createItemTagKey(new ResourceLocation(CuriosApi.MODID, SlotTypePreset.CHARM.getIdentifier()));
    public static final TagKey<Item> NECKLACE_TAG = TagTools.createItemTagKey(new ResourceLocation(CuriosApi.MODID, SlotTypePreset.NECKLACE.getIdentifier()));
    public static final TagKey<Item> HEAD_TAG = TagTools.createItemTagKey(new ResourceLocation(CuriosApi.MODID, SlotTypePreset.HEAD.getIdentifier()));
    public static final TagKey<Item> BODY_TAG = TagTools.createItemTagKey(new ResourceLocation(CuriosApi.MODID, SlotTypePreset.BODY.getIdentifier()));

    public static final RegistryObject<TrinketItem> GOLD_RING = trinket("gold_ring", "item/gold_ring", "Base golden ring item", RING_TAG);
    public static final RegistryObject<TrinketItem> GOLD_RING_BLUE = trinket("gold_ring_blue", "item/gold_ring_blue", "Base golden ring item", RING_TAG);
    public static final RegistryObject<TrinketItem> GOLD_RING_GREEN = trinket("gold_ring_green", "item/gold_ring_green", "Base golden ring item", RING_TAG);
    public static final RegistryObject<TrinketItem> GOLD_RING_RED = trinket("gold_ring_red", "item/gold_ring_red", "Base golden ring item", RING_TAG);
    public static final RegistryObject<TrinketItem> GOLD_RING_DIAMOND = trinket("gold_ring_diamond", "item/gold_ring_diamond", "Base golden ring item", RING_TAG);
    public static final RegistryObject<TrinketItem> SILVER_RING = trinket("silver_ring", "item/silver_ring", "Base silver ring item", RING_TAG);
    public static final RegistryObject<TrinketItem> SILVER_RING_BLUE = trinket("silver_ring_blue", "item/silver_ring_blue", "Base silver ring item", RING_TAG);
    public static final RegistryObject<TrinketItem> SILVER_RING_GREEN = trinket("silver_ring_green", "item/silver_ring_green", "Base silver ring item", RING_TAG);
    public static final RegistryObject<TrinketItem> SILVER_RING_RED = trinket("silver_ring_red", "item/silver_ring_red", "Base silver ring item", RING_TAG);
    public static final RegistryObject<TrinketItem> SILVER_RING_DIAMOND = trinket("silver_ring_diamond", "item/silver_ring_diamond", "Base silver ring item", RING_TAG);
    public static final RegistryObject<TrinketItem> OBSIDIAN_RING = trinket("obsidian_ring", "item/obsidian_ring", "Base obsidian ring item", RING_TAG);
    public static final RegistryObject<TrinketItem> OBSIDIAN_RING_DIAMOND = trinket("obsidian_ring_diamond", "item/obsidian_ring_diamond", "Base obsidian ring item", RING_TAG);

    public static final RegistryObject<TrinketItem> STAR = trinket("star", "item/star", "Base star item",
            RING_TAG, BELT_TAG, BRACELET_TAG, CHARM_TAG, NECKLACE_TAG, HEAD_TAG, BODY_TAG);
    public static final RegistryObject<TrinketItem> HEART = trinket("heart", "item/heart", "Base heart item",
            RING_TAG, BELT_TAG, BRACELET_TAG, CHARM_TAG, NECKLACE_TAG, HEAD_TAG, BODY_TAG);
    public static final RegistryObject<TrinketItem> HEART_BODY = trinket("heart_body", "item/heart", "Base heart item (body)", BODY_TAG);

    public static final RegistryObject<TrinketItem> FEATHER = trinket("feather", "item/feather", "Feather star item",
            BELT_TAG, CHARM_TAG, NECKLACE_TAG, HEAD_TAG, BODY_TAG);

    public static final RegistryObject<TrinketItem> BLACK_PEARL = trinket("black_pearl", "item/black_pearl", "Black pearl", CHARM_TAG);
    public static final RegistryObject<TrinketItem> BLUE_PEARL = trinket("blue_pearl", "item/blue_pearl", "Blue pearl", CHARM_TAG);
    public static final RegistryObject<TrinketItem> YELLOW_PEARL = trinket("yellow_pearl", "item/yellow_pearl", "Yellow pearl", CHARM_TAG);
    public static final RegistryObject<TrinketItem> PURPLE_PEARL = trinket("purple_pearl", "item/purple_pearl", "Purple pearl", CHARM_TAG);
    public static final RegistryObject<TrinketItem> SHINY_PEARL = trinket("shiny_pearl", "item/shiny_pearl", "Shiny pearl", CHARM_TAG);

    public static final RegistryObject<TrinketItem> LEATHER_BELT = trinket("leather_belt", "item/leather_belt", "Leather Belt", BELT_TAG);
    public static final RegistryObject<TrinketItem> BLUE_BELT = trinket("blue_belt", "item/blue_belt", "Blue Belt", BELT_TAG);

    public static final RegistryObject<TrinketItem> CHARM1 = trinket("charm1", "item/charm1", "Charm", CHARM_TAG);
    public static final RegistryObject<TrinketItem> CHARM2 = trinket("charm2", "item/charm2", "Charm", CHARM_TAG);

    public static final Capability<ICurio> CURIOS_CAPABILITY = CuriosCapability.ITEM;

    public TrinketsModule() {
        MinecraftForge.EVENT_BUS.addListener(this::onServerStarting);

        DefaultTrinkets.init();
        DefaultBonusTables.init();
    }

    public static RegistryObject<TrinketItem> trinket(String id, String texture, String description, TagKey... tags) {
        RegistryObject<TrinketItem> object = Registration.ITEMS.register(id, tab(TrinketItem::new));
        TRINKET_ITEMS.put(object.getId(), new TrinketInfo(new ResourceLocation(MODID, id), texture, description, object, tags));
        return object;
    }

    @Override
    public void init(FMLCommonSetupEvent event) {
    }

    public void onServerStarting(ServerStartingEvent event) {
        registerTrinkets(event.getServer().overworld());
    }

    private void registerTrinkets(ServerLevel level) {
        Registry<TrinketDescription> registry = Tools.getRegistryAccess(level).registryOrThrow(CustomRegistries.TRINKET_REGISTRY_KEY);
        for (ResourceLocation trinket : Tools.getRegistryAccess(level).registryOrThrow(CustomRegistries.TRINKET_SET_REGISTRY_KEY).get(new ResourceLocation(MODID, "standard")).trinkets()) {
            TrinketDescription description = registry.get(trinket);
            ResourceLocation itemId = description.item();
            Item item = ForgeRegistries.ITEMS.getValue(itemId);
            if (item == null || item == Items.AIR) {
                throw new RuntimeException("Can't find item '" + itemId.toString() + "'!");
            }
            if (item instanceof ITrinketItem trinketItem) {
                trinketItem.registerTrinketInstance(level, trinket, description);
            } else {
                throw new RuntimeException("Item '" + itemId.toString() + "' is not an ITrinketItem!");
            }
        }
    }

    @Override
    public void initClient(FMLClientSetupEvent event) {
    }

    @Override
    public void initConfig() {

    }

    @Override
    public void initDatagen(DataGen dataGen) {
        dataGen.addCodecProvider("bonus", "fancytrinkets/bonustables", BonusTable.CODEC);
        dataGen.addCodecProvider("trinkets", "fancytrinkets/trinkets", TrinketDescription.CODEC);
        dataGen.addCodecProvider("trinketsets", "fancytrinkets/trinketsets", TrinketSet.CODEC);
        dataGen.add(
                Dob.builder()
                        .codecObjectSupplier("bonus", () -> DefaultBonusTables.DEFAULT_BONUS_TABLES.entrySet().stream()
                                .map(entry -> Pair.of(entry.getKey(), entry.getValue().bonusTable()))
                                .collect(Collectors.toMap(Pair::getLeft, Pair::getRight))),
                Dob.builder()
                        .codecObjectSupplier("trinkets", () -> DefaultTrinkets.DEFAULT_TRINKETS.entrySet().stream()
                                .map(entry -> Pair.of(entry.getKey(), entry.getValue().trinketDescription()))
                                .collect(Collectors.toMap(Pair::getLeft, Pair::getRight))),
                Dob.builder()
                        .codecObjectSupplier("trinketsets", () -> {
                            List<ResourceLocation> trinkets = new ArrayList<>(DefaultTrinkets.DEFAULT_TRINKETS.keySet());
                            return Map.of(new ResourceLocation(FancyTrinkets.MODID, "standard"), new TrinketSet(trinkets));
                        })
        );

        for (Map.Entry<ResourceLocation, TrinketsModule.TrinketInfo> entry : TrinketsModule.TRINKET_ITEMS.entrySet()) {
            TrinketsModule.TrinketInfo trinket = entry.getValue();
            dataGen.add(
                    Dob.itemBuilder(trinket.item())
                            .name(trinket.description())
                            .generatedItem(trinket.texture())
                            .itemTags(List.of(trinket.tags()))
            );
        }

        dataGen.add(
                Dob.builder()
                        .recipeConsumer(() -> consumer -> {
                            XpRecipeBuilder.shapedRecipe(createTrinketStack("base_star"))
                                    .define('g', Tags.Items.DUSTS_PRISMARINE)
                                    .patternLine("g g g")
                                    .patternLine("  g  ")
                                    .patternLine("ggggg")
                                    .patternLine("  g  ")
                                    .patternLine("g g g")
                                    .build(consumer, trinket("base_star"));
                            XpRecipeBuilder.shapedRecipe(createTrinketStack("flight_star"))
                                    .define('f', Tags.Items.FEATHERS)
                                    .define('t', Items.GHAST_TEAR)
                                    .define('g', LootModule.GHAST_ESSENCE.get())
                                    .define('S', createTrinketIngredient("base_star"))
                                    .patternLine("gfgfg")
                                    .patternLine("fgtgf")
                                    .patternLine("gtStg")
                                    .patternLine("fgtgf")
                                    .patternLine("gfgfg")
                                    .build(consumer, trinket("flight_star"));
                            XpRecipeBuilder.shapedRecipe(createTrinketStack("flight_star"))
                                    .define('g', LootModule.GHAST_ESSENCE.get())
                                    .define('S', createTrinketIngredient("flight_star"))
                                    .patternLine("     ")
                                    .patternLine(" ggg ")
                                    .patternLine(" gSg ")
                                    .patternLine(" ggg ")
                                    .patternLine("     ")
                                    .build(consumer, trinket("flight_star_reforge"));
                            XpRecipeBuilder.shapedRecipe(createTrinketStack("power_star"))
                                    .define('i', Tags.Items.INGOTS_IRON)
                                    .define('t', Items.DRAGON_HEAD)
                                    .define('g', LootModule.DRAGON_ESSENCE.get())
                                    .define('w', LootModule.WITHER_ESSENCE.get())
                                    .define('S', createTrinketIngredient("base_star"))
                                    .patternLine(" iii ")
                                    .patternLine("iwtwi")
                                    .patternLine("igSgi")
                                    .patternLine("iwgwi")
                                    .patternLine(" iii ")
                                    .build(consumer, trinket("power_star"));
                            XpRecipeBuilder.shapedRecipe(createTrinketStack("power_star"))
                                    .define('g', LootModule.WITHER_ESSENCE.get())
                                    .define('S', createTrinketIngredient("power_star"))
                                    .patternLine("     ")
                                    .patternLine(" ggg ")
                                    .patternLine(" gSg ")
                                    .patternLine(" ggg ")
                                    .patternLine("     ")
                                    .build(consumer, trinket("power_star_reforge"));
                            XpRecipeBuilder.shapedRecipe(createTrinketStack("swift_star"))
                                    .define('t', Tags.Items.GEMS_QUARTZ)
                                    .define('g', LootModule.SPIDER_ESSENCE.get())
                                    .define('S', createTrinketIngredient("base_star"))
                                    .patternLine("ttgtt")
                                    .patternLine("tgggt")
                                    .patternLine("ggSgg")
                                    .patternLine("tgggt")
                                    .patternLine("ttgtt")
                                    .build(consumer, trinket("swift_star"));
                            XpRecipeBuilder.shapedRecipe(createTrinketStack("swift_star"))
                                    .define('g', LootModule.SPIDER_ESSENCE.get())
                                    .define('S', createTrinketIngredient("swift_star"))
                                    .patternLine("     ")
                                    .patternLine(" ggg ")
                                    .patternLine(" gSg ")
                                    .patternLine(" ggg ")
                                    .patternLine("     ")
                                    .build(consumer, trinket("swift_star_reforge"));
                            XpRecipeBuilder.shapedRecipe(createTrinketStack("grow_charm"))
                                    .define('f', Tags.Items.BONES)
                                    .define('t', Items.BONE_MEAL)
                                    .define('w', Items.DIAMOND_BLOCK)
                                    .define('g', LootModule.SKELETON_ESSENCE.get())
                                    .patternLine(" fwf ")
                                    .patternLine("fgggf")
                                    .patternLine("tgggt")
                                    .patternLine("fgggf")
                                    .patternLine(" fwf ")
                                    .build(consumer, trinket("grow_charm"));
                            XpRecipeBuilder.shapedRecipe(createTrinketStack("grow_charm"))
                                    .define('g', LootModule.SKELETON_ESSENCE.get())
                                    .define('S', createTrinketIngredient("grow_charm"))
                                    .patternLine("     ")
                                    .patternLine(" ggg ")
                                    .patternLine(" gSg ")
                                    .patternLine(" ggg ")
                                    .patternLine("     ")
                                    .build(consumer, trinket("grow_charm_reforge"));
                            XpRecipeBuilder.shapedRecipe(createTrinketStack("slowfalling_feather"))
                                    .define('o', Tags.Items.ENDER_PEARLS)
                                    .define('f', Tags.Items.FEATHERS)
                                    .define('g', LootModule.CHICKEN_ESSENCE.get())
                                    .patternLine("ooofg")
                                    .patternLine("oofgg")
                                    .patternLine("ogggo")
                                    .patternLine("fggoo")
                                    .patternLine("gfooo")
                                    .build(consumer, trinket("slowfalling_feather"));
                            XpRecipeBuilder.shapedRecipe(createTrinketStack("slowfalling_feather"))
                                    .define('g', LootModule.CHICKEN_ESSENCE.get())
                                    .define('S', createTrinketIngredient("slowfalling_feather"))
                                    .patternLine("     ")
                                    .patternLine(" ggg ")
                                    .patternLine(" gSg ")
                                    .patternLine(" ggg ")
                                    .patternLine("     ")
                                    .build(consumer, trinket("slowfalling_feather_reforge"));
                            XpRecipeBuilder.shapedRecipe(createTrinketStack("base_gold_ring"))
                                    .define('g', Tags.Items.INGOTS_GOLD)
                                    .patternLine(" ggg ")
                                    .patternLine("g   g")
                                    .patternLine("g   g")
                                    .patternLine("g   g")
                                    .patternLine(" ggg ")
                                    .build(consumer, trinket("base_gold_ring"));
                            XpRecipeBuilder.shapedRecipe(createTrinketStack("lightness_ring"))
                                    .define('t', Items.DIAMOND_BLOCK)
                                    .define('w', Items.AMETHYST_BLOCK)
                                    .define('f', LootModule.IRON_GOLEM_ESSENCE.get())
                                    .define('g', LootModule.CHICKEN_ESSENCE.get())
                                    .define('S', createTrinketIngredient("base_gold_ring"))
                                    .patternLine(" wtw ")
                                    .patternLine("wfgfw")
                                    .patternLine("tgSgt")
                                    .patternLine("wfgfw")
                                    .patternLine(" wtw ")
                                    .build(consumer, trinket("lightness_ring"));
                            XpRecipeBuilder.shapedRecipe(createTrinketStack("lightness_ring"))
                                    .define('g', LootModule.CHICKEN_ESSENCE.get())
                                    .define('S', createTrinketIngredient("lightness_ring"))
                                    .patternLine("     ")
                                    .patternLine(" ggg ")
                                    .patternLine(" gSg ")
                                    .patternLine(" ggg ")
                                    .patternLine("     ")
                                    .build(consumer, trinket("lightness_ring_reforge"));
                            XpRecipeBuilder.shapedRecipe(createTrinketStack("fireresist_ring"))
                                    .define('t', Items.GHAST_TEAR)
                                    .define('f', LootModule.IRON_GOLEM_ESSENCE.get())
                                    .define('g', LootModule.BLAZE_ESSENCE.get())
                                    .define('S', createTrinketIngredient("base_gold_ring"))
                                    .patternLine("gfgfg")
                                    .patternLine("ftgtf")
                                    .patternLine("ggSgg")
                                    .patternLine("ftgtf")
                                    .patternLine("gfgfg")
                                    .build(consumer, trinket("fireresist_ring"));
                            XpRecipeBuilder.shapedRecipe(createTrinketStack("fireresist_ring"))
                                    .define('g', LootModule.BLAZE_ESSENCE.get())
                                    .define('S', createTrinketIngredient("fireresist_ring"))
                                    .patternLine("     ")
                                    .patternLine(" ggg ")
                                    .patternLine(" gSg ")
                                    .patternLine(" ggg ")
                                    .patternLine("     ")
                                    .build(consumer, trinket("fireresist_ring_reforge"));
                            XpRecipeBuilder.shapedRecipe(createTrinketStack("nightvision_ring"))
                                    .define('t', Items.SPIDER_EYE)
                                    .define('w', Items.ENDER_EYE)
                                    .define('f', Items.FERMENTED_SPIDER_EYE)
                                    .define('g', LootModule.SPIDER_ESSENCE.get())
                                    .define('S', createTrinketIngredient("base_gold_ring"))
                                    .patternLine("tfwft")
                                    .patternLine("wtgfw")
                                    .patternLine("wgSgt")
                                    .patternLine("fwgtf")
                                    .patternLine("twfwf")
                                    .build(consumer, trinket("nightvision_ring"));
                            XpRecipeBuilder.shapedRecipe(createTrinketStack("nightvision_ring"))
                                    .define('g', LootModule.SPIDER_ESSENCE.get())
                                    .define('S', createTrinketIngredient("nightvision_ring"))
                                    .patternLine("     ")
                                    .patternLine(" ggg ")
                                    .patternLine(" gSg ")
                                    .patternLine(" ggg ")
                                    .patternLine("     ")
                                    .build(consumer, trinket("nightvision_ring_reforge"));
                            XpRecipeBuilder.shapedRecipe(createTrinketStack("stepassist_ring"))
                                    .define('t', ItemTags.STAIRS)
                                    .define('w', ItemTags.WOODEN_STAIRS)
                                    .define('g', LootModule.SPIDER_ESSENCE.get())
                                    .define('S', createTrinketIngredient("base_gold_ring"))
                                    .patternLine("twtwt")
                                    .patternLine("wtgtw")
                                    .patternLine("tgSgt")
                                    .patternLine("wtgtw")
                                    .patternLine("twtwt")
                                    .build(consumer, trinket("stepassist_ring"));
                            XpRecipeBuilder.shapedRecipe(createTrinketStack("stepassist_ring"))
                                    .define('g', LootModule.SPIDER_ESSENCE.get())
                                    .define('S', createTrinketIngredient("stepassist_ring"))
                                    .patternLine("     ")
                                    .patternLine(" ggg ")
                                    .patternLine(" gSg ")
                                    .patternLine(" ggg ")
                                    .patternLine("     ")
                                    .build(consumer, trinket("stepassist_ring_reforge"));
                            XpRecipeBuilder.shapedRecipe(createTrinketStack("base_heart"))
                                    .define('g', Items.REDSTONE_BLOCK)
                                    .patternLine(" ggg ")
                                    .patternLine("ggggg")
                                    .patternLine("ggggg")
                                    .patternLine(" ggg ")
                                    .patternLine("  g  ")
                                    .build(consumer, trinket("base_heart"));
                            XpRecipeBuilder.shapedRecipe(createTrinketStack("super_health"))
                                    .define('t', Items.GHAST_TEAR)
                                    .define('w', Items.NETHER_STAR)
                                    .define('f', LootModule.ENDERMAN_ESSENCE.get())
                                    .define('g', LootModule.IRON_GOLEM_ESSENCE.get())
                                    .define('S', createTrinketIngredient("base_heart"))
                                    .patternLine("tffft")
                                    .patternLine("fgwgf")
                                    .patternLine("fgSgf")
                                    .patternLine("fgwgf")
                                    .patternLine("tffft")
                                    .build(consumer, trinket("super_health"));
                            XpRecipeBuilder.shapedRecipe(createTrinketStack("super_health"))
                                    .define('g', LootModule.IRON_GOLEM_ESSENCE.get())
                                    .define('S', createTrinketIngredient("super_health"))
                                    .patternLine("     ")
                                    .patternLine(" ggg ")
                                    .patternLine(" gSg ")
                                    .patternLine(" ggg ")
                                    .patternLine("     ")
                                    .build(consumer, trinket("super_health_reforge"));
                            XpRecipeBuilder.shapedRecipe(createTrinketStack("cure"))
                                    .define('t', Items.END_CRYSTAL)
                                    .define('w', Items.NETHER_STAR)
                                    .define('f', LootModule.IRON_GOLEM_ESSENCE.get())
                                    .define('g', LootModule.ENDERMAN_ESSENCE.get())
                                    .define('S', createTrinketIngredient("base_heart"))
                                    .patternLine("tffft")
                                    .patternLine("fgwgf")
                                    .patternLine("fgSgf")
                                    .patternLine("fgwgf")
                                    .patternLine("tffft")
                                    .build(consumer, trinket("cure"));
                            XpRecipeBuilder.shapedRecipe(createTrinketStack("cure"))
                                    .define('g', LootModule.IRON_GOLEM_ESSENCE.get())
                                    .define('S', createTrinketIngredient("cure"))
                                    .patternLine("     ")
                                    .patternLine(" ggg ")
                                    .patternLine(" gSg ")
                                    .patternLine(" ggg ")
                                    .patternLine("     ")
                                    .build(consumer, trinket("cure_reforge"));
                            XpRecipeBuilder.shapedRecipe(createTrinketStack("base_gold_ring_diamond"))
                                    .define('d', Items.DIAMOND)
                                    .define('g', Tags.Items.INGOTS_GOLD)
                                    .patternLine(" gdg ")
                                    .patternLine("g   g")
                                    .patternLine("g   g")
                                    .patternLine("g   g")
                                    .patternLine(" ggg ")
                                    .build(consumer, trinket("base_gold_ring_diamond"));
                            XpRecipeBuilder.shapedRecipe(createTrinketStack("absorption_ring"))
                                    .define('t', Items.GHAST_TEAR)
                                    .define('f', LootModule.IRON_GOLEM_ESSENCE.get())
                                    .define('g', LootModule.WITHER_ESSENCE.get())
                                    .define('S', createTrinketIngredient("base_gold_ring_diamond"))
                                    .patternLine("gfgfg")
                                    .patternLine("ftgtf")
                                    .patternLine("ggSgg")
                                    .patternLine("ftgtf")
                                    .patternLine("gfgfg")
                                    .build(consumer, trinket("absorption_ring"));
                            XpRecipeBuilder.shapedRecipe(createTrinketStack("absorption_ring"))
                                    .define('g', LootModule.WITHER_ESSENCE.get())
                                    .define('S', createTrinketIngredient("absorption_ring"))
                                    .patternLine("     ")
                                    .patternLine(" ggg ")
                                    .patternLine(" gSg ")
                                    .patternLine(" ggg ")
                                    .patternLine("     ")
                                    .build(consumer, trinket("absorption_ring_reforge"));
                            XpRecipeBuilder.shapedRecipe(createTrinketStack("regeneration_ring"))
                                    .define('t', Items.GHAST_TEAR)
                                    .define('w', LootModule.WITHER_ESSENCE.get())
                                    .define('g', LootModule.ZOMBIE_ESSENCE.get())
                                    .define('S', createTrinketIngredient("base_gold_ring_diamond"))
                                    .patternLine("ggggg")
                                    .patternLine("gwtwg")
                                    .patternLine("gtStg")
                                    .patternLine("gwtwg")
                                    .patternLine("ggggg")
                                    .build(consumer, trinket("regeneration_ring"));
                            XpRecipeBuilder.shapedRecipe(createTrinketStack("regeneration_ring"))
                                    .define('g', LootModule.WITHER_ESSENCE.get())
                                    .define('S', createTrinketIngredient("regeneration_ring"))
                                    .patternLine("     ")
                                    .patternLine(" ggg ")
                                    .patternLine(" gSg ")
                                    .patternLine(" ggg ")
                                    .patternLine("     ")
                                    .build(consumer, trinket("regeneration_ring_reforge"));
                            XpRecipeBuilder.shapedRecipe(createTrinketStack("strength_ring"))
                                    .define('t', Items.NETHER_STAR)
                                    .define('I', Items.IRON_BLOCK)
                                    .define('w', LootModule.WITHER_ESSENCE.get())
                                    .define('g', LootModule.IRON_GOLEM_ESSENCE.get())
                                    .define('S', createTrinketIngredient("base_gold_ring_diamond"))
                                    .patternLine("IgggI")
                                    .patternLine("gwtwg")
                                    .patternLine("gtStg")
                                    .patternLine("gwtwg")
                                    .patternLine("IgggI")
                                    .build(consumer, trinket("strength_ring"));
                            XpRecipeBuilder.shapedRecipe(createTrinketStack("strength_ring"))
                                    .define('g', LootModule.IRON_GOLEM_ESSENCE.get())
                                    .define('S', createTrinketIngredient("strength_ring"))
                                    .patternLine("     ")
                                    .patternLine(" ggg ")
                                    .patternLine(" gSg ")
                                    .patternLine(" ggg ")
                                    .patternLine("     ")
                                    .build(consumer, trinket("strength_ring_reforge"));
                            XpRecipeBuilder.shapedRecipe(createTrinketStack("warp_pearl"))
                                    .define('o', Tags.Items.ENDER_PEARLS)
                                    .define('g', LootModule.ENDERMAN_ESSENCE.get())
                                    .patternLine(" ooo ")
                                    .patternLine("ogggo")
                                    .patternLine("ogggo")
                                    .patternLine("ogggo")
                                    .patternLine(" ooo ")
                                    .build(consumer, trinket("warp_pearl"));
                            XpRecipeBuilder.shapedRecipe(createTrinketStack("warp_pearl"))
                                    .define('g', LootModule.ENDERMAN_ESSENCE.get())
                                    .define('S', createTrinketIngredient("warp_pearl"))
                                    .patternLine("     ")
                                    .patternLine(" ggg ")
                                    .patternLine(" gSg ")
                                    .patternLine(" ggg ")
                                    .patternLine("     ")
                                    .build(consumer, trinket("warp_pearl_reforge"));
                        })

        );
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

    public static record TrinketInfo(ResourceLocation id, String texture,
                                     String description,
                                     RegistryObject<TrinketItem> item,
                                     TagKey<Item>... tags) {
    }
}
