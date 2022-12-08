package com.mcjty.fancytrinkets.modules.loot;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class EssenceLootModifier extends LootModifier {

    private final ResourceLocation item;
    private final float chance;
    private final int min;
    private final int max;
    private final float lootingFactor;

    public EssenceLootModifier(LootItemCondition[] conditionsIn, ResourceLocation item, float chance, int min, int max, float looting) {
        super(conditionsIn);
        this.item = item;
        this.chance = chance;
        this.min = min;
        this.max = max;
        this.lootingFactor = looting;
    }

    @NotNull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        Item it = ForgeRegistries.ITEMS.getValue(item);
        if (it == null) {
            return generatedLoot;
        }
        Random random = context.getRandom();
        if (random.nextFloat() < chance + context.getLootingModifier() * lootingFactor) {
            int cnt;
            if (max <= min) {
                cnt = min;
            } else {
                cnt = random.nextInt(max - min + 1) + min;
            }
            cnt += random.nextInt(context.getLootingModifier()+1);
            while (cnt > 0) {
                generatedLoot.add(new ItemStack(it));
                cnt--;
            }
        }
        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<EssenceLootModifier> {

        public static final Serializer INSTANCE = new Serializer();

        @Override
        public EssenceLootModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] ailootcondition) {
            ResourceLocation item = new ResourceLocation(object.get("item").getAsString());
            float chance = object.get("chance").getAsFloat();
            int min = object.get("min").getAsInt();
            int max = object.get("max").getAsInt();
            float looting = object.get("looting").getAsFloat();
            return new EssenceLootModifier(ailootcondition, item, chance, min, max, looting);
        }

        @Override
        public JsonObject write(EssenceLootModifier instance) {
            JsonObject object = makeConditions(instance.conditions);
            object.add("item", new JsonPrimitive(instance.item.toString()));
            object.add("chance", new JsonPrimitive(instance.chance));
            object.add("min", new JsonPrimitive(instance.min));
            object.add("max", new JsonPrimitive(instance.max));
            object.add("looting", new JsonPrimitive(instance.lootingFactor));
            return object;
        }
    }
}
