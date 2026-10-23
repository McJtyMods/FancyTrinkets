package com.mcjty.fancytrinkets.modules.loot;
import com.mojang.serialization.Codec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import java.util.List;
public interface DropModifier {
    Codec<DropModifier> CODEC = Codec.STRING.dispatch("type", modifier -> modifier instanceof EssenceLootModifier ? "fancytrinkets:essence_loot" : "fancytrinkets:trinket_loot", type -> switch(type) {
        case "fancytrinkets:essence_loot" -> EssenceLootModifier.CODEC;
        case "fancytrinkets:trinket_loot" -> TrinketLootModifier.CODEC;
        default -> throw new IllegalArgumentException("Unknown FancyTrinkets drop modifier: " + type);
    });
    Identifier table();
    List<LootItemCondition> conditions();
    void addDrops(List<ItemStack> drops, LootContext context);
}
