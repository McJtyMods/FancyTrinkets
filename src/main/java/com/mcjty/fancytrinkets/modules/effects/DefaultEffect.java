package com.mcjty.fancytrinkets.modules.effects;

import com.mcjty.fancytrinkets.playerdata.PlayerEffects;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;
import java.util.function.Consumer;

public class DefaultEffect implements IEffect {

    protected final Integer hotkey;
    protected final String toggle;

    public DefaultEffect(Integer hotkey, String toggle) {
        this.hotkey = hotkey;
        this.toggle = toggle;
    }

    protected void executeIfEnabled(ServerPlayer player, Consumer<PlayerEffects> runnable) {
        player.getCapability(PlayerEffects.PLAYER_EFFECTS).ifPresent(playerEffects -> {
            if (toggle != null) {
                if (!playerEffects.isToggleOn(toggle)) {
                    return;
                }
            }
            runnable.accept(playerEffects);
        });
    }

    protected void executeIfEnabled(ServerPlayer player, Runnable runnable) {
        if (toggle != null) {
            player.getCapability(PlayerEffects.PLAYER_EFFECTS).ifPresent(playerEffects -> {
                if (!playerEffects.isToggleOn(toggle)) {
                    return;
                }
                runnable.run();
            });
        } else {
            runnable.run();
        }
    }

    @Override
    public void onUnequip(ItemStack stack, ServerPlayer player, String slotId) {
        turnOff(player);
    }

    @Override
    public void onHotkey(ItemStack stack, ServerPlayer player, String slotId, int key) {
        if (toggle != null && Objects.equals(key, hotkey)) {
            player.getCapability(PlayerEffects.PLAYER_EFFECTS).ifPresent(playerEffects -> {
                if (!playerEffects.toggle(player, toggle)) {
                    turnOff(player);
                }
            });
        }
    }

    protected void turnOff(ServerPlayer player) {
    }

    @Override
    public Integer getHotkey() {
        return hotkey;
    }

    @Override
    public String getToggle() {
        return toggle;
    }
}
