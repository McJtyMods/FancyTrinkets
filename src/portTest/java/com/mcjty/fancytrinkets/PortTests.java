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
import mcjty.lib.container.GenericItemHandler;
import mcjty.lib.typed.TypedMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.display.SlotDisplayContext;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/** Checks run inside a real server only when -PportTests is supplied. */
public final class PortTests {
    private static Identifier id(String path) { return Identifier.fromNamespaceAndPath(FancyTrinkets.MODID, path); }

    @net.fabricmc.fabric.api.gametest.v1.GameTest(maxTicks = 100)
    public void legacyData(GameTestHelper helper) {
        var world = helper.getLevel();
        var jsonOps = world.registryAccess().createSerializationContext(JsonOps.INSTANCE);
        // Literal 1.21 item component layout, including bonus effects.
        var oldItem = JsonParser.parseString("""
                {"id":"fancytrinkets:star","count":1,"components":{"fancytrinkets:trinket_data":
                  {"id":"fancytrinkets:flight_star","effects":["fancytrinkets:luck"]}}}
                """);
        var stack = ItemStack.CODEC.parse(jsonOps, oldItem).getOrThrow();
        var expected = new TrinketData(id("flight_star"), List.of(id("luck")));
        helper.assertValueEqual(stack.get(TrinketsModule.TRINKET_DATA.get()), expected, "Legacy trinket data");
        var nbtOps = world.registryAccess().createSerializationContext(NbtOps.INSTANCE);
        var saved = ItemStack.CODEC.encodeStart(nbtOps, stack).getOrThrow();
        var loaded = ItemStack.CODEC.parse(nbtOps, saved).getOrThrow();
        helper.assertValueEqual(loaded.get(TrinketsModule.TRINKET_DATA.get()), expected, "Saved trinket bonuses");
        helper.assertTrue(Registration.TRINKET_ITEM.find(loaded, null) != null, "Trinket capability");
        helper.assertTrue(loaded.getItem() instanceof eu.pb4.trinkets.api.callback.TrinketCallback, "Trinkets callback");

        var xp = new CompoundTag();
        xp.putInt("experience", 4321);
        var attachments = new CompoundTag();
        attachments.put("fancytrinkets:experience_crafter_data", xp);
        var legacy = new CompoundTag();
        legacy.putString("id", "fancytrinkets:experience_crafter");
        legacy.put("fabric:attachments", attachments);
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
        var copy = new ExperienceCrafterBE(pos, state);
        copy.loadWithComponents(net.minecraft.world.level.storage.TagValueInput.create(net.minecraft.util.ProblemReporter.DISCARDING, world.registryAccess(), restored.getUpdateTag(world.registryAccess())));
        helper.assertValueEqual(copy.getExperience(), 4321, "XP syncs to clients");
        world.setBlock(pos, state, 3);
        var placed = (ExperienceCrafterBE) world.getBlockEntity(pos);
        placed.setExperience(4321);
        var drop = net.minecraft.world.level.block.Block.getDrops(state, world, pos, placed, null, ItemStack.EMPTY).getFirst();
        var replacement = new ExperienceCrafterBE(pos, state);
        replacement.applyComponentsFromItemStack(drop);
        helper.assertValueEqual(replacement.getExperience(), 4321, "XP survives breaking and replacing crafter");
        helper.succeed();
    }

