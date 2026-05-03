package com.mazltoff.musiccontrol.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

public final class MusicControlKeyMappings {
    private static final String CATEGORY = "key.categories.music_control";

    public static final KeyMapping OPEN_MENU = new KeyMapping(
            "key.music_control.open_menu",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_M,
            CATEGORY
    );

    private MusicControlKeyMappings() {
    }

    public static void register(RegisterKeyMappingsEvent event) {
        event.register(OPEN_MENU);
    }

    public static void onClientTick(ClientTickEvent.Post event) {
        while (OPEN_MENU.consumeClick()) {
            Minecraft minecraft = Minecraft.getInstance();

            if (minecraft.player != null) {
                minecraft.player.displayClientMessage(
                        Component.literal("Music Control key works!"),
                        true
                );
            }
        }
    }
}