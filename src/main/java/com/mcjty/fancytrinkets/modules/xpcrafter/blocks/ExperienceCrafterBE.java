package com.mcjty.fancytrinkets.modules.xpcrafter.blocks;

import com.mcjty.fancytrinkets.modules.xpcrafter.XpCrafterModule;
import com.mcjty.fancytrinkets.setup.Config;
import mcjty.lib.api.container.DefaultContainerProvider;
import mcjty.lib.bindings.GuiValue;
import mcjty.lib.blockcommands.Command;
import mcjty.lib.blockcommands.ServerCommand;
import mcjty.lib.container.ContainerFactory;
import mcjty.lib.container.GenericContainer;
import mcjty.lib.container.GenericItemHandler;
import mcjty.lib.container.SlotDefinition;
import mcjty.lib.tileentity.Cap;
import mcjty.lib.tileentity.CapType;
import mcjty.lib.tileentity.GenericTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.common.util.LazyOptional;

import static com.mcjty.fancytrinkets.modules.xpcrafter.recipe.XpRecipe.RECIPE_DIMENSION;
import static mcjty.lib.api.container.DefaultContainerProvider.container;

public class ExperienceCrafterBE extends GenericTileEntity {

    public static final int SLOT_OUTPUT = 0;
    public static final int SLOT_PREVIEW = 1;
    public static final int SLOT_GRID = 2;

    public static final Lazy<ContainerFactory> CONTAINER_FACTORY = Lazy.of(() -> new ContainerFactory(5*5+1)
            .slot(SlotDefinition.generic().out(), SLOT_OUTPUT, 115, 27)
            .slot(SlotDefinition.generic(), SLOT_PREVIEW, 151, 27)
            .box(SlotDefinition.generic().in(), SLOT_GRID, 10, 11, RECIPE_DIMENSION, RECIPE_DIMENSION)
            .playerSlots(10, 110));

    @GuiValue
    private int experience = 0;

    @Cap(type = CapType.ITEMS_AUTOMATION)
    private final GenericItemHandler items = GenericItemHandler.create(this, CONTAINER_FACTORY).build();

    @Cap(type = CapType.CONTAINER)
    private final LazyOptional<MenuProvider> screenHandler = LazyOptional.of(() -> new DefaultContainerProvider<GenericContainer>("Experience Crafter")
            .containerSupplier(container(XpCrafterModule.CONTAINER_EXPERIENCE_CRAFTER, CONTAINER_FACTORY,this))
            .itemHandler(() -> items)
            .setupSync(this));

    @ServerCommand
    public static final Command<?> CMD_FILLXP = Command.<ExperienceCrafterBE>create("fillxp", (te, player, params) -> te.fillExperience((ServerPlayer) player));

    public ExperienceCrafterBE(BlockPos pos, BlockState state) {
        super(XpCrafterModule.TYPE_EXPERIENCE_CRAFTER.get(), pos, state);
    }

    private void fillExperience(ServerPlayer player) {
        int level = player.experienceLevel;
        int maxXp = (int) (player.experienceProgress * player.getXpNeededForNextLevel() + getTotalXpForLevel(level));
        int toExtract = Math.min(maxXp, Config.MAXEXPERIENCE.get());
        player.giveExperiencePoints(-toExtract);
        experience += toExtract;
        setChanged();
    }

    public static int getTotalXpForLevel(int level) {
        if (level < 17) {
            return level * level + 6 * level;
        }
        if (level < 32) {
            return Mth.floor(2.5 * level * level - 40.5 * level + 360);
        }
        return Mth.floor(4.5 * level * level - 162.5 * level + 2220);
    }

    public int getExperience() {
        return experience;
    }

    @Override
    protected void loadInfo(CompoundTag tagCompound) {
        super.loadInfo(tagCompound);
        if (tagCompound.contains("Info")) {
            CompoundTag info = tagCompound.getCompound("Info");
            experience = info.getInt("experience");
        }
    }

    @Override
    protected void saveInfo(CompoundTag tagCompound) {
        super.saveInfo(tagCompound);
        getOrCreateInfo(tagCompound).putInt("experience", experience);
    }
}