    @net.fabricmc.fabric.api.gametest.v1.GameTest(maxTicks = 100)
    public void allXpRecipes(GameTestHelper helper) {
        var world = helper.getLevel();
        var recipes = world.getServer().getRecipeManager().getRecipes().stream().map(holder -> holder.value()).filter(recipe -> recipe instanceof com.mcjty.fancytrinkets.modules.xpcrafter.recipe.XpRecipe).map(recipe -> (com.mcjty.fancytrinkets.modules.xpcrafter.recipe.XpRecipe) recipe).toList();
        helper.assertValueEqual(recipes.size(), 34, "All XP recipes loaded");
        var pos = helper.absolutePos(new BlockPos(0, 1, 0));
        for (var holder : recipes) {
            var recipe = holder;
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
            var items = (mcjty.lib.container.IItemHandlerModifiable) net.fabricmc.fabric.api.transfer.v1.item.ItemStorage.SIDED.find(world, pos, null);
            helper.assertTrue(items != null, "Crafter item capability");
            var input = new ArrayList<ItemStack>();
            var context = SlotDisplayContext.fromLevel(world);
            for (int y = 0; y < 5; y++) {
                for (int x = 0; x < 5; x++) {
                    var ingredient = x < recipe.getWidth() && y < recipe.getHeight()
                            ? recipe.getIngredients().get(y * recipe.getWidth() + x) : java.util.Optional.<net.minecraft.world.item.crafting.Ingredient>empty();
                    var material = ingredient.map(i -> i.display().resolveForStacks(context).getFirst().copyWithCount(2)).orElse(ItemStack.EMPTY);
                    input.add(material.copy());
                    items.setStackInSlot(ExperienceCrafterBE.SLOT_GRID + y * 5 + x, material);
                }
            }
            helper.assertTrue(recipe.matches(CraftingInput.of(5, 5, input), world), "Recipe matches: " + recipe.getId());
            helper.assertTrue(!crafter.getPreviewOutput().isEmpty(), "Craft preview: " + recipe.getId());
            var selected = world.recipeAccess().getRecipeFor(XpCrafterModule.XP_RECIPE_TYPE.get(),
                    CraftingInput.of(5, 5, input), world).orElseThrow().value();
            crafter.executeServerCommand("craft", null, TypedMap.EMPTY);
            var output = items.getStackInSlot(ExperienceCrafterBE.SLOT_OUTPUT);
            helper.assertTrue(!output.isEmpty(), "Crafted: " + recipe.getId());
            helper.assertValueEqual(output.get(TrinketsModule.TRINKET_DATA.get()).trinketId(),
                    selected.getResultItem().get(TrinketsModule.TRINKET_DATA.get()).trinketId(), "Crafted correct trinket");
            for (int i = 0; i < 25; i++) {
                helper.assertValueEqual(items.getStackInSlot(ExperienceCrafterBE.SLOT_GRID + i).getCount(),
                        input.get(i).isEmpty() ? 0 : 1, "Consumes one ingredient per occupied slot");
            }
            helper.assertTrue(!recipe.matches(CraftingInput.of(1, 1, List.of(new ItemStack(Items.DIRT))), world), "Wrong grid rejected");
        }
        helper.succeed();
    }

