package com.mazltoff.musiccontrol.categories;

import com.mazltoff.musiccontrol.MusicControlNeoForge;
import com.mazltoff.musiccontrol.config.MusicControlConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.HashSet;

import static com.mazltoff.musiccontrol.categories.Music.ALL_MUSICS;
import static com.mazltoff.musiccontrol.categories.Music.ALL_MUSIC_DISCS;
import static com.mazltoff.musiccontrol.categories.Music.DEFAULT_MUSICS;
import static com.mazltoff.musiccontrol.categories.Music.MUSIC_BY_NAMESPACE;

public final class MusicScanner {
    private static boolean scanned = false;

    private MusicScanner() {
    }

    public static void scanBuiltInSoundEvents() {
        if (scanned) {
            return;
        }

        MusicCategories.initBasic();

        for (ResourceLocation soundId : BuiltInRegistries.SOUND_EVENT.keySet()) {
            if (!isMusicSound(soundId)) {
                continue;
            }

            addScannedMusic(soundId);
        }

        scanned = true;
    }

    private static boolean isMusicSound(ResourceLocation soundId) {
        String path = soundId.getPath();

        return path.startsWith("music.")
                || path.startsWith("music_disc.");
    }

    private static void addScannedMusic(ResourceLocation soundId) {
        Music music = new Music(soundId);

        MUSIC_BY_NAMESPACE.computeIfAbsent(ALL_MUSICS, ignored -> new HashSet<>()).add(music);

        if (MusicIdentifier.isDisc(soundId)) {
            MUSIC_BY_NAMESPACE.computeIfAbsent(ALL_MUSIC_DISCS, ignored -> new HashSet<>()).add(music);
        } else {
            MUSIC_BY_NAMESPACE.computeIfAbsent(DEFAULT_MUSICS, ignored -> new HashSet<>()).add(music);
        }

        String namespace = soundId.getNamespace();

        MUSIC_BY_NAMESPACE.computeIfAbsent(namespace, ignored -> new HashSet<>()).add(music);

        if (!MusicCategories.NAMESPACES.contains(namespace)) {
            MusicCategories.NAMESPACES.add(namespace);
        }

        if (!MusicCategories.CATEGORIES.contains(namespace)) {
            MusicCategories.CATEGORIES.add(namespace);
        }
    }
}