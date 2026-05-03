package com.mazltoff.musiccontrol.client;

import com.mazltoff.musiccontrol.MusicControlNeoForge;
import net.minecraft.resources.ResourceLocation;

public final class MusicControlClient {
    public static boolean init = false;
    public static boolean inCustomTracking = false;
    public static boolean isPaused = false;
    public static boolean shouldPlay = true;
    public static boolean isCurrentEventEmpty = false;
    public static boolean categoryChanged = false;

    public static ResourceLocation musicSelected;
    public static ResourceLocation currentMusic;
    public static ResourceLocation currentEvent;
    public static String currentCategory = "default";

    public static boolean previousMusic = false;
    public static boolean nextMusic = false;
    public static boolean pauseResume = false;
    public static boolean loopMusic = false;
    public static boolean previousCategory = false;
    public static boolean nextCategory = false;
    public static boolean printMusic = false;

    private MusicControlClient() {
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MusicControlNeoForge.MODID, path);
    }
}