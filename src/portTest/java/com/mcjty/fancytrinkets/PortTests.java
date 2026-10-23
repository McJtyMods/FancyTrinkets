package com.mcjty.fancytrinkets;

import com.google.gson.JsonParser;
import com.mcjty.fancytrinkets.datapack.CustomRegistries;
import com.mcjty.fancytrinkets.modules.trinkets.TrinketsModule;
import com.mcjty.fancytrinkets.modules.trinkets.data.TrinketData;
import com.mcjty.fancytrinkets.modules.trinkets.items.TrinketItem;
import com.mcjty.fancytrinkets.modules.xpcrafter.XpCrafterModule;
import com.mcjty.fancytrinkets.modules.xpcrafter.blocks.ExperienceCrafterBE;
import com.mcjty.fancytrinkets.modules.xpcrafter.recipe.XpRecipeSerializer;
import com.mcjty.fancytrinkets.playerdata.PlayerEffectData;
import com.mcjty.fancytrinkets.setup.Registration;
import com.mojang.serialization.JsonOps;
import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mcjty.lib.container.GenericItemHandler;
import mcjty.lib.typed.TypedMap;
import mcjty.lib.varia.InventoryTools;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.context.ContextKeySet;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.display.SlotDisplayContext;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemUtil;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.neoforged.neoforge.resource.NeoForgeReloadListeners;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/** Checks run inside a real server only when -PportTests is supplied. */
@EventBusSubscriber(modid = FancyTrinkets.MODID)
public final class PortTests {
    private static final Map<String, Consumer<GameTestHelper>> TESTS = Map.of(
            "legacy_data", PortTests::legacyData,
            "all_xp_recipes", PortTests::allXpRecipes,
            "loot_conditions", PortTests::lootConditions,
            "curios_effects", PortTests::curiosEffects);

    private static Identifier id(String path) { return Identifier.fromNamespaceAndPath(FancyTrinkets.MODID, path); }

    @SubscribeEvent
    public static void functions(RegisterEvent event) {
        TESTS.forEach((name, test) -> event.register(Registries.TEST_FUNCTION, id(name), () -> test));
    }

