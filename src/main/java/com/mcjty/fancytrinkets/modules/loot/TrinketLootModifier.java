package com.mcjty.fancytrinkets.modules.loot;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mcjty.fancytrinkets.datapack.CustomRegistries;
import com.mcjty.fancytrinkets.datapack.TrinketDescription;
import com.mcjty.fancytrinkets.modules.trinkets.items.TrinketItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class TrinketLootModifier extends LootModifier {

    private final List<ResourceLocation> trinketIds;
    private final float chance;
    private final int min;
    private final int max;
    private final float lootingFactor;
    private final float minQuality;
    private final float maxQuality;

    public TrinketLootModifier(LootItemCondition[] conditionsIn, List<ResourceLocation> trinketIds, float chance, int min, int max, float looting,
                               float minQuality, float maxQuality) {
        super(conditionsIn);
        this.trinketIds = trinketIds;
        this.chance = chance;
        this.min = min;
        this.max = max;
        this.lootingFactor = looting;
        this.minQuality = minQuality;
        this.maxQuality = maxQuality;
    }

    @NotNull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        Random random = context.getRandom();
        ResourceLocation id;
        if (trinketIds.isEmpty()) {
            // Pick a totally random trinket
            List<ResourceLocation> keys = context.getLevel().registryAccess().registryOrThrow(CustomRegistries.TRINKET_REGISTRY_KEY).entrySet()
                    .stream().map(p -> p.getKey().location()).collect(Collectors.toList());
            if (keys.isEmpty()) {
                // Weird
                return generatedLoot;
            }
            id = keys.get(random.nextInt(keys.size()));
        } else {
            id = trinketIds.get(random.nextInt(trinketIds.size()));
        }

        TrinketDescription trinket = context.getLevel().registryAccess().registryOrThrow(CustomRegistries.TRINKET_REGISTRY_KEY).get(id);
        if (trinket == null) {
            return generatedLoot;
        }

        Item it = ForgeRegistries.ITEMS.getValue(trinket.item());
        if (it == null) {
            return generatedLoot;
        }
        if (random.nextFloat() < chance + context.getLootingModifier() * lootingFactor) {
            int cnt;
            if (max <= min) {
                cnt = min;
            } else {
                cnt = random.nextInt(max - min + 1) + min;
            }
            cnt += random.nextInt(context.getLootingModifier()+1);
            while (cnt > 0) {
                ItemStack stack = TrinketItem.createTrinketStack(context.getLevel(), trinket, id, random.nextFloat() * (maxQuality - minQuality) + minQuality);
                generatedLoot.add(stack);
                cnt--;
            }
        }
        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<TrinketLootModifier> {

        public static final Serializer INSTANCE = new Serializer();

        @Override
        public TrinketLootModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] ailootcondition) {
            ResourceLocation item = new ResourceLocation(object.get("item").getAsString());
            JsonArray array = object.get("trinkets").getAsJsonArray();
            List<ResourceLocation> trinkets = new ArrayList<>();
            for (JsonElement element : array) {
                trinkets.add(new ResourceLocation(element.getAsString()));
            }
            float chance = object.get("chance").getAsFloat();
            int min = object.get("min").getAsInt();
            int max = object.get("max").getAsInt();
            float looting = object.get("looting").getAsFloat();
            float minQuality = object.get("minquality").getAsFloat();
            float maxQuality = object.get("maxquality").getAsFloat();
            return new TrinketLootModifier(ailootcondition, trinkets, chance, min, max, looting, minQuality, maxQuality);
        }

        @Override
        public JsonObject write(TrinketLootModifier instance) {
            JsonObject object = makeConditions(instance.conditions);
            JsonArray array = new JsonArray();
            for (ResourceLocation id : instance.trinketIds) {
                array.add(id.toString());
            }
            object.add("trinkets", array);
            object.add("chance", new JsonPrimitive(instance.chance));
            object.add("min", new JsonPrimitive(instance.min));
            object.add("max", new JsonPrimitive(instance.max));
            object.add("looting", new JsonPrimitive(instance.lootingFactor));
            object.add("minquality", new JsonPrimitive(instance.minQuality));
            object.add("maxquality", new JsonPrimitive(instance.maxQuality));
            return object;
        }
    }
}
