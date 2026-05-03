package com.mazltoff.musiccontrol;

import com.mazltoff.musiccontrol.config.MusicControlConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

public final class Utils {
    private Utils() {
    }

    public static void print(Minecraft minecraft, Component text) {
        if (!MusicControlConfig.shouldShowActionBarMessages()) {
            return;
        }

        if (minecraft.player != null) {
            minecraft.player.displayClientMessage(text, true);
        }
    }

    public static boolean isNight(Level level) {
        long time = level.getDayTime() % 24000L;
        return time > 13000L && time < 23000L;
    }
}