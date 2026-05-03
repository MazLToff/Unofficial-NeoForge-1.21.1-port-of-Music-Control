package com.mazltoff.musiccontrol.client;

import com.mazltoff.musiccontrol.config.MusicControlConfig;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

public final class MusicControlKeyMappings {
    private static final String CATEGORY = "key.categories.music_control";

    private static final KeyMapping PREVIOUS_MUSIC = new KeyMapping(
            "key.music_control.previousMusic",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_LEFT,
            CATEGORY
    );

    private static final KeyMapping NEXT_MUSIC = new KeyMapping(
            "key.music_control.nextMusic",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT,
            CATEGORY
    );

    private static final KeyMapping PAUSE_RESUME = new KeyMapping(
            "key.music_control.pause",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT_SHIFT,
            CATEGORY
    );

    private static final KeyMapping LOOP_MUSIC = new KeyMapping(
            "key.music_control.loop",
            InputConstants.Type.KEYSYM,
            InputConstants.UNKNOWN.getValue(),
            CATEGORY
    );

    private static final KeyMapping PREVIOUS_CATEGORY = new KeyMapping(
            "key.music_control.previousCategory",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_PAGE_UP,
            CATEGORY
    );

    private static final KeyMapping NEXT_CATEGORY = new KeyMapping(
            "key.music_control.nextCategory",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_PAGE_DOWN,
            CATEGORY
    );

    private static final KeyMapping PRINT_MUSIC = new KeyMapping(
            "key.music_control.print",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT_CONTROL,
            CATEGORY
    );

    private static final KeyMapping VOLUME_UP = new KeyMapping(
            "key.music_control.volumeUp",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_UP,
            CATEGORY
    );

    private static final KeyMapping VOLUME_DOWN = new KeyMapping(
            "key.music_control.volumeDown",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_DOWN,
            CATEGORY
    );

    private static final KeyMapping OPEN_MENU = new KeyMapping(
            "key.music_control.openMenu",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_M,
            CATEGORY
    );

    private MusicControlKeyMappings() {
    }

    public static void register(RegisterKeyMappingsEvent event) {
        event.register(PREVIOUS_MUSIC);
        event.register(NEXT_MUSIC);
        event.register(PAUSE_RESUME);
        event.register(LOOP_MUSIC);
        event.register(PREVIOUS_CATEGORY);
        event.register(NEXT_CATEGORY);
        event.register(PRINT_MUSIC);
        event.register(VOLUME_UP);
        event.register(VOLUME_DOWN);
        event.register(OPEN_MENU);
    }

    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();

        while (PREVIOUS_MUSIC.consumeClick()) {
            MusicPlaybackController.previousMusic(minecraft);
        }

        while (NEXT_MUSIC.consumeClick()) {
            MusicPlaybackController.nextMusic(minecraft);
        }

        while (PAUSE_RESUME.consumeClick()) {
            MusicPlaybackController.togglePause(minecraft);
        }

        while (LOOP_MUSIC.consumeClick()) {
            MusicPlaybackController.toggleLoop(minecraft);
        }

        while (PREVIOUS_CATEGORY.consumeClick()) {
            MusicPlaybackController.previousCategory(minecraft);
        }

        while (NEXT_CATEGORY.consumeClick()) {
            MusicPlaybackController.nextCategory(minecraft);
        }

        while (PRINT_MUSIC.consumeClick()) {
            MusicPlaybackController.printCurrentMusic(minecraft);
        }

        while (VOLUME_UP.consumeClick()) {
            MusicPlaybackController.changeMusicVolume(
                    minecraft,
                    MusicControlConfig.getVolumeIncrement()
            );
        }

        while (VOLUME_DOWN.consumeClick()) {
            MusicPlaybackController.changeMusicVolume(
                    minecraft,
                    -MusicControlConfig.getVolumeIncrement()
            );
        }

        while (OPEN_MENU.consumeClick()) {
            MusicPlaybackController.openMenuPlaceholder(minecraft);
        }
    }
}