    @SubscribeEvent
    public static void tests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(id("port"));
        TESTS.forEach((name, test) -> event.registerTest(id(name), new FunctionGameTestInstance(
                ResourceKey.create(Registries.TEST_FUNCTION, id(name)),
                new TestData<>(environment, Identifier.withDefaultNamespace("empty"), 100, 0, true))));
    }

    private static void legacyData(GameTestHelper helper) {
        var world = helper.getLevel();
        var jsonOps = world.registryAccess().createSerializationContext(JsonOps.INSTANCE);
        // Literal 1.21 item component layout, including bonus effects.
        var oldItem = JsonParser.parseString("""
                {"id":"fancytrinkets:star","count":1,"components":{"fancytrinkets:trinket_data":
                  {"id":"fancytrinkets:flight_star","effects":["fancytrinkets:luck"]}}}
                """);
        var stack = ItemStack.CODEC.parse(jsonOps, oldItem).getOrThrow();
        var expected = new TrinketData(id("flight_star"), List.of(id("luck")));
        helper.assertValueEqual(stack.get(TrinketsModule.TRINKET_DATA), expected, "Legacy trinket data");
        var nbtOps = world.registryAccess().createSerializationContext(NbtOps.INSTANCE);
        var saved = ItemStack.CODEC.encodeStart(nbtOps, stack).getOrThrow();
        var loaded = ItemStack.CODEC.parse(nbtOps, saved).getOrThrow();
        helper.assertValueEqual(loaded.get(TrinketsModule.TRINKET_DATA), expected, "Saved trinket bonuses");
        helper.assertTrue(loaded.getCapability(Registration.TRINKET_ITEM_CAPABILITY) != null, "Trinket capability");
        helper.assertTrue(CuriosApi.getCurio(loaded).isPresent(), "Curios capability");

        var xp = new CompoundTag();
        xp.putInt("experience", 4321);
        var attachments = new CompoundTag();
        attachments.put("fancytrinkets:experience_crafter_data", xp);
        var legacy = new CompoundTag();
        legacy.putString("id", "fancytrinkets:experience_crafter");
        legacy.put("neoforge:attachments", attachments);
        var pos = helper.absolutePos(new BlockPos(0, 1, 0));
        var state = XpCrafterModule.EXPERIENCE_CRAFTER.block().get().defaultBlockState();
        helper.assertValueEqual(state.getDestroySpeed(world, pos), 2.0f, "Original crafter hardness");
        helper.assertTrue(state.requiresCorrectToolForDrops(), "Crafter still requires the correct tool");
        var restored = (ExperienceCrafterBE) BlockEntity.loadStatic(pos, state, legacy, world.registryAccess());
        helper.assertValueEqual(restored.getExperience(), 4321, "Legacy crafter XP");
        var reloaded = (ExperienceCrafterBE) BlockEntity.loadStatic(pos, state,
                restored.saveWithFullMetadata(world.registryAccess()), world.registryAccess());
        helper.assertValueEqual(reloaded.getExperience(), 4321, "XP survives save/load");
        var playerData = PlayerEffectData.CODEC.parse(JsonOps.INSTANCE, JsonParser.parseString(
                "{\"toggles\":[\"flight\"],\"damageReduction\":{\"fall\":0.5}}" )).getOrThrow();
        helper.assertTrue(playerData.isToggleOn("flight"), "Legacy player toggle");
        helper.assertValueEqual(playerData.getDamageReduction("fall"), 0.5f, "Legacy damage reduction");
        helper.succeed();
    }

    private static void allXpRecipes(GameTestHelper helper) {
        var world = helper.getLevel();
        var recipes = world.recipeAccess().recipeMap().byType(XpCrafterModule.XP_RECIPE_TYPE.get());
        helper.assertValueEqual(recipes.size(), 34, "All XP recipes loaded");
        var pos = helper.absolutePos(new BlockPos(0, 1, 0));
        for (var holder : recipes) {
            var recipe = holder.value();
            var buffer = new RegistryFriendlyByteBuf(Unpooled.buffer(), world.registryAccess());
            try {
                XpRecipeSerializer.STREAM_CODEC.encode(buffer, recipe);
                var synced = XpRecipeSerializer.STREAM_CODEC.decode(buffer);
                helper.assertValueEqual(synced.getId(), recipe.getId(), "Synced recipe id");
                helper.assertTrue(ItemStack.matches(synced.getResultItem(), recipe.getResultItem()), "Synced result components");
            } finally { buffer.release(); }
            world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            world.setBlock(pos, XpCrafterModule.EXPERIENCE_CRAFTER.block().get().defaultBlockState(), 3);
            var crafter = (ExperienceCrafterBE) world.getBlockEntity(pos);
            var items = world.getCapability(Capabilities.Item.BLOCK, pos, null);
            helper.assertTrue(items != null, "Crafter item capability");
            var input = new ArrayList<ItemStack>();
            var context = SlotDisplayContext.fromLevel(world);
            for (int y = 0; y < 5; y++) {
                for (int x = 0; x < 5; x++) {
                    var ingredient = x < recipe.getWidth() && y < recipe.getHeight()
                            ? recipe.getIngredients().get(y * recipe.getWidth() + x) : java.util.Optional.<net.minecraft.world.item.crafting.Ingredient>empty();
                    var material = ingredient.map(i -> i.display().resolveForStacks(context).getFirst().copyWithCount(2)).orElse(ItemStack.EMPTY);
                    input.add(material.copy());
                    InventoryTools.modifier(items).set(ExperienceCrafterBE.SLOT_GRID + y * 5 + x, ItemResource.of(material), material.getCount());
                }
            }
            helper.assertTrue(recipe.matches(CraftingInput.of(5, 5, input), world), "Recipe matches: " + recipe.getId());
            helper.assertTrue(!crafter.getPreviewOutput().isEmpty(), "Craft preview: " + recipe.getId());
            var selected = world.recipeAccess().getRecipeFor(XpCrafterModule.XP_RECIPE_TYPE.get(),
                    CraftingInput.of(5, 5, input), world).orElseThrow().value();
            crafter.executeServerCommand("craft", null, TypedMap.EMPTY);
            var output = ItemUtil.getStack(items, ExperienceCrafterBE.SLOT_OUTPUT);
            helper.assertTrue(!output.isEmpty(), "Crafted: " + recipe.getId());
            helper.assertValueEqual(output.get(TrinketsModule.TRINKET_DATA).trinketId(),
                    selected.getResultItem().get(TrinketsModule.TRINKET_DATA).trinketId(), "Crafted correct trinket");
            for (int i = 0; i < 25; i++) {
                helper.assertValueEqual(ItemUtil.getStack(items, ExperienceCrafterBE.SLOT_GRID + i).getCount(),
                        input.get(i).isEmpty() ? 0 : 1, "Consumes one ingredient per occupied slot");
            }
            helper.assertTrue(!recipe.matches(CraftingInput.of(1, 1, List.of(new ItemStack(Items.DIRT))), world), "Wrong grid rejected");
        }
        helper.succeed();
    }

    private static void lootConditions(GameTestHelper helper) {
        var world = helper.getLevel();
        var manager = world.getServer().getServerResources().managers()
                .getListener(NeoForgeReloadListeners.LOOT_MODIFIERS_KEY);
        var essence = manager.getModifier(id("wither_essence"));
        var trinket = manager.getModifier(id("dragon_trinket"));
        helper.assertTrue(essence != null && trinket != null, "Generated loot modifiers loaded");
        var player = new net.neoforged.neoforge.common.util.FakePlayer(world,
                new com.mojang.authlib.GameProfile(java.util.UUID.randomUUID(), "loot-test"));
        var parameters = new ContextKeySet.Builder().optional(LootContextParams.LAST_DAMAGE_PLAYER).build();
        var playerKill = new LootParams.Builder(world).withParameter(LootContextParams.LAST_DAMAGE_PLAYER, player).create(parameters);
        var otherKill = new LootParams.Builder(world).create(parameters);
        for (boolean correctTable : List.of(false, true)) {
            for (boolean killedByPlayer : List.of(false, true)) {
                var table = Identifier.withDefaultNamespace(correctTable ? "entities/wither" : "entities/zombie");
                var context = new LootContext.Builder(killedByPlayer ? playerKill : otherKill)
                        .withQueriedLootTableId(table).withOptionalRandomSeed(5).create(Optional.empty());
                var loot = essence.apply(new ObjectArrayList<>(), context);
                helper.assertValueEqual(!loot.isEmpty(), correctTable && killedByPlayer,
                        "Essence requires both the correct entity and a player kill");
            }
        }
        var dragon = new LootContext.Builder(otherKill).withQueriedLootTableId(Identifier.withDefaultNamespace("entities/ender_dragon"))
                .withOptionalRandomSeed(5).create(Optional.empty());
        var loot = trinket.apply(new ObjectArrayList<>(), dragon);
        helper.assertValueEqual(loot.size(), 1, "Dragon trinket drop");
        helper.assertValueEqual(loot.getFirst().get(TrinketsModule.TRINKET_DATA).trinketId(), id("power_star"), "Dragon trinket data");
        var wrongTable = new LootContext.Builder(otherKill).withQueriedLootTableId(Identifier.withDefaultNamespace("entities/zombie"))
                .withOptionalRandomSeed(5).create(Optional.empty());
        helper.assertTrue(trinket.apply(new ObjectArrayList<>(), wrongTable).isEmpty(), "Trinket requires the correct loot table");
        helper.succeed();
    }

    private static void curiosEffects(GameTestHelper helper) {
        var world = helper.getLevel();
        var player = new net.neoforged.neoforge.common.util.FakePlayer(world,
                new com.mojang.authlib.GameProfile(java.util.UUID.randomUUID(), "port-test"));
        player.setGameMode(GameType.SURVIVAL);
        var registry = world.registryAccess().lookupOrThrow(CustomRegistries.TRINKET_REGISTRY_KEY);
        var strength = TrinketItem.createTrinketStack(registry.getValue(id("strength_ring")), id("strength_ring"));
        var inventory = CuriosApi.getCuriosInventory(player).orElseThrow();
        inventory.loadDatapacks();
        inventory.setEquippedCurio("ring", 0, strength);
        helper.assertTrue(inventory.findCurios(stack -> stack.getCapability(Registration.TRINKET_ITEM_CAPABILITY) != null).size() == 1,
                "Equipped trinket found through Curios");
        var curio = CuriosApi.getCurio(strength).orElseThrow();
        var slot = new SlotContext("ring", player, 0, false, true);
        double base = player.getAttributeValue(Attributes.ATTACK_DAMAGE);
        curio.onEquip(slot, ItemStack.EMPTY);
        curio.curioTick(slot);
        com.mcjty.fancytrinkets.playerdata.PlayerEffects.getPlayerEffects(player).tick(player);
        helper.assertTrue(player.getAttributeValue(Attributes.ATTACK_DAMAGE) > base, "Equipped strength effect applied");
        curio.onUnequip(slot, ItemStack.EMPTY);
        helper.assertValueEqual(player.getAttributeValue(Attributes.ATTACK_DAMAGE), base, "Strength removed on unequip");
        var flight = TrinketItem.createTrinketStack(registry.getValue(id("flight_star")), id("flight_star"));
        player.setData(Registration.PLAYER_EFFECTS, PlayerEffectData.DEFAULT.toggle("flight"));
        var flightCurio = CuriosApi.getCurio(flight).orElseThrow();
        flightCurio.curioTick(slot);
        helper.assertTrue(player.getAbilities().mayfly, "Enabled flight effect applied");
        flightCurio.onUnequip(slot, ItemStack.EMPTY);
        helper.assertTrue(!player.getAbilities().mayfly, "Flight removed on unequip");
        helper.succeed();
    }
}
