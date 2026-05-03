package com.mazltoff.musiccontrol.client;

import com.mazltoff.musiccontrol.mixin.MusicManagerAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import org.jetbrains.annotations.Nullable;

public final class MinecraftMusicAccess {
    private MinecraftMusicAccess() {
    }

    public static SoundInstance getCurrentMusic(Minecraft minecraft) {
        return accessor(minecraft).musiccontrol$getCurrentMusic();
    }

    public static boolean hasCurrentMusic(Minecraft minecraft) {
        return getCurrentMusic(minecraft) != null;
    }

    public static boolean isCurrentMusicActive(Minecraft minecraft) {
        SoundInstance currentMusic = getCurrentMusic(minecraft);

        return currentMusic != null && minecraft.getSoundManager().isActive(currentMusic);
    }

    @Nullable
    public static ResourceLocation getCurrentMusicId(Minecraft minecraft) {
        SoundInstance currentMusic = getCurrentMusic(minecraft);

        if (currentMusic == null) {
            return null;
        }

        return currentMusic.getLocation();
    }

    public static int getNextSongDelay(Minecraft minecraft) {
        return accessor(minecraft).musiccontrol$getNextSongDelay();
    }

    public static void setNextSongDelay(Minecraft minecraft, int delay) {
        accessor(minecraft).musiccontrol$setNextSongDelay(Math.max(0, delay));
    }

    public static void stopCurrentMusic(Minecraft minecraft) {
        accessor(minecraft).musiccontrol$invokeStopPlaying();
        accessor(minecraft).musiccontrol$setCurrentMusic(null);
    }

    public static void playMusic(Minecraft minecraft, ResourceLocation musicId) {
        if (musicId == null) {
            return;
        }

        stopCurrentMusic(minecraft);

        SoundEvent soundEvent = SoundEvent.createVariableRangeEvent(musicId);
        SimpleSoundInstance soundInstance = SimpleSoundInstance.forMusic(soundEvent);

        minecraft.getSoundManager().play(soundInstance);

        accessor(minecraft).musiccontrol$setCurrentMusic(soundInstance);
        accessor(minecraft).musiccontrol$setNextSongDelay(Integer.MAX_VALUE);
    }

    private static MusicManagerAccessor accessor(Minecraft minecraft) {
        MusicManager musicManager = minecraft.getMusicManager();
        return (MusicManagerAccessor) musicManager;
    }
}