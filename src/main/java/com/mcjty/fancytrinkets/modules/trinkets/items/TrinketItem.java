package com.mcjty.fancytrinkets.modules.trinkets.items;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.api.ITrinketItem;
import com.mcjty.fancytrinkets.datapack.BonusTable;
import com.mcjty.fancytrinkets.datapack.CustomRegistries;
import com.mcjty.fancytrinkets.datapack.EffectDescription;
import com.mcjty.fancytrinkets.datapack.TrinketDescription;
import com.mcjty.fancytrinkets.keys.KeyBindings;
import com.mcjty.fancytrinkets.modules.effects.EffectInstance;
import com.mcjty.fancytrinkets.modules.effects.IEffect;
import com.mcjty.fancytrinkets.modules.trinkets.TrinketInstance;
import com.mcjty.fancytrinkets.modules.trinkets.TrinketsModule;
import com.mcjty.fancytrinkets.modules.trinkets.data.TrinketData;
import com.mcjty.fancytrinkets.setup.Config;
import com.mcjty.fancytrinkets.setup.Registration;
import mcjty.lib.items.BaseItem;
import mcjty.lib.tooltips.ITooltipSettings;
import mcjty.lib.varia.ComponentFactory;
import mcjty.lib.varia.SafeClientTools;
import mcjty.lib.varia.Tools;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TrinketItem extends BaseItem implements ITooltipSettings, ITrinketItem {

    public static final String MESSAGE_FANCYTRINKETS_SHIFTMESSAGE = "message.fancytrinkets.shiftmessage";
    public static final String MESSAGE_FANCYTRINKETS_BONUS = "message.fancytrinkets.bonus";
    private static final Random random = new Random();

    private final Map<ResourceLocation, TrinketInstance> trinkets = new HashMap<>();

    // Synced from server, all active toggles
    public static Set<String> toggles = new HashSet<>();

    public TrinketItem() {
        super(FancyTrinkets.setup.defaultProperties()
                .stacksTo(1));
    }

    public static boolean addBonusEffects(Level level, ITrinketItem trinket, ItemStack stack, float targetQuality) {
        ResourceLocation id = trinket.getTrinketId(stack);
        TrinketDescription description = Tools.getRegistryAccess(level).registryOrThrow(CustomRegistries.TRINKET_REGISTRY_KEY).get(id);
        if (description != null) {
            ResourceLocation bonusTableId = description.bonusTableId();
            if (bonusTableId != null) {
                BonusTable bonusTable = Tools.getRegistryAccess(level).registryOrThrow(CustomRegistries.BONUS_TABLE_REGISTRY_KEY).get(bonusTableId);
                if (bonusTable != null) {
                    List<ResourceLocation> effects = new ArrayList<>();
                    // Find a good set of effects for the desired quality
                    List<BonusTable.EffectRef> list = Collections.emptyList();
                    float maxDiff = 10.0f;
                    while (list.size() < 9) {
                        list = findSuitableEffects(bonusTable.effects(), targetQuality, maxDiff);
                        maxDiff += 5;
                    }

                    if (random.nextDouble(100.0) <= Config.CHANCE_BONUS_EFFECT1.get()) {
                        addEffect(targetQuality, effects, list);
                        if (random.nextDouble(100.0) <= Config.CHANCE_BONUS_EFFECT2.get()) {
                            addEffect(targetQuality, effects, list);
                            if (random.nextDouble(100.0) <= Config.CHANCE_BONUS_EFFECT3.get()) {
                                addEffect(targetQuality, effects, list);
                                if (random.nextDouble(100.0) <= Config.CHANCE_BONUS_EFFECT4.get()) {
                                    addEffect(targetQuality, effects, list);
                                }
                            }
                        }
                    }
                    trinket.addEffects(stack, effects);
                    return true;
                }
            }
        }
        return false;
    }

    private static List<BonusTable.EffectRef> findSuitableEffects(List<BonusTable.EffectRef> list, float desiredQuality, float maxDiff) {
        return list.stream().filter(ref -> Math.abs(ref.quality() - desiredQuality) <= maxDiff).collect(Collectors.toList());
    }

    private static void addEffect(float targetQuality, List<ResourceLocation> effects, List<BonusTable.EffectRef> list) {
        int bestIndex = -1;
        float bestDiff = Float.MAX_VALUE;
        for (int i = 0 ; i < 5 ; i++) {
            BonusTable.EffectRef ref = list.get(random.nextInt(list.size()));
            float diff = Math.abs(ref.quality() - targetQuality);
            if (diff < bestDiff) {
                bestDiff = diff;
                bestIndex = i;
            }
        }
        ResourceLocation id = list.remove(bestIndex).effect();
        effects.add(id);
    }

    @Override
    public List<ItemStack> getItemsForTab() {
        List<ItemStack> list = new ArrayList<>();
        for (TrinketInstance trinket : getTrinkets(SafeClientTools.getClientWorld()).values()) {
            ItemStack stack = new ItemStack(this);
            stack.set(TrinketsModule.TRINKET_DATA, new TrinketData(trinket.id(), Collections.emptyList()));
            list.add(stack);
        }
        return list;
    }

    @Override
    public void addEffects(ItemStack stack, List<ResourceLocation> effects) {
        TrinketData data = stack.getOrDefault(TrinketsModule.TRINKET_DATA, TrinketData.DEFAULT);
        data = data.withEffects(effects);
        stack.set(TrinketsModule.TRINKET_DATA, data);
    }

    public static Stream<ResourceLocation> getEffects(ItemStack stack) {
        TrinketData data = stack.getOrDefault(TrinketsModule.TRINKET_DATA, TrinketData.DEFAULT);
        return data.effects().stream();
    }

    public static ItemStack createTrinketStack(Level level, TrinketDescription description, ResourceLocation id, float quality) {
        ItemStack stack = createTrinketStack(description, id);
        ITrinketItem trinket = stack.getCapability(Registration.TRINKET_ITEM_CAPABILITY);
        if (trinket != null) {
            addBonusEffects(level, trinket, stack, quality);
        }
        return stack;
    }

    @NotNull
    public static ItemStack createTrinketStack(TrinketDescription description, ResourceLocation id) {
        ResourceLocation itemId = description.item();
        Item item = Tools.getItem(itemId);
        if (item == null) {
            throw new RuntimeException("Cannot find item for trinket '" + id.toString() + "'!");
        }
        ItemStack result = new ItemStack(item);
        result.set(TrinketsModule.TRINKET_DATA, new TrinketData(id, Collections.emptyList()));
        return result;
    }

    @Override
    public ResourceLocation getTrinketId(ItemStack stack) {
        TrinketData data = stack.getOrDefault(TrinketsModule.TRINKET_DATA, TrinketData.DEFAULT);
        return data.trinketId();
    }

    @Override
    public void registerTrinketInstance(Level level, ResourceLocation id, TrinketDescription description) {
        trinkets.put(id, description.build(id, level));
    }

    // Get and create trinket map
    private Map<ResourceLocation, TrinketInstance> getTrinkets(Level level) {
        if (trinkets.isEmpty()) {
            TrinketsModule.registerTrinkets(level);
        }
        return trinkets;
    }

    @Override
    public void forAllEffects(Level level, ItemStack stack, BiConsumer<IEffect, Integer> consumer) {
        ResourceLocation trinketId = getTrinketId(stack);
        if (trinketId != null) {
            AtomicInteger idx = new AtomicInteger(0);
            TrinketInstance instance = getTrinkets(level).get(trinketId);
            if (instance != null) {
                for (EffectInstance effect : instance.effects()) {
                    consumer.accept(effect.effect(), idx.incrementAndGet());
                }
            }
            Registry<EffectDescription> registry = Tools.getRegistryAccess(level).registryOrThrow(CustomRegistries.EFFECT_REGISTRY_KEY);
            getEffects(stack).forEach(effect -> {
                EffectDescription description = registry.get(effect);
                if (description != null) {
                    consumer.accept(description.effect(), idx.incrementAndGet());
                }
            });
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> list, TooltipFlag flags) {
        ResourceLocation trinketId = getTrinketId(stack);
        if (trinketId != null) {
            TrinketInstance instance = getTrinkets(context.level()).get(trinketId);
            if (instance != null) {
                MutableComponent name = ComponentFactory.translatable(instance.nameKey()).withStyle(ChatFormatting.AQUA);
                if (list.isEmpty()) {
                    list.add(name);
                } else {
                    list.set(0, name);
                }
                list.add(ComponentFactory.translatable(instance.descriptionKey()));
                for (EffectInstance effectInstance : instance.effects()) {
                    IEffect effect  = effectInstance.effect();
                    if (!effectInstance.hidden()) {
                        MutableComponent translatable = ComponentFactory.translatable("effectId." + effectInstance.id().getNamespace() + "." + effectInstance.id().getPath());
                        String toggle = effect.getToggle();
                        ChatFormatting color = ChatFormatting.BLUE;
                        ChatFormatting style = ChatFormatting.BLUE;
                        if (toggle != null) {
                            if (!toggles.contains(toggle)) {
                                color = ChatFormatting.GRAY;
                                style  = ChatFormatting.STRIKETHROUGH;
                            }
                        }

                        Integer hotkey = effect.getHotkey();
                        if (hotkey != null) {
                            Component key = KeyBindings.toggles[hotkey - 1].getKey().getDisplayName();
                            Component key2 = ComponentFactory.literal(" [Key ").withStyle(ChatFormatting.YELLOW).append(key).append("]");
                            list.add(ComponentFactory.literal("    ").append(translatable.withStyle(color).withStyle(style).append(key2)));
                        } else {
                            list.add(ComponentFactory.literal("    ").append(translatable.withStyle(color).withStyle(style)));
                        }
                    }
                }
                AtomicBoolean first = new AtomicBoolean(true);
                getEffects(stack).forEach(effect -> {
                    if (first.get()) {
                        list.add(ComponentFactory.translatable(MESSAGE_FANCYTRINKETS_BONUS).withStyle(ChatFormatting.AQUA));
                        first.set(false);
                    }
                    MutableComponent translatable = ComponentFactory.translatable("effectId." + effect.getNamespace() + "." + effect.getPath());
                    ChatFormatting color = ChatFormatting.GREEN;
                    EffectDescription description = Tools.getRegistryAccess(SafeClientTools.getClientWorld()).registryOrThrow(CustomRegistries.EFFECT_REGISTRY_KEY).get(effect);
                    if (description != null && description.harmful()) {
                        color = ChatFormatting.RED;
                    }
                    list.add(ComponentFactory.literal("    ").append(translatable).withStyle(color));
                });
            }
        }
    }

    @Nonnull
    public ICurio createCurio(ItemStack itemStack) {
        return new ICurio() {
            @Override
            public ItemStack getStack() {
                return itemStack;
            }

            @Override
            public void curioTick(SlotContext slotContext) {
                if (slotContext.entity() instanceof ServerPlayer player) {
                    String slotId = slotContext.identifier() + slotContext.index() + "_";
                    TrinketItem.this.forAllEffects(player.level(), itemStack, (effect, idx) -> effect.tick(itemStack, player, slotId + idx));
                }
            }

            @Override
            public void onEquip(SlotContext slotContext, ItemStack prevStack) {
                if (slotContext.entity() instanceof ServerPlayer player) {
                    String slotId = slotContext.identifier() + slotContext.index() + "_";
                    TrinketItem.this.forAllEffects(player.level(), itemStack, (effect, idx) -> effect.onEquip(itemStack, player, slotId + idx));
                }
            }

            @Override
            public void onUnequip(SlotContext slotContext, ItemStack newStack) {
                if (slotContext.entity() instanceof ServerPlayer player) {
                    String slotId = slotContext.identifier() + slotContext.index() + "_";
                    TrinketItem.this.forAllEffects(player.level(), itemStack, (effect, idx) -> effect.onUnequip(itemStack, player, slotId + idx));
                }
            }
        };
    }
}