    @net.fabricmc.fabric.api.gametest.v1.GameTest(maxTicks = 100)
    public void trinketsEffects(GameTestHelper helper) {
        var world = helper.getLevel();
        var player = new net.minecraft.server.level.ServerPlayer(world.getServer(), world,
                new com.mojang.authlib.GameProfile(java.util.UUID.randomUUID(), "trinkets-test"), net.minecraft.server.level.ClientInformation.createDefault()) {
            @Override public GameType gameMode() { return GameType.SURVIVAL; }
            @Override public boolean isInvulnerableTo(net.minecraft.server.level.ServerLevel level, net.minecraft.world.damagesource.DamageSource source) { return false; }
            public void applyTestDamage(float amount) { super.actuallyHurt(world, world.damageSources().generic(), amount); }
        };
        // Minecraft's in-level mock always reports creative. Reuse its embedded connection with a survival player.
        player.connection = helper.makeMockServerPlayerInLevel().connection;
        var registry = world.registryAccess().lookupOrThrow(CustomRegistries.TRINKET_REGISTRY_KEY);
        var strength = TrinketItem.createTrinketStack(registry.getValue(id("strength_ring")), id("strength_ring"));
        var inventory = eu.pb4.trinkets.api.TrinketsApi.getAttachment(player);
        var slot = inventory.getSlotAccess("hand/ring", 0);
        helper.assertTrue(slot != null, "Player has ring equipment slot");
        slot.set(strength);
        helper.assertTrue(inventory.isEquipped(stack -> Registration.TRINKET_ITEM.find(stack, null) != null), "Equipped trinket found");
        var callback = eu.pb4.trinkets.api.callback.TrinketCallback.getCallback(strength);
        double original = player.getAttributeValue(Attributes.ATTACK_DAMAGE);
        callback.onEquip(strength, slot, player);
        callback.tick(strength, slot, player);
        com.mcjty.fancytrinkets.playerdata.PlayerEffects.getPlayerEffects(player).tick(player);
        helper.assertTrue(player.getAttributeValue(Attributes.ATTACK_DAMAGE) > original, "Equipped strength effect applied");
        callback.onUnequip(strength, slot, player);
        slot.set(ItemStack.EMPTY);
        helper.assertValueEqual(player.getAttributeValue(Attributes.ATTACK_DAMAGE), original, "Strength removed on unequip");
        var flight = TrinketItem.createTrinketStack(registry.getValue(id("flight_star")), id("flight_star"));
        player.setAttached(Registration.PLAYER_EFFECTS, PlayerEffectData.DEFAULT.toggle("flight"));
        var flightCallback = eu.pb4.trinkets.api.callback.TrinketCallback.getCallback(flight);
        slot.set(flight);
        helper.assertTrue(!player.isCreative(), "Flight check uses survival mode");
        helper.assertTrue(slot.canApplyEffects(flight), "Flight equipment is active");
        var effectTypes = new java.util.ArrayList<String>();
        ((TrinketItem) flight.getItem()).forAllEffects(world, flight, (effect, index) -> effectTypes.add(effect.getClass().getName()));
        helper.assertTrue(effectTypes.stream().anyMatch(type -> type.endsWith("FlightEffect")), "Flight star has flight effect: " + effectTypes);
        flightCallback.tick(flight, slot, player);
        helper.assertTrue(player.getAbilities().mayfly, "Enabled flight effect applied");
        flightCallback.onUnequip(flight, slot, player);
        slot.set(ItemStack.EMPTY);
        helper.assertTrue(!player.getAbilities().mayfly, "Flight removed on unequip");
        player.setAttached(Registration.PLAYER_EFFECTS, PlayerEffectData.DEFAULT.registerDamageReduction("generic", .5f));
        player.getAbilities().invulnerable = false;
        player.setAbsorptionAmount(0);
        player.setHealth(20);
        player.applyTestDamage(4);
        helper.assertValueEqual(player.getHealth(), 18f, "Damage reduction applies after armor and magic");
        var nightVision = TrinketItem.createTrinketStack(registry.getValue(id("nightvision_ring")), id("nightvision_ring"));
        slot.set(nightVision);
        com.mcjty.fancytrinkets.keys.PacketSendKey.create(1).handle(player);
        helper.assertTrue(player.getAttachedOrCreate(Registration.PLAYER_EFFECTS).isToggleOn("night_vision"), "Hotkey activates equipped trinket toggle");
        eu.pb4.trinkets.api.callback.TrinketCallback.getCallback(nightVision).tick(nightVision, slot, player);
        com.mcjty.fancytrinkets.playerdata.PlayerEffects.getPlayerEffects(player).tick(player);
        helper.assertTrue(player.hasEffect(net.minecraft.world.effect.MobEffects.NIGHT_VISION), "Toggled night vision effect is applied");
        slot.set(ItemStack.EMPTY);

        helper.succeed();
    }
    @net.fabricmc.fabric.api.gametest.v1.GameTest(maxTicks = 100)
    public void lootDrops(GameTestHelper helper) {
        var world = helper.getLevel();
        var player = helper.makeMockPlayer(GameType.SURVIVAL);
        var entity = net.minecraft.world.entity.EntityTypes.WITHER.create(world, net.minecraft.world.entity.EntitySpawnReason.COMMAND);
        var table = world.getServer().reloadableRegistries().getLootTable(net.minecraft.world.entity.EntityTypes.WITHER.getDefaultLootTable().orElseThrow());
        var params = new net.minecraft.world.level.storage.loot.LootParams.Builder(world)
                .withParameter(net.minecraft.world.level.storage.loot.parameters.LootContextParams.THIS_ENTITY, entity)
                .withParameter(net.minecraft.world.level.storage.loot.parameters.LootContextParams.ORIGIN, net.minecraft.world.phys.Vec3.ZERO)
                .withParameter(net.minecraft.world.level.storage.loot.parameters.LootContextParams.DAMAGE_SOURCE, world.damageSources().generic())
                .withParameter(net.minecraft.world.level.storage.loot.parameters.LootContextParams.LAST_DAMAGE_PLAYER, player)
                .create(net.minecraft.world.level.storage.loot.parameters.LootContextParamSets.ENTITY);
        var drops = table.getRandomItems(params, 123L);
        helper.assertTrue(drops.stream().anyMatch(stack -> stack.is(com.mcjty.fancytrinkets.modules.loot.LootModule.WITHER_ESSENCE.get())), "Player-killed wither drops essence");
        var natural = new net.minecraft.world.level.storage.loot.LootParams.Builder(world)
                .withParameter(net.minecraft.world.level.storage.loot.parameters.LootContextParams.THIS_ENTITY, entity)
                .withParameter(net.minecraft.world.level.storage.loot.parameters.LootContextParams.ORIGIN, net.minecraft.world.phys.Vec3.ZERO)
                .withParameter(net.minecraft.world.level.storage.loot.parameters.LootContextParams.DAMAGE_SOURCE, world.damageSources().generic())
                .create(net.minecraft.world.level.storage.loot.parameters.LootContextParamSets.ENTITY);
        helper.assertTrue(table.getRandomItems(natural, 123L).stream().noneMatch(stack -> stack.is(com.mcjty.fancytrinkets.modules.loot.LootModule.WITHER_ESSENCE.get())), "Natural death does not drop essence");
        helper.succeed();
    }

}
