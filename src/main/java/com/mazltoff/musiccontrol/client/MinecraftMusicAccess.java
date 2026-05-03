package com.mazltoff.musiccontrol.client;

import com.mazltoff.musiccontrol.mixin.MusicManagerAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.MusicManager;

public final class MinecraftMusicAccess {
    private MinecraftMusicAccess() {
    }

    public static SoundInstance getCurrentMusic(Minecraft minecraft) {
        return accessor(minecraft).musiccontrol$getCurrentMusic();
    }

    public static boolean hasCurrentMusic(Minecraft minecraft) {
        return getCurrentMusic(minecraft) != null;
    }

    public static int getNextSongDelay(Minecraft minecraft) {
        return accessor(minecraft).musiccontrol$getNextSongDelay();
    }

    public static void setNextSongDelay(Minecraft minecraft, int delay) {
        accessor(minecraft).musiccontrol$setNextSongDelay(Math.max(0, delay));
    }

    public static void stopCurrentMusic(Minecraft minecraft) {
        accessor(minecraft).musiccontrol$invokeStopPlaying();
    }

    private static MusicManagerAccessor accessor(Minecraft minecraft) {
        MusicManager musicManager = minecraft.getMusicManager();
        return (MusicManagerAccessor) musicManager;
    }
}