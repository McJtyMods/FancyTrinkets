package com.mcjty.fancytrinkets.modules.loot;

import com.mcjty.fancytrinkets.datapack.CustomRegistries;
import com.mcjty.fancytrinkets.datapack.TrinketDescription;
import com.mcjty.fancytrinkets.modules.trinkets.items.TrinketItem;
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

import java.util.List;
import java.util.stream.Collectors;

public class TrinketLootModifier extends LootModifier {

    private final List<ResourceLocation> trinketIds;
    private final float chance;
    private final int min;
    private final int max;
    private final float lootingFactor;
    private final float minQuality;
    private final float maxQuality;

    public static final Codec<TrinketLootModifier> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            LOOT_CONDITIONS_CODEC.fieldOf("conditions").forGetter(l -> l.conditions),
            Codec.list(Codec.STRING).fieldOf("trinkets").forGetter(l -> l.trinketIds.stream().map(ResourceLocation::toString).collect(Collectors.toList())),
            Codec.FLOAT.fieldOf("chance").forGetter(l -> l.chance),
            Codec.INT.fieldOf("min").forGetter(l -> l.min),
            Codec.INT.fieldOf("max").forGetter(l -> l.max),
            Codec.FLOAT.fieldOf("looting").forGetter(l -> l.lootingFactor),
            Codec.FLOAT.fieldOf("minquality").forGetter(l -> l.minQuality),
            Codec.FLOAT.fieldOf("maxquality").forGetter(l -> l.maxQuality)
    ).apply(instance, (conditions, trinkets, chance, min, max, looting, minquality, maxquality)
            -> new TrinketLootModifier(conditions,
            trinkets.stream().map(ResourceLocation::new).collect(Collectors.toList()),
            chance, min, max, looting, minquality, maxquality)));


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

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        RandomSource random = context.getRandom();
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

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
