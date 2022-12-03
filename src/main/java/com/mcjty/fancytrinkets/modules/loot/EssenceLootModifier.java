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

    public static final Codec<EssenceLootModifier> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            LOOT_CONDITIONS_CODEC.fieldOf("conditions").forGetter(l -> l.conditions),
            Codec.STRING.fieldOf("item").forGetter(l -> l.item.toString())
    ).apply(instance, (conditions, item) -> new EssenceLootModifier(conditions, new ResourceLocation(item))));


    public EssenceLootModifier(LootItemCondition[] conditionsIn, ResourceLocation item) {
        super(conditionsIn);
        this.item = item;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        RandomSource random = context.getRandom();
        if (random.nextFloat() < .5f) {
            Item i = ForgeRegistries.ITEMS.getValue(item);
            if (i != null) {
                generatedLoot.add(new ItemStack(i));
            }
        }
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
