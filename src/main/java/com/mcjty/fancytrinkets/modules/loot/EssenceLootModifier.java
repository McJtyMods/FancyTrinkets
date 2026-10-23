package com.mcjty.fancytrinkets.modules.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mcjty.lib.varia.Tools;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

public class EssenceLootModifier implements DropModifier {

    private final Identifier table;
    private final java.util.List<LootItemCondition> conditions;
    @Override public Identifier table() { return table; }
    @Override public java.util.List<LootItemCondition> conditions() { return conditions; }

    private final Identifier item;
    private final float chance;
    private final int min;
    private final int max;
    private final float lootingFactor;

    public static final MapCodec<EssenceLootModifier> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Identifier.CODEC.fieldOf("table").forGetter(l -> l.table),
            net.minecraft.world.level.storage.loot.predicates.LootItemCondition.DIRECT_CODEC.listOf().optionalFieldOf("conditions", java.util.List.of()).forGetter(l -> l.conditions),
            Identifier.CODEC.fieldOf("item").forGetter(l -> l.item),
            Codec.FLOAT.fieldOf("chance").forGetter(l -> l.chance),
            Codec.INT.fieldOf("min").forGetter(l -> l.min),
            Codec.INT.fieldOf("max").forGetter(l -> l.max),
            Codec.FLOAT.fieldOf("looting").forGetter(l -> l.lootingFactor)
    ).apply(instance, EssenceLootModifier::new));

    public EssenceLootModifier(Identifier table, java.util.List<LootItemCondition> conditions, Identifier item, float chance, int min, int max, float looting) {
        this.table = table;
        this.conditions = java.util.List.copyOf(conditions);
        this.item = item;
        this.chance = chance;
        this.min = min;
        this.max = max;
        this.lootingFactor = looting;
    }

    @Override
    public void addDrops(java.util.List<ItemStack> generatedLoot, LootContext context) {
        Item it = Tools.getItem(item);
        if (it == null) {
            return;
        }
        RandomSource random = context.getRandom();
        // @todo 1.21 is context.GetLuck() correct for loot modifiers?
        if (random.nextFloat() < chance + context.getLuck() * lootingFactor) {
            int cnt;
            if (max <= min) {
                cnt = min;
            } else {
                cnt = random.nextInt(max - min + 1) + min;
            }
            cnt += random.nextInt(Math.max(1, (int) context.getLuck() + 1));
            while (cnt > 0) {
                generatedLoot.add(new ItemStack(it));
                cnt--;
            }
        }
        return;
    }

}
