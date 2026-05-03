package com.mazltoff.musiccontrol.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;
import com.mazltoff.musiccontrol.categories.MusicCategories;
import com.mazltoff.musiccontrol.Utils;
import com.mazltoff.musiccontrol.config.MusicControlConfig;

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
            MusicControlClient.previousMusic = true;
            print(minecraft, Component.literal("Previous music"));
        }

        while (NEXT_MUSIC.consumeClick()) {
            MusicControlClient.nextMusic = true;
            print(minecraft, Component.literal("Next music"));
        }

        while (PAUSE_RESUME.consumeClick()) {
            MusicControlClient.pauseResume = true;
            print(minecraft, Component.literal("Pause / resume music"));
        }

        while (LOOP_MUSIC.consumeClick()) {
            MusicControlClient.loopMusic = !MusicControlClient.loopMusic;
            print(minecraft, Component.literal(MusicControlClient.loopMusic ? "Loop music: ON" : "Loop music: OFF"));
        }

        while (PREVIOUS_CATEGORY.consumeClick()) {
            MusicControlClient.previousCategory = true;
            MusicCategories.changeCategory(false);
            print(
                    minecraft,
                    Component.translatable(
                            "music_control.message.category",
                            MusicCategories.getCurrentCategoryText()
                    )
            );
        }

        while (NEXT_CATEGORY.consumeClick()) {
            MusicControlClient.nextCategory = true;
            MusicCategories.changeCategory(true);
            print(
                    minecraft,
                    Component.translatable(
                            "music_control.message.category",
                            MusicCategories.getCurrentCategoryText()
                    )
            );
        }

        while (PRINT_MUSIC.consumeClick()) {
            MusicControlClient.printMusic = true;
            print(minecraft, Component.literal("Current music: unknown yet"));
        }

        while (VOLUME_UP.consumeClick()) {
            changeMusicVolume(minecraft, MusicControlConfig.getVolumeIncrement());
        }

        while (VOLUME_DOWN.consumeClick()) {
            changeMusicVolume(minecraft, -MusicControlConfig.getVolumeIncrement());
        }

        while (OPEN_MENU.consumeClick()) {
            print(minecraft, Component.literal("Music Control menu is not ported yet"));
        }
    }

    private static void changeMusicVolume(Minecraft minecraft, int delta) {
        float current = minecraft.options.getSoundSourceVolume(SoundSource.MUSIC);
        int volume = Math.round(current * 100.0F);
        volume = Math.max(0, Math.min(100, volume + delta));

        minecraft.options.getSoundSourceOptionInstance(SoundSource.MUSIC).set(volume / 100.0);
        minecraft.options.save();

        print(minecraft, Component.literal("Music volume: " + volume + "%"));
    }

    private static void print(Minecraft minecraft, Component message) {
        Utils.print(minecraft, message);
    }
}