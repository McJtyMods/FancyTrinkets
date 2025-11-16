package com.mcjty.fancytrinkets.modules.xpcrafter.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapedRecipePattern;

public class XpRecipeSerializer implements RecipeSerializer<XpRecipe> {

    public static final MapCodec<XpRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.STRING.fieldOf("group").forGetter(ShapedRecipe::getGroup),
            ResourceLocation.CODEC.fieldOf("mob").forGetter(XpRecipe::getId),
            ShapedRecipePattern.MAP_CODEC.fieldOf("pattern").forGetter(r -> r.pattern),
            ItemStack.CODEC.fieldOf("result").forGetter(XpRecipe::getResultItem)
    ).apply(instance, XpRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, XpRecipe> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, XpRecipe::getGroup,
            ResourceLocation.STREAM_CODEC, XpRecipe::getId,
            ShapedRecipePattern.STREAM_CODEC, r -> r.pattern,
            ItemStack.STREAM_CODEC, XpRecipe::getResultItem,
            XpRecipe::new
    );


    @Override
    public MapCodec<XpRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, XpRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
