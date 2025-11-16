package com.mcjty.fancytrinkets.modules.xpcrafter.blocks;

import com.mcjty.fancytrinkets.api.ITrinketItem;
import com.mcjty.fancytrinkets.modules.trinkets.items.TrinketItem;
import com.mcjty.fancytrinkets.modules.xpcrafter.XpCrafterModule;
import com.mcjty.fancytrinkets.modules.xpcrafter.data.ExperienceCrafterData;
import com.mcjty.fancytrinkets.modules.xpcrafter.recipe.XpRecipe;
import com.mcjty.fancytrinkets.setup.Config;
import com.mcjty.fancytrinkets.setup.Registration;
import mcjty.lib.api.container.DefaultContainerProvider;
import mcjty.lib.bindings.GuiValue;
import mcjty.lib.bindings.Value;
import mcjty.lib.blockcommands.Command;
import mcjty.lib.blockcommands.ServerCommand;
import mcjty.lib.container.ContainerFactory;
import mcjty.lib.container.GenericContainer;
import mcjty.lib.container.GenericItemHandler;
import mcjty.lib.container.SlotDefinition;
import mcjty.lib.tileentity.Cap;
import mcjty.lib.tileentity.CapType;
import mcjty.lib.tileentity.GenericTileEntity;
import mcjty.lib.typed.Type;
import mcjty.lib.varia.Tools;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.TransientCraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.function.Function;

import static com.mcjty.fancytrinkets.modules.xpcrafter.recipe.XpRecipe.RECIPE_DIMENSION;
import static mcjty.lib.api.container.DefaultContainerProvider.container;

public class ExperienceCrafterBE extends GenericTileEntity {

    public static final int SLOT_OUTPUT = 0;
    public static final int SLOT_PREVIEW = 1;
    public static final int SLOT_GRID = 2;

    public static final Lazy<ContainerFactory> CONTAINER_FACTORY = Lazy.of(() -> new ContainerFactory(RECIPE_DIMENSION*RECIPE_DIMENSION+2)
            .slot(SlotDefinition.generic().out(), SLOT_OUTPUT, 151, 27)
            .slot(SlotDefinition.ghostOut(), SLOT_PREVIEW, 115, 27)
            .box(SlotDefinition.generic().in(), SLOT_GRID, 10, 11, RECIPE_DIMENSION, RECIPE_DIMENSION)
            .playerSlots(10, 110));

    @GuiValue
    public static final Value<?, Integer> VALUE_EXPERIENCE = Value.<ExperienceCrafterBE, Integer>create("experience", Type.INTEGER, ExperienceCrafterBE::getExperience, ExperienceCrafterBE::setExperience);

    private final GenericItemHandler items = GenericItemHandler.create(this, CONTAINER_FACTORY)
            .onUpdate(this::onUpdate)
            .build();
    @Cap(type = CapType.ITEMS_AUTOMATION)
    private static final Function<ExperienceCrafterBE, IItemHandler> ITEM_CAP = tile -> tile.items;

    @Cap(type = CapType.CONTAINER)
    private static final Function<ExperienceCrafterBE, MenuProvider> screenHandler = tile -> new DefaultContainerProvider<GenericContainer>("Experience Crafter")
            .containerSupplier(container(XpCrafterModule.CONTAINER_EXPERIENCE_CRAFTER, CONTAINER_FACTORY, tile))
            .itemHandler(() -> tile.items)
            .setupSync(tile);

    @ServerCommand
    public static final Command<?> CMD_FILLXP = Command.<ExperienceCrafterBE>create("fillxp", (te, player, params) -> te.fillExperience((ServerPlayer) player));

    @ServerCommand
    public static final Command<?> CMD_CRAFT = Command.<ExperienceCrafterBE>create("craft", (te, player, params) -> te.craft());

    private final CraftingContainer inv = new TransientCraftingContainer(new AbstractContainerMenu(null, -1) {
        @Override
        public boolean stillValid(@Nonnull Player playerIn) {
            return false;
        }

        @Override
        public ItemStack quickMoveStack(Player player, int slot) {
            return ItemStack.EMPTY;
        }
    }, RECIPE_DIMENSION, RECIPE_DIMENSION);

