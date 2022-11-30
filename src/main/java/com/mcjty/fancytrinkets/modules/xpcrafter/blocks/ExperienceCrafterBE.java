package com.mcjty.fancytrinkets.modules.xpcrafter.blocks;

import com.mcjty.fancytrinkets.datapack.BonusTable;
import com.mcjty.fancytrinkets.datapack.CustomRegistries;
import com.mcjty.fancytrinkets.datapack.TrinketDescription;
import com.mcjty.fancytrinkets.modules.trinkets.TrinketsModule;
import com.mcjty.fancytrinkets.modules.trinkets.items.TrinketItem;
import com.mcjty.fancytrinkets.modules.xpcrafter.XpCrafterModule;
import com.mcjty.fancytrinkets.modules.xpcrafter.recipe.XpRecipe;
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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private int experience = 0;

    @Cap(type = CapType.ITEMS_AUTOMATION)
    private final GenericItemHandler items = GenericItemHandler.create(this, CONTAINER_FACTORY)
            .onUpdate(this::onUpdate)
            .build();

    @Cap(type = CapType.CONTAINER)
    private final LazyOptional<MenuProvider> screenHandler = LazyOptional.of(() -> new DefaultContainerProvider<GenericContainer>("Experience Crafter")
            .containerSupplier(container(XpCrafterModule.CONTAINER_EXPERIENCE_CRAFTER, CONTAINER_FACTORY,this))
            .itemHandler(() -> items)
            .setupSync(this));

    @ServerCommand
    public static final Command<?> CMD_FILLXP = Command.<ExperienceCrafterBE>create("fillxp", (te, player, params) -> te.fillExperience((ServerPlayer) player));

    @ServerCommand
    public static final Command<?> CMD_CRAFT = Command.<ExperienceCrafterBE>create("craft", (te, player, params) -> te.craft());

    private final CraftingContainer inv = new CraftingContainer(new AbstractContainerMenu(null, -1) {
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
        super(XpCrafterModule.TYPE_EXPERIENCE_CRAFTER.get(), pos, state);
    }

    private void craft() {
        Optional<XpRecipe> result = findRecipe();
        result.ifPresent(recipe -> {
            ItemStack stack = recipe.assemble(inv);
            ItemStack outputSlot = items.getStackInSlot(SLOT_OUTPUT);
            if (outputSlot.isEmpty()) {
                stack = stack.copy();
                if (stack.getItem() instanceof TrinketItem) {
                    addBonusEffects(stack);
                }
                items.setStackInSlot(SLOT_OUTPUT, stack);
                for (int i = 0; i < RECIPE_DIMENSION * RECIPE_DIMENSION; i++) {
                    ItemStack in = items.getStackInSlot(SLOT_GRID + i);
                    if (!in.isEmpty()) {
                        in.setCount(in.getCount() - recipe.getIngredients().get(i).getItems()[0].getCount());
                        items.setStackInSlot(SLOT_GRID + i, in);
                    }
                }
            }
        });
    }

    private void addBonusEffects(ItemStack stack) {
        ResourceLocation id = TrinketItem.getTrinketId(stack);
        TrinketDescription description = level.registryAccess().registryOrThrow(CustomRegistries.TRINKET_REGISTRY_KEY).get(id);
        if (description != null) {
            ResourceLocation bonusTableId = description.bonusTableId();
            if (bonusTableId != null) {
                BonusTable bonusTable = level.registryAccess().registryOrThrow(CustomRegistries.BONUS_TABLE_REGISTRY_KEY).get(bonusTableId);
                if (bonusTable != null) {
                    List<ResourceLocation> effects = new ArrayList<>();
                    List<BonusTable.EffectRef> list = bonusTable.effects();
                    // @todo temporary, use other random
                    BonusTable.EffectRef ref = list.get(level.random.nextInt(list.size()));
                    effects.add(ref.effect());
                    TrinketItem.addEffects(stack, effects);
                }
            }
        }
    }

    private void onUpdate(int slot, ItemStack stack) {
        if (slot >= SLOT_GRID) {
            updatePreview();
        }
    }

    private void updatePreview() {
        Optional<XpRecipe> result = findRecipe();
        ItemStack output = result.map(XpRecipe::getResultItem).orElse(ItemStack.EMPTY);
        items.setStackInSlot(SLOT_PREVIEW, output);
        setChanged();
    }

    @NotNull
    private Optional<XpRecipe> findRecipe() {
        for (int i = SLOT_GRID; i < SLOT_GRID + RECIPE_DIMENSION * RECIPE_DIMENSION; i++) {
            inv.setItem(i - SLOT_GRID, items.getStackInSlot(i));
        }
        Optional<XpRecipe> result = level.getRecipeManager().getRecipeFor(XpCrafterModule.XP_RECIPE_TYPE.get(), inv, level);
        return result;
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

    public ItemStack getPreviewOutput() {
        return items.getStackInSlot(SLOT_PREVIEW);
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
        CompoundTag infoTag = getOrCreateInfo(tagCompound);
        infoTag.putInt("experience", experience);
    }
}
