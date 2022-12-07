package com.mcjty.fancytrinkets.modules.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

public class EssenceLootModifier extends LootModifier {

    private final ResourceLocation item;
    private final float chance;
    private final int min;
    private final int max;
    private final float lootingFactor;

    public static final Codec<EssenceLootModifier> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            LOOT_CONDITIONS_CODEC.fieldOf("conditions").forGetter(l -> l.conditions),
            ResourceLocation.CODEC.fieldOf("item").forGetter(l -> l.item),
            Codec.FLOAT.fieldOf("chance").forGetter(l -> l.chance),
            Codec.INT.fieldOf("min").forGetter(l -> l.min),
            Codec.INT.fieldOf("max").forGetter(l -> l.max),
            Codec.FLOAT.fieldOf("looting").forGetter(l -> l.lootingFactor)
    ).apply(instance, EssenceLootModifier::new));


    public EssenceLootModifier(LootItemCondition[] conditionsIn, ResourceLocation item, float chance, int min, int max, float looting) {
        super(conditionsIn);
        this.item = item;
        this.chance = chance;
        this.min = min;
        this.max = max;
        this.lootingFactor = looting;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        Item it = ForgeRegistries.ITEMS.getValue(item);
        if (it == null) {
            return generatedLoot;
        }
        RandomSource random = context.getRandom();
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

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