    public ExperienceCrafterBE(BlockPos pos, BlockState state) {
        super(XpCrafterModule.EXPERIENCE_CRAFTER.be().get(), pos, state);
    }

    private void craft() {
        Optional<RecipeHolder<XpRecipe>> result = findRecipe();
        result.ifPresent(recipe -> {
            // @todo 1.21 is this right?
            CraftingInput input = CraftingInput.of(3, 3, inv.getItems());
            ItemStack stack = recipe.value().assemble(input, Tools.getRegistryAccess(level));
            ItemStack outputSlot = items.getStackInSlot(SLOT_OUTPUT);
            if (outputSlot.isEmpty()) {
                stack = stack.copy();
                ItemStack finalStack = stack;
                ITrinketItem trinket = stack.getCapability(Registration.TRINKET_ITEM_CAPABILITY);
                if (trinket != null) {
                    addBonusEffects(trinket, finalStack);
                }
                items.setStackInSlot(SLOT_OUTPUT, stack);
                for (int i = 0; i < RECIPE_DIMENSION * RECIPE_DIMENSION; i++) {
                    ItemStack in = items.getStackInSlot(SLOT_GRID + i);
                    if (!in.isEmpty()) {
                        in.setCount(in.getCount() - recipe.value().getIngredients().get(i).getItems()[0].getCount());
                        items.setStackInSlot(SLOT_GRID + i, in);
                    }
                }
            }
        });
    }

    private void addBonusEffects(ITrinketItem trinket, ItemStack stack) {
        float targetQuality = 100.0f * (getExperience() + Config.EXPERIENCE_OFFSET.get()) / (float) (Config.MAXEXPERIENCE.get() + Config.EXPERIENCE_OFFSET.get());
        if (TrinketItem.addBonusEffects(level, trinket, stack, targetQuality)) {
            setData(XpCrafterModule.EXPERIENCE_CRAFTER_DATA, ExperienceCrafterData.DEFAULT);
            setChanged();
        }
    }

    private void onUpdate(int slot, ItemStack stack) {
        if (slot >= SLOT_GRID) {
            updatePreview();
        }
    }

    private void updatePreview() {
        Optional<RecipeHolder<XpRecipe>> result = findRecipe();
        ItemStack output = result.map(p -> p.value().getResultItem()).orElse(ItemStack.EMPTY);
        items.setStackInSlot(SLOT_PREVIEW, output);
        setChanged();
    }

    @NotNull
    private Optional<RecipeHolder<XpRecipe>> findRecipe() {
        for (int i = SLOT_GRID; i < SLOT_GRID + RECIPE_DIMENSION * RECIPE_DIMENSION; i++) {
            inv.setItem(i - SLOT_GRID, items.getStackInSlot(i));
        }
        // @todo 1.21 is this correct?
        CraftingInput input = CraftingInput.of(RECIPE_DIMENSION, RECIPE_DIMENSION, inv.getItems());
        return level.getRecipeManager().getRecipeFor(XpCrafterModule.XP_RECIPE_TYPE.get(), input, level);
    }

    private void fillExperience(ServerPlayer player) {
        int level = player.experienceLevel;
        int maxXp = (int) (player.experienceProgress * player.getXpNeededForNextLevel() + getTotalXpForLevel(level));
        int toExtract = Math.min(maxXp, Config.MAXEXPERIENCE.get());
        player.giveExperiencePoints(-toExtract);
        setExperience(getExperience() + toExtract);
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
        ExperienceCrafterData data = getData(XpCrafterModule.EXPERIENCE_CRAFTER_DATA);
        return data.experience();
    }

    public void setExperience(int exp) {
        setData(XpCrafterModule.EXPERIENCE_CRAFTER_DATA, new ExperienceCrafterData(exp));
    }

    public ItemStack getPreviewOutput() {
        return items.getStackInSlot(SLOT_PREVIEW);
    }
}
