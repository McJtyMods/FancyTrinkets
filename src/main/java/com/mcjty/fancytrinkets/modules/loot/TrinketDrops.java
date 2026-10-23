package com.mcjty.fancytrinkets.modules.loot;
import com.google.gson.*;
import com.mojang.serialization.JsonOps;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.Identifier;
import java.util.*;
public final class TrinketDrops {
    private static Map<Identifier, JsonElement> definitions = Map.of();
    private static HolderLookup.Provider parsedLookup;
    private static Map<Identifier, List<DropModifier>> parsed = Map.of();
    public static void register() {
        LootTableEvents.ALL_LOADED.register((resources, registries) -> {
            Map<Identifier, JsonElement> loaded = new TreeMap<>();
            resources.listResources("fancytrinkets/loot_modifiers", id -> id.getPath().endsWith(".json")).forEach((id, resource) -> {
                try (var reader = resource.openAsReader()) { loaded.put(id, JsonParser.parseReader(reader)); }
                catch (Exception error) { throw new IllegalStateException("Cannot read drop modifier " + id, error); }
            });
            definitions = Map.copyOf(loaded);
            parsedLookup = null;
        });
        LootTableEvents.MODIFY_DROPS.register((table, context, drops) -> {
            var lookup = context.getLevel().getServer().reloadableRegistries().lookup();
            if (lookup != parsedLookup) {
                Map<Identifier, List<DropModifier>> modifiers = new HashMap<>();
                var ops = lookup.createSerializationContext(JsonOps.INSTANCE);
                definitions.forEach((id, json) -> {
                    DropModifier modifier = DropModifier.CODEC.parse(ops, json).getOrThrow(message -> new IllegalStateException("Invalid drop modifier " + id + ": " + message));
                    modifiers.computeIfAbsent(modifier.table(), key -> new ArrayList<>()).add(modifier);
                });
                parsed = modifiers;
                parsedLookup = lookup;
            }
            var key = table.unwrapKey();
            if (key.isPresent()) for (DropModifier modifier : parsed.getOrDefault(key.get().identifier(), List.of())) {
                if (modifier.conditions().stream().allMatch(condition -> condition.test(context))) modifier.addDrops(drops, context);
            }
        });
    }
}
