package com.mcjty.fancytrinkets.datagen;

import com.mcjty.fancytrinkets.FancyTrinkets;
import com.mcjty.fancytrinkets.modules.loot.EssenceLootModifier;
import com.mcjty.fancytrinkets.modules.loot.LootModule;
import com.mcjty.fancytrinkets.modules.trinkets.TrinketsModule;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

import java.util.Map;

public class GenGLM extends GlobalLootModifierProvider {

    public GenGLM(DataGenerator gen) {
        super(gen, FancyTrinkets.MODID);
    }

    @Override
    protected void start() {
        for (Map.Entry<String, LootModule.EssenceGLM> entry : LootModule.ESSENCE_GLMS.entrySet()) {
            LootModule.EssenceGLM glm = entry.getValue();
            add(entry.getKey(), new EssenceLootModifier(new LootItemCondition[] {
                    LootItemKilledByPlayerCondition.killedByPlayer().build()
            }, glm.itemId(), glm.chance(), glm.min(), glm.max(), glm.looting()));
        }
    }
}
