package com.mcjty.fancytrinkets.modules.xpcrafter.client;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.modules.xpcrafter.XpCrafterModule;
import com.mcjty.fancytrinkets.modules.xpcrafter.blocks.ExperienceCrafterBE;
import com.mcjty.fancytrinkets.setup.Config;
import com.mcjty.fancytrinkets.setup.Messages;
import com.mojang.blaze3d.vertex.PoseStack;
import mcjty.lib.container.GenericContainer;
import mcjty.lib.gui.GenericGuiContainer;
import mcjty.lib.gui.Window;
import mcjty.lib.gui.widgets.Button;
import mcjty.lib.gui.widgets.EnergyBar;
import mcjty.lib.gui.widgets.Panel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import javax.annotation.Nonnull;

import static mcjty.lib.gui.widgets.Widgets.button;
import static mcjty.lib.gui.widgets.Widgets.positional;

public class GuiExperienceCrafter extends GenericGuiContainer<ExperienceCrafterBE, GenericContainer> {
    public static final int CONTROLLER_WIDTH = 180;
    public static final int CONTROLLER_HEIGHT = 192;

    private static final ResourceLocation BACKGROUND = new ResourceLocation(FancyTrinkets.MODID, "textures/gui/experience_crafter.png");

    private EnergyBar xpbar;

    public GuiExperienceCrafter(ExperienceCrafterBE screenControllerTileEntity, GenericContainer container, Inventory inventory) {
        super(screenControllerTileEntity, container, inventory, XpCrafterModule.EXPERIENCE_CRAFTER.get().getManualEntry());

        imageWidth = CONTROLLER_WIDTH;
        imageHeight = CONTROLLER_HEIGHT;
    }

    public static void register() {
        register(XpCrafterModule.CONTAINER_EXPERIENCE_CRAFTER.get(), GuiExperienceCrafter::new);
    }

    @Override
    public void init() {
        super.init();

        Button takexpButton = button(105, 63, 65, 14, "Fill XP")
                .name("fillxp")
                .tooltips("Collect XP of the player");
        xpbar = new EnergyBar().hint(105, 81, 65, 14)
                .setEnergyOnColor(0x33ff88)
                .maxValue(Config.MAXEXPERIENCE.get())
                .horizontal();

        Panel toplevel = positional().background(BACKGROUND)
                .children(takexpButton, xpbar);
        toplevel.bounds(leftPos, topPos, imageWidth, imageHeight);

        window = new Window(this, toplevel);

        window.action(Messages.INSTANCE, "fillxp", tileEntity, ExperienceCrafterBE.CMD_FILLXP);
    }


    @Override
    protected void renderBg(@Nonnull PoseStack matrixStack, float partialTicks, int x, int y) {
        drawWindow(matrixStack);
        xpbar.value(tileEntity.getExperience());
    }
}