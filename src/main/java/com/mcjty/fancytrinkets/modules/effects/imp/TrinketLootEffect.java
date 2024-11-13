package com.mcjty.fancytrinkets.modules.effects.imp;

import com.mcjty.fancytrinkets.datapack.EffectDescription;
import com.mcjty.fancytrinkets.datapack.IEffectParameters;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class TrinketLootEffect extends EffectImp {

    public record Loot(ItemStack stack, float chance, int min, int max) {}

    public static final Codec<Loot> LOOT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ItemStack.CODEC.fieldOf("stack").forGetter(l -> l.stack),
            Codec.FLOAT.fieldOf("chance").forGetter(l -> l.chance),
            Codec.INT.fieldOf("min").forGetter(l -> l.min),
            Codec.INT.fieldOf("max").forGetter(l -> l.max)
    ).apply(instance, Loot::new));

    private final Set<String> tags;
    private final List<Loot> loot;

    public record Params(Set<String> tags, List<Loot> loot) implements IEffectParameters {
        @Override
        public EffectDescription.EffectType getType() {
            return EffectDescription.EffectType.GROWTICK;
        }

        public static TrinketLootEffect.Params cast(IEffectParameters params) {
            if (params instanceof TrinketLootEffect.Params p) {
                return p;
            }
            throw new RuntimeException("Bad parameter type!");
        }
    }

    public static final Codec<IEffectParameters> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.listOf().fieldOf("tags").forGetter(l -> new ArrayList<>(((TrinketLootEffect.Params)l).tags)),
                    LOOT_CODEC.listOf().fieldOf("loot").forGetter(l -> ((TrinketLootEffect.Params)l).loot)
            ).apply(instance, (strings, loots) -> new TrinketLootEffect.Params(Set.copyOf(strings), List.copyOf(loots))));

    public TrinketLootEffect(Integer hotkey, String toggle, Set<String> tags, List<Loot> loot) {
        super(hotkey, toggle);
        this.tags = tags;
        this.loot = loot;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void generateLoot(@Nonnull Consumer<ItemStack> stackConsumer, RandomSource source) {
        loot.forEach(loot -> {
            if (source.nextFloat() < loot.chance()) {
                int amount = loot.min() + source.nextInt(loot.max() - loot.min() + 1);
                ItemStack stack = loot.stack().copy();
                stack.setCount(amount);
                stackConsumer.accept(stack);
            }
        });
    }

    @Override
    public void tick(ItemStack stack, ServerPlayer player, String slotId) {
    }
}
