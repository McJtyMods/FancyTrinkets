package com.mcjty.fancytrinkets.setup;
import com.mcjty.fancytrinkets.modules.xpcrafter.XpCrafterModule;
import com.mcjty.fancytrinkets.modules.xpcrafter.recipe.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.*;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.crafting.RecipeHolder;
import java.util.List;
public record RecipeSync(List<XpRecipe> recipes) implements CustomPacketPayload {
    public static final Type<RecipeSync> TYPE = new Type<>(Identifier.parse("fancytrinkets:recipes"));
    public static final StreamCodec<RegistryFriendlyByteBuf, RecipeSync> CODEC = XpRecipeSerializer.STREAM_CODEC.apply(ByteBufCodecs.list()).map(RecipeSync::new, RecipeSync::recipes);
    @Override public Type<? extends CustomPacketPayload> type() { return TYPE; }
    public static void register() {
        PayloadTypeRegistry.clientboundPlay().register(TYPE, CODEC);
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> send(handler.player));
        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, resources, success) -> {
            if (success) for (ServerPlayer player : server.getPlayerList().getPlayers()) send(player);
        });
    }
    private static void send(ServerPlayer player) {
        var recipes = player.level().getServer().getRecipeManager().getRecipes().stream().map(RecipeHolder::value).filter(recipe -> recipe.getType() == XpCrafterModule.XP_RECIPE_TYPE.get()).map(recipe -> (XpRecipe) recipe).toList();
        ServerPlayNetworking.send(player, new RecipeSync(recipes));
    }
}
