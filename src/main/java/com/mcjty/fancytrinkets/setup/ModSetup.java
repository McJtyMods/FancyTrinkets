package com.mcjty.fancytrinkets.setup;

import com.mcjty.fancytrinkets.compat.XpRecipeCategory;
import com.mcjty.fancytrinkets.keys.KeyBindings;
import com.mcjty.fancytrinkets.modules.effects.DefaultEffects;
import com.mcjty.fancytrinkets.modules.trinkets.DefaultTrinkets;
import com.mcjty.fancytrinkets.modules.trinkets.items.TrinketItem;
import mcjty.lib.datagen.DataGen;
import mcjty.lib.datagen.Dob;
import mcjty.lib.setup.DefaultModSetup;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.Map;

public class ModSetup extends DefaultModSetup {

    @Override
    public void init(FMLCommonSetupEvent e) {
        super.init(e);

        NeoForge.EVENT_BUS.register(new ForgeEventHandlers());
        Messages.registerMessages();
    }

    public void datagen(DataGen dataGen) {
        dataGen.add(
                Dob.builder()
                        .message("itemGroup.fancytrinkets", "Fancy Trinkets")
                        .message(TrinketItem.MESSAGE_FANCYTRINKETS_SHIFTMESSAGE, "<Press Shift>")
                        .message(TrinketItem.MESSAGE_FANCYTRINKETS_BONUS, "Bonus effects:")
                        .message(KeyBindings.FANCYTRINKETS_KEY_TOGGLE_1, "Fancy Trinkets Toggle 1")
                        .message(KeyBindings.FANCYTRINKETS_KEY_TOGGLE_2, "Fancy Trinkets Toggle 2")
                        .message(KeyBindings.FANCYTRINKETS_KEY_TOGGLE_3, "Fancy Trinkets Toggle 3")
                        .message(KeyBindings.FANCYTRINKETS_KEY_TOGGLE_4, "Fancy Trinkets Toggle 4")
                        .message(KeyBindings.FANCYTRINKETS_KEY_TOGGLE_5, "Fancy Trinkets Toggle 5")
                        .message(KeyBindings.FANCYTRINKETS_KEY_TOGGLE_6, "Fancy Trinkets Toggle 6")
                        .message(KeyBindings.FANCYTRINKETS_KEY_TOGGLE_7, "Fancy Trinkets Toggle 7")
                        .message(KeyBindings.FANCYTRINKETS_KEY_TOGGLE_8, "Fancy Trinkets Toggle 8")
                        .message(KeyBindings.KEY_CATEGORIES_FANCYTRINKETS, "Fancy Trinkets")
                        .message(XpRecipeCategory.KEY_XP_RECIPE_CATEGORY, "Experience Crafter")
        );
        for (Map.Entry<ResourceLocation, DefaultTrinkets.TrinketInfo> entry : DefaultTrinkets.DEFAULT_TRINKETS.entrySet()) {
            ResourceLocation key = entry.getKey();
            dataGen.add(
                    Dob.builder()
                            .message("trinket." + key.getNamespace() + "." + key.getPath() + ".name", entry.getValue().name())
                            .message("trinket." + key.getNamespace() + "." + key.getPath() + ".description", entry.getValue().description())
            );
        }
        for (Map.Entry<ResourceLocation, DefaultEffects.EffectInfo> entry : DefaultEffects.DEFAULT_EFFECTS.entrySet()) {
            dataGen.add(
                    Dob.builder()
                            .message("effectId.fancytrinkets." + entry.getKey().getPath(), entry.getValue().description())
            );
        }
    }

    @Override
    protected void setupModCompat() {
    }
}
