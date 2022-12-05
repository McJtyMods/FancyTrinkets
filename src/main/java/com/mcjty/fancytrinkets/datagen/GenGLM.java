package com.mcjty.fancytrinkets.datagen;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.modules.loot.EssenceLootModifier;
import com.mcjty.fancytrinkets.modules.loot.LootModule;
import com.mcjty.fancytrinkets.modules.loot.TrinketLootModifier;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.EntityTypePredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

import java.util.List;
import java.util.Map;

public class GenGLM extends GlobalLootModifierProvider {

    public GenGLM(DataGenerator gen) {
        super(gen, FancyTrinkets.MODID);
    }

    @Override
    protected void start() {
        for (Map.Entry<String, LootModule.EssenceGLM> entry : LootModule.ESSENCE_GLMS.entrySet()) {
            LootModule.EssenceGLM glm = entry.getValue();
            add(entry.getKey(), new EssenceLootModifier(new LootItemCondition[]{
                    LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS,
                            EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(glm.type()))).build(),
                    LootItemKilledByPlayerCondition.killedByPlayer().build()
            }, glm.itemId(), glm.chance(), glm.min(), glm.max(), glm.looting()));
        }

        add("wither_trinket", new TrinketLootModifier(new LootItemCondition[]{
                LootTableIdCondition.builder(new ResourceLocation("minecraft", "entities/wither")).build()
        }, List.of(new ResourceLocation(FancyTrinkets.MODID, "regeneration_ring")), 0.5f, 1, 1, 0, 60, 70));

        add("dragon_trinket", new TrinketLootModifier(new LootItemCondition[]{
                LootTableIdCondition.builder(new ResourceLocation("minecraft", "entities/ender_dragon")).build()
        }, List.of(new ResourceLocation(FancyTrinkets.MODID, "power_star")), 1.0f, 1, 1, 0, 90, 100));

        add("enderman_trinket", new TrinketLootModifier(new LootItemCondition[]{
                LootTableIdCondition.builder(new ResourceLocation("minecraft", "entities/enderman")).build()
        }, List.of(new ResourceLocation(FancyTrinkets.MODID, "warp_pearl")), 0.02f, 1, 1, 0, 90, 100));
    }
}
