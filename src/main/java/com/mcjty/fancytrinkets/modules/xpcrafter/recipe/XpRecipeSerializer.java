package com.mcjty.fancytrinkets.modules.xpcrafter.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.RecipeSerializer;

import net.minecraft.world.item.crafting.ShapedRecipePattern;

public class XpRecipeSerializer {

    public static final MapCodec<XpRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.STRING.fieldOf("group").forGetter(XpRecipe::getGroup),
            Identifier.CODEC.fieldOf("mob").forGetter(XpRecipe::getId),
            ShapedRecipePattern.MAP_CODEC.fieldOf("pattern").forGetter(r -> r.pattern),
            ItemStackTemplate.CODEC.fieldOf("result").forGetter(XpRecipe::getResultTemplate)
    ).apply(instance, XpRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, XpRecipe> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, XpRecipe::getGroup,
            Identifier.STREAM_CODEC, XpRecipe::getId,
            ShapedRecipePattern.STREAM_CODEC, r -> r.pattern,
            ItemStackTemplate.STREAM_CODEC, XpRecipe::getResultTemplate,
            XpRecipe::new
    );

    public static final RecipeSerializer<XpRecipe> SERIALIZER = new RecipeSerializer<>(CODEC, STREAM_CODEC);
}
