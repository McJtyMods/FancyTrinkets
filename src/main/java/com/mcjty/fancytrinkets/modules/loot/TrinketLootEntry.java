package com.mcjty.fancytrinkets.modules.loot;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mcjty.fancytrinkets.modules.effects.imp.TrinketLootEffect;
import com.mcjty.fancytrinkets.setup.Registration;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class TrinketLootEntry extends LootPoolSingletonContainer {

    private final Set<String> tags;

    public TrinketLootEntry(int weightIn, int qualityIn, LootItemCondition[] conditionsIn, LootItemFunction[] functionsIn, Set<String> tags) {
        super(weightIn, qualityIn, conditionsIn, functionsIn);
        this.tags = tags;
    }

    @Override
    protected void createItemStack(@Nonnull Consumer<ItemStack> stackConsumer, @Nonnull LootContext context) {
        Entity entity = context.getParam(LootContextParams.THIS_ENTITY);
        if (entity instanceof ServerPlayer player) {
            for (SlotResult slot : CuriosApi.getCuriosHelper().findCurios(player, stack -> stack.getCapability(Registration.TRINKET_ITEM_CAPABILITY).isPresent())) {
                ItemStack stack = slot.stack();
                // Check if the stack is a trinket
                stack.getCapability(Registration.TRINKET_ITEM_CAPABILITY).ifPresent(trinket -> {
                    Set<String> activeToggles = trinket.getActiveToggles();
                    // For all effects that are active and are a TrinketLootEffect
                    trinket.forAllEffects(player.level(), stack, (effect, idx) -> {
                        if (effect instanceof TrinketLootEffect trinketLootEffect) {
                            String toggle = effect.getToggle();
                            if (toggle == null || activeToggles.contains(toggle)) {
                                Set<String> trinketTags = trinketLootEffect.getTags();
                                // Test if any tag in 'tags' matches a tag in 'trinketTags'
                                if (!Collections.disjoint(tags, trinketTags)) {
                                    trinketLootEffect.generateLoot(stackConsumer, context.getRandom());
                                }
                            }
                        }
                    });
                });
            }
        }
    }

    @Override
    @Nonnull
    public LootPoolEntryType getType() {
        return LootModule.TRINKET_LOOT_ENTRY;
    }

    public static Builder<?> builder(Set<String> tags) {
        return simpleBuilder((weight, quality, conditions, functions) -> new TrinketLootEntry(weight, quality, conditions, functions, tags));
    }

    public static class Serializer extends LootPoolSingletonContainer.Serializer<TrinketLootEntry> {

        @Override
        public void serializeCustom(@Nonnull JsonObject object, @Nonnull TrinketLootEntry entry, @Nonnull JsonSerializationContext conditions) {
            super.serializeCustom(object, entry, conditions);
            JsonArray array = new JsonArray();
            entry.tags.forEach(array::add);
            object.add("tags", array);
         }

        @Override
        @Nonnull
        protected TrinketLootEntry deserialize(@Nonnull JsonObject object, @Nonnull JsonDeserializationContext context, int weight, int quality, @Nonnull LootItemCondition[] conditions, @Nonnull LootItemFunction[] functions) {
            Set<String> tags = new HashSet<>();
            JsonArray array = object.getAsJsonArray("tags");
            array.forEach(element -> tags.add(element.getAsString()));
            return new TrinketLootEntry(weight, quality, conditions, functions, tags);
        }
    }
}
