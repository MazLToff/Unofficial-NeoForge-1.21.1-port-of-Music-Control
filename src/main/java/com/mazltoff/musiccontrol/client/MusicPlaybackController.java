package com.mazltoff.musiccontrol.client;

import com.mazltoff.musiccontrol.Utils;
import com.mazltoff.musiccontrol.categories.Music;
import com.mazltoff.musiccontrol.categories.MusicCategories;
import com.mazltoff.musiccontrol.categories.MusicLibrary;
import com.mazltoff.musiccontrol.config.MusicControlConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;

public final class MusicPlaybackController {
    private MusicPlaybackController() {
    }

    public static void previousMusic(Minecraft minecraft) {
        MusicControlClient.previousMusic = true;

        Music selected = MusicLibrary.selectMusic(false);
        printSelectedMusic(minecraft, selected);
    }

    public static void nextMusic(Minecraft minecraft) {
        MusicControlClient.nextMusic = true;

        Music selected = MusicLibrary.selectMusic(true);
        printSelectedMusic(minecraft, selected);
    }

    public static void togglePause(Minecraft minecraft) {
        MusicControlClient.pauseResume = true;
        MusicControlClient.isPaused = !MusicControlClient.isPaused;

        if (MusicControlClient.isPaused) {
            MinecraftMusicAccess.stopCurrentMusic(minecraft);
        } else {
            MinecraftMusicAccess.setNextSongDelay(minecraft, 0);
        }

        Utils.print(
                minecraft,
                Component.translatable(
                        MusicControlClient.isPaused
                                ? "music_control.message.pause_on"
                                : "music_control.message.pause_off"
                )
        );
    }

    public static void toggleLoop(Minecraft minecraft) {
        MusicControlClient.loopMusic = !MusicControlClient.loopMusic;

        Utils.print(
                minecraft,
                Component.translatable(
                        MusicControlClient.loopMusic
                                ? "music_control.message.loop_on"
                                : "music_control.message.loop_off"
                )
        );
    }

    public static void previousCategory(Minecraft minecraft) {
        MusicControlClient.previousCategory = true;
        MusicControlClient.musicSelected = null;

        MusicCategories.changeCategory(false);

        Utils.print(
                minecraft,
                Component.translatable(
                        "music_control.message.category",
                        MusicCategories.getCurrentCategoryText()
                )
        );
    }

    public static void nextCategory(Minecraft minecraft) {
        MusicControlClient.nextCategory = true;
        MusicControlClient.musicSelected = null;

        MusicCategories.changeCategory(true);

        Utils.print(
                minecraft,
                Component.translatable(
                        "music_control.message.category",
                        MusicCategories.getCurrentCategoryText()
                )
        );
    }

    public static void printCurrentMusic(Minecraft minecraft) {
        MusicControlClient.printMusic = true;

        Utils.print(
                minecraft,
                Component.translatable(
                        "music_control.message.current_music",
                        MusicLibrary.getSelectedMusicText()
                )
        );
    }

    public static void openMenuPlaceholder(Minecraft minecraft) {
        Utils.print(minecraft, Component.translatable("music_control.message.menu_not_ported"));
    }

    public static void changeMusicVolume(Minecraft minecraft, int delta) {
        float current = minecraft.options.getSoundSourceVolume(SoundSource.MUSIC);
        int volume = Math.round(current * 100.0F);
        volume = Math.max(0, Math.min(100, volume + delta));

        minecraft.options.getSoundSourceOptionInstance(SoundSource.MUSIC).set(volume / 100.0);
        minecraft.options.save();

        Utils.print(
                minecraft,
                Component.translatable("music_control.message.volume", volume)
        );
    }

    private static void printSelectedMusic(Minecraft minecraft, Music selected) {
        Utils.print(
                minecraft,
                selected == null
                        ? Component.translatable("music_control.message.no_music_available")
                        : Component.translatable(
                        "music_control.message.selected_music",
                        Music.getTranslatedText(selected.getIdentifier())
                )
        );
    }
}