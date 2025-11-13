package com.mcjty.fancytrinkets.modules.xpcrafter.client;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.modules.xpcrafter.XpCrafterModule;
import com.mcjty.fancytrinkets.modules.xpcrafter.blocks.ExperienceCrafterBE;
import com.mcjty.fancytrinkets.setup.Config;
import mcjty.lib.container.GenericContainer;
import mcjty.lib.gui.GenericGuiContainer;
import mcjty.lib.gui.Window;
import mcjty.lib.gui.widgets.Button;
import mcjty.lib.gui.widgets.EnergyBar;
import mcjty.lib.gui.widgets.Panel;
import mcjty.lib.tileentity.GenericTileEntity;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

import javax.annotation.Nonnull;

import static mcjty.lib.gui.widgets.Widgets.button;
import static mcjty.lib.gui.widgets.Widgets.positional;

public class GuiExperienceCrafter extends GenericGuiContainer<ExperienceCrafterBE, GenericContainer> {
    public static final int CONTROLLER_WIDTH = 180;
    public static final int CONTROLLER_HEIGHT = 192;

    private static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath(FancyTrinkets.MODID, "textures/gui/experience_crafter.png");

    private EnergyBar xpbar;
    private Button craftButton;

    public GuiExperienceCrafter(GenericContainer container, Inventory inventory, Component title) {
        super(container, inventory, title, XpCrafterModule.EXPERIENCE_CRAFTER.get().getManualEntry());

        imageWidth = CONTROLLER_WIDTH;
        imageHeight = CONTROLLER_HEIGHT;
    }

    public static void register(RegisterMenuScreensEvent event) {
        event.register(XpCrafterModule.CONTAINER_EXPERIENCE_CRAFTER.get(), GuiExperienceCrafter::new);
    }

    @Override
    public void init() {
        super.init();

        Button takexpButton = button(105, 63, 65, 14, "Fill XP")
                .name("fillxp")
                .tooltips("Collect XP of the player");
        xpbar = new EnergyBar().hint(105, 81, 65, 14)
                .setSpacerColor(0xffccffcc)
                .setEnergyOnColor(0xff44dd99)
                .setEnergyOffColor(0xffddeedd)
                .setTextColor(0xff000000)
                .maxValue(Config.MAXEXPERIENCE.get())
                .horizontal();

        craftButton = button(105, 10, 65, 14, "Craft")
                .name("craft")
                .tooltips("Perform the craft");

        Panel toplevel = positional().background(BACKGROUND)
                .children(takexpButton, craftButton, xpbar);
        toplevel.bounds(leftPos, topPos, imageWidth, imageHeight);

        window = new Window(this, toplevel);

        ExperienceCrafterBE be = getBE();
        window.action("fillxp", be, ExperienceCrafterBE.CMD_FILLXP);
        window.action("craft", be, ExperienceCrafterBE.CMD_CRAFT);
    }


    @Override
    protected void renderBg(@Nonnull GuiGraphics graphics, float partialTicks, int x, int y) {
        drawWindow(graphics, partialTicks, x, y);
        ExperienceCrafterBE be = getBE();
        xpbar.value(be.getExperience());
        craftButton.enabled(!be.getPreviewOutput().isEmpty());
    }
}
