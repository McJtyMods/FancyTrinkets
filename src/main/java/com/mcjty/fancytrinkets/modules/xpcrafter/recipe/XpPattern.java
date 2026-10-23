package com.mcjty.fancytrinkets.modules.xpcrafter.recipe;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.*;
import net.minecraft.world.item.crafting.Ingredient;
import java.util.*;
public record XpPattern(Map<String, Ingredient> key, List<String> pattern) {
    public static final MapCodec<XpPattern> MAP_CODEC = RecordCodecBuilder.<XpPattern>mapCodec(instance -> instance.group(
            Codec.unboundedMap(Codec.STRING, Ingredient.CODEC).fieldOf("key").forGetter(XpPattern::key),
            Codec.STRING.listOf(1, 5).fieldOf("pattern").forGetter(XpPattern::pattern)
    ).apply(instance, XpPattern::new)).validate(value -> {
        try { value.ingredients(); return DataResult.success(value); }
        catch (IllegalArgumentException error) { return DataResult.error(error::getMessage); }
    });
    public static final StreamCodec<RegistryFriendlyByteBuf, XpPattern> STREAM_CODEC = ByteBufCodecs.fromCodecWithRegistries(MAP_CODEC.codec());
    public XpPattern { key = Map.copyOf(key); pattern = List.copyOf(pattern); }

    public XpPattern trimmed() {
        ingredients();
        int top = 0, bottom = height(), left = width(), right = -1;
        while (top < bottom && pattern.get(top).isBlank()) top++;
        while (bottom > top && pattern.get(bottom - 1).isBlank()) bottom--;
        for (int y = top; y < bottom; y++) for (int x = 0; x < width(); x++) if (pattern.get(y).charAt(x) != ' ') {
            left = Math.min(left, x); right = Math.max(right, x);
        }
        List<String> rows = new ArrayList<>();
        for (int y = top; y < bottom; y++) rows.add(pattern.get(y).substring(left, right + 1));
        return new XpPattern(key, rows);
    }
    public int width() { return pattern.getFirst().length(); }
    public int height() { return pattern.size(); }
    public List<Optional<Ingredient>> ingredients() {
        if (pattern.isEmpty() || pattern.stream().allMatch(String::isBlank) || height() > 5 || width() < 1 || width() > 5) throw new IllegalArgumentException("XP pattern must be 1x1 through 5x5");
        if (key.keySet().stream().anyMatch(symbol -> symbol.length() != 1 || symbol.equals(" "))) throw new IllegalArgumentException("Invalid XP recipe key");
        List<Optional<Ingredient>> ingredients = new ArrayList<>();
        for (String row : pattern) {
            if (row.length() != width()) throw new IllegalArgumentException("XP pattern rows must have equal widths");
            for (int i = 0; i < row.length(); i++) {
                String symbol = row.substring(i, i + 1);
                if (symbol.equals(" ")) ingredients.add(Optional.empty());
                else {
                    Ingredient ingredient = key.get(symbol);
                    if (ingredient == null) throw new IllegalArgumentException("Undefined XP recipe symbol: " + symbol);
                    ingredients.add(Optional.of(ingredient));
                }
            }
        }
        return List.copyOf(ingredients);
    }
}
