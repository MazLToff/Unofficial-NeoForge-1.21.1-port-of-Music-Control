package com.mazltoff.musiccontrol.client;

import com.mazltoff.musiccontrol.Utils;
import com.mazltoff.musiccontrol.categories.Music;
import com.mazltoff.musiccontrol.categories.MusicCategories;
import com.mazltoff.musiccontrol.categories.MusicLibrary;
import com.mazltoff.musiccontrol.config.MusicControlConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;

public final class MusicPlaybackController {
    private static int loopReplayCooldownTicks = 0;

    private MusicPlaybackController() {
    }

    public static void clearSelectedMusic() {
        MusicControlClient.musicSelected = null;
        loopReplayCooldownTicks = 0;
    }

    public static void stopManualMusic(Minecraft minecraft) {
        MinecraftMusicAccess.stopCurrentMusic(minecraft);
        MusicControlClient.currentMusic = null;
        MusicControlClient.musicSelected = null;
        MusicControlClient.isPaused = false;
        loopReplayCooldownTicks = 0;
    }

    public static void tick(Minecraft minecraft) {
        if (loopReplayCooldownTicks > 0) {
            loopReplayCooldownTicks--;
        }

        if (!MusicControlClient.loopMusic) {
            return;
        }

        if (MusicControlClient.isPaused) {
            return;
        }

        if (MusicControlClient.musicSelected == null) {
            return;
        }

        if (MinecraftMusicAccess.isCurrentMusicActive(minecraft)) {
            return;
        }

        if (loopReplayCooldownTicks > 0) {
            return;
        }

        MinecraftMusicAccess.playMusic(minecraft, MusicControlClient.musicSelected);
        loopReplayCooldownTicks = 20;
    }

    public static void previousMusic(Minecraft minecraft) {
        MusicControlClient.previousMusic = true;

        Music selected = MusicLibrary.selectMusic(false);

        if (selected != null) {
            MinecraftMusicAccess.playMusic(minecraft, selected.getIdentifier());
            MusicControlClient.isPaused = false;
            loopReplayCooldownTicks = 20;
        }

        printSelectedMusic(minecraft, selected);
    }

    public static void nextMusic(Minecraft minecraft) {
        MusicControlClient.nextMusic = true;

        Music selected = MusicLibrary.selectMusic(true);

        if (selected != null) {
            MinecraftMusicAccess.playMusic(minecraft, selected.getIdentifier());
            MusicControlClient.isPaused = false;
            loopReplayCooldownTicks = 20;
        }

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

        if (MusicControlClient.loopMusic && MusicControlClient.musicSelected != null) {
            MinecraftMusicAccess.setNextSongDelay(minecraft, Integer.MAX_VALUE);
        }

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
        clearSelectedMusic();

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
        clearSelectedMusic();

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

        ResourceLocation currentMusicId = MinecraftMusicAccess.getCurrentMusicId(minecraft);

        Component musicText;

        if (currentMusicId != null) {
            musicText = Music.getTranslatedText(currentMusicId);
        } else {
            musicText = MusicLibrary.getSelectedMusicText();
        }

        Utils.print(
                minecraft,
                Component.translatable(
                        "music_control.message.current_music",
                        musicText
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