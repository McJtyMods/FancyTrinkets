package com.mcjty.fancytrinkets;

import com.mcjty.fancytrinkets.compat.FancyJeiPlugin;
import com.mcjty.fancytrinkets.datapack.CustomRegistries;
import com.mcjty.fancytrinkets.keys.PacketSendKey;
import com.mcjty.fancytrinkets.modules.trinkets.TrinketsModule;
import com.mcjty.fancytrinkets.modules.trinkets.items.TrinketItem;
import com.mcjty.fancytrinkets.modules.xpcrafter.XpCrafterModule;
import com.mcjty.fancytrinkets.modules.xpcrafter.blocks.ExperienceCrafterBE;
import com.mcjty.fancytrinkets.modules.xpcrafter.client.GuiExperienceCrafter;
import com.mcjty.fancytrinkets.setup.ClientEventHandlers;
import com.mcjty.fancytrinkets.setup.Messages;
import com.mcjty.fancytrinkets.setup.Registration;
import mcjty.lib.gui.widgets.Button;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.runtime.IJeiRuntime;
import mcjty.lib.varia.InventoryTools;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemUtil;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/** Runs only in the disposable fancytrinkets-port-smoke world with -PclientSmoke. */
@JeiPlugin
@EventBusSubscriber(modid = FancyTrinkets.MODID, value = Dist.CLIENT)
public final class ClientPortSmoke implements IModPlugin {
    private static int ticks;
    private static IJeiRuntime jei;
    private static CompletableFuture<Void> pending = CompletableFuture.completedFuture(null);
    private static final BlockPos CRAFTER = new BlockPos(0, 4, 0);

    @Override public Identifier getPluginUid() { return Identifier.parse("fancytrinkets:port_smoke"); }
    @Override public void onRuntimeAvailable(IJeiRuntime runtime) { jei = runtime; }

    @SubscribeEvent
    public static void tick(ClientTickEvent.Post event) {
        if (!Boolean.getBoolean("fancytrinkets.clientSmoke")) return;
        var mc = Minecraft.getInstance();
        var server = mc.getSingleplayerServer();
        if (mc.level == null || mc.player == null || server == null) return;
        if (ticks == 0 && mc.gui.screen() != null) return;
        if (!pending.isDone()) return;
        pending.join();
        int tick = ticks++;
        if (tick == 0) {
            pending = CompletableFuture.runAsync(() -> {
                for (int x = -1; x <= 1; x++) for (int z = -1; z <= 1; z++) server.overworld().getChunk(x, z);
                server.getCommands().performPrefixedCommand(server.createCommandSourceStack(), "fill -3 3 -3 9 3 9 minecraft:stone");
                server.overworld().setBlock(CRAFTER, XpCrafterModule.EXPERIENCE_CRAFTER.block().get().defaultBlockState(), 3);
                var items = server.overworld().getCapability(Capabilities.Item.BLOCK, CRAFTER, null);
                String[] pattern = {" ggg ", "g   g", "g   g", "g   g", " ggg "};
                for (int y = 0; y < 5; y++) for (int x = 0; x < 5; x++) {
                    var stack = pattern[y].charAt(x) == 'g' ? new ItemStack(Items.GOLD_INGOT, 2) : ItemStack.EMPTY;
                    InventoryTools.modifier(items).set(ExperienceCrafterBE.SLOT_GRID + y * 5 + x,
                            ItemResource.of(stack), stack.getCount());
                }
                for (var player : server.getPlayerList().getPlayers()) {
                    player.setGameMode(GameType.SURVIVAL);
                    player.giveExperienceLevels(30);
                    player.teleportTo(server.overworld(), 1, 5, 2, java.util.Set.of(), 180, 0, false);
                    var registry = server.overworld().registryAccess().lookupOrThrow(CustomRegistries.TRINKET_REGISTRY_KEY);
                    var id = Identifier.parse("fancytrinkets:nightvision_ring");
                    CuriosApi.getCuriosInventory(player).orElseThrow().setEquippedCurio("ring", 0,
                            TrinketItem.createTrinketStack(registry.getValue(id), id));
                }
            }, server);
        }
        if (tick == 80) {
            for (var entry : Registration.ITEMS.getRegister().getEntries()) {
                var model = new net.minecraft.client.renderer.item.ItemStackRenderState();
                mc.getItemModelResolver().updateForTopItem(model, new ItemStack(entry.get()),
                        net.minecraft.world.item.ItemDisplayContext.GUI, mc.level, mc.player, 0);
                if (model.isEmpty()) throw new AssertionError("Missing item model: " + entry.getId());
            }
            if (ClientEventHandlers.getXpRecipes().size() != 34) throw new AssertionError("XP recipes did not sync");
            if (jei == null || jei.getRecipeManager().createRecipeLookup(FancyJeiPlugin.XP_RECIPE_TYPE).get().count() != 34)
                throw new AssertionError("JEI did not register all XP recipes");
            pending = CompletableFuture.runAsync(() -> {
                var player = server.getPlayerList().getPlayers().getFirst();
                var state = server.overworld().getBlockState(CRAFTER);
                state.useWithoutItem(server.overworld(), player,
                        new BlockHitResult(Vec3.atCenterOf(CRAFTER), Direction.UP, CRAFTER, false));
            }, server);
        }
        if (tick == 120) {
            if (!(mc.gui.screen() instanceof GuiExperienceCrafter gui)) throw new AssertionError("Crafter editor did not open");
            screenshot(mc, "fancytrinkets-crafter-preview.png");
            click(gui, "fillxp");
        }
        if (tick == 160) {
            var gui = (GuiExperienceCrafter) mc.gui.screen();
            if (((ExperienceCrafterBE) gui.getBE()).getExperience() <= 0) throw new AssertionError("Fill XP did not sync");
            click(gui, "craft");
        }
        if (tick == 200) {
            screenshot(mc, "fancytrinkets-crafter-crafted.png");
            pending = CompletableFuture.runAsync(() -> {
                var items = server.overworld().getCapability(Capabilities.Item.BLOCK, CRAFTER, null);
                var output = ItemUtil.getStack(items, ExperienceCrafterBE.SLOT_OUTPUT);
                if (output.isEmpty() || !Identifier.parse("fancytrinkets:base_gold_ring").equals(output.get(TrinketsModule.TRINKET_DATA).trinketId()))
                    throw new AssertionError("GUI craft did not reach server");
            }, server);
            mc.player.closeContainer();
        }
        if (tick == 220) Messages.sendToServer(PacketSendKey.create(1));
        if (tick == 260) {
            if (!TrinketItem.toggles.contains("night_vision")) throw new AssertionError("Toggle packet did not sync to client");
            pending = CompletableFuture.runAsync(() -> {
                var player = server.getPlayerList().getPlayers().getFirst();
                if (!player.hasEffect(net.minecraft.world.effect.MobEffects.NIGHT_VISION)) throw new AssertionError("Curios hotkey did not enable night vision");
            }, server);
            jei.getRecipesGui().showTypes(List.of(FancyJeiPlugin.XP_RECIPE_TYPE));
        }
        if (tick == 300) screenshot(mc, "fancytrinkets-jei.png");
        if (tick == 320) {
            System.out.println("FANCYTRINKETS CLIENT SMOKE PASSED: item models, 34 synced JEI recipes, crafter GUI, XP transfer, crafting, and Curios hotkey sync");
            mc.stop();
        }
    }

    private static void click(GuiExperienceCrafter gui, String name) {
        Button button = (Button) gui.getWindow().findChild(name);
        button.mouseClick(0, 0, com.mojang.blaze3d.platform.InputConstants.MOUSE_BUTTON_LEFT);
        button.mouseRelease(0, 0, com.mojang.blaze3d.platform.InputConstants.MOUSE_BUTTON_LEFT);
    }

    private static void screenshot(Minecraft mc, String name) {
        net.minecraft.client.Screenshot.grab(mc.gameDirectory, name, mc.gameRenderer.mainRenderTarget(), 1,
                message -> System.out.println(message.getString()));
    }
}
