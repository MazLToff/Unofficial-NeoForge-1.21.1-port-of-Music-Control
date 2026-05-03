package com.mazltoff.musiccontrol.categories;

import com.mazltoff.musiccontrol.client.MusicControlClient;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.HashSet;
import java.util.List;

import static com.mazltoff.musiccontrol.categories.Music.ALL_MUSICS;
import static com.mazltoff.musiccontrol.categories.Music.ALL_MUSIC_DISCS;
import static com.mazltoff.musiccontrol.categories.Music.DEFAULT_MUSICS;
import static com.mazltoff.musiccontrol.categories.Music.MUSIC_BY_NAMESPACE;

public final class MusicLibrary {
    private static boolean initialized = false;

    private MusicLibrary() {
    }

    public static void init() {
        if (initialized) {
            return;
        }

        MusicCategories.initBasic();

        addDefault("music.menu");
        addDefault("music.game");
        addDefault("music.game.creative");
        addDefault("music.game.credits");
        addDefault("music.game.end");
        addDefault("music.game.end.dragon");

        addDefault("music.nether.basalt_deltas");
        addDefault("music.nether.crimson_forest");
        addDefault("music.nether.nether_wastes");
        addDefault("music.nether.soul_sand_valley");
        addDefault("music.nether.warped_forest");

        addDefault("music.under_water");

        addDefault("music.overworld.badlands");
        addDefault("music.overworld.bamboo_jungle");
        addDefault("music.overworld.cherry_grove");
        addDefault("music.overworld.deep_dark");
        addDefault("music.overworld.desert");
        addDefault("music.overworld.dripstone_caves");
        addDefault("music.overworld.flower_forest");
        addDefault("music.overworld.forest");
        addDefault("music.overworld.frozen_peaks");
        addDefault("music.overworld.grove");
        addDefault("music.overworld.jagged_peaks");
        addDefault("music.overworld.jungle");
        addDefault("music.overworld.lush_caves");
        addDefault("music.overworld.meadow");
        addDefault("music.overworld.old_growth_taiga");
        addDefault("music.overworld.snowy_slopes");
        addDefault("music.overworld.sparse_jungle");
        addDefault("music.overworld.stony_peaks");
        addDefault("music.overworld.swamp");

        addDisc("music_disc.13");
        addDisc("music_disc.cat");
        addDisc("music_disc.blocks");
        addDisc("music_disc.chirp");
        addDisc("music_disc.creator");
        addDisc("music_disc.creator_music_box");
        addDisc("music_disc.far");
        addDisc("music_disc.mall");
        addDisc("music_disc.mellohi");
        addDisc("music_disc.otherside");
        addDisc("music_disc.pigstep");
        addDisc("music_disc.precipice");
        addDisc("music_disc.relic");
        addDisc("music_disc.stal");
        addDisc("music_disc.strad");
        addDisc("music_disc.wait");
        addDisc("music_disc.ward");
        addDisc("music_disc.5");
        addDisc("music_disc.11");

        initialized = true;
    }

    public static List<Music> getCurrentCategoryMusics() {
        init();

        HashSet<Music> musics = MUSIC_BY_NAMESPACE.get(MusicControlClient.currentCategory);

        if (musics == null || musics.isEmpty()) {
            musics = MUSIC_BY_NAMESPACE.get(DEFAULT_MUSICS);
        }

        if (musics == null) {
            return List.of();
        }

        return musics.stream().sorted().toList();
    }

    public static Music selectMusic(boolean next) {
        List<Music> musics = getCurrentCategoryMusics();

        if (musics.isEmpty()) {
            MusicControlClient.musicSelected = null;
            return null;
        }

        ResourceLocation current = MusicControlClient.musicSelected != null
                ? MusicControlClient.musicSelected
                : MusicControlClient.currentMusic;

        int currentIndex = -1;

        if (current != null) {
            for (int i = 0; i < musics.size(); i++) {
                if (musics.get(i).getIdentifier().equals(current)) {
                    currentIndex = i;
                    break;
                }
            }
        }

        int newIndex;

        if (currentIndex < 0) {
            newIndex = next ? 0 : musics.size() - 1;
        } else if (next) {
            newIndex = (currentIndex + 1) % musics.size();
        } else {
            newIndex = currentIndex - 1;

            if (newIndex < 0) {
                newIndex = musics.size() - 1;
            }
        }

        Music selected = musics.get(newIndex);
        MusicControlClient.musicSelected = selected.getIdentifier();
        MusicControlClient.currentMusic = selected.getIdentifier();

        return selected;
    }

    public static Component getSelectedMusicText() {
        if (MusicControlClient.musicSelected == null) {
            return Component.translatable("music_control.message.no_music_selected");
        }

        return Music.getTranslatedText(MusicControlClient.musicSelected);
    }

    private static void addDefault(String path) {
        addToCategory(DEFAULT_MUSICS, path);
    }

    private static void addDisc(String path) {
        addToCategory(ALL_MUSIC_DISCS, path);
    }

    private static void addToCategory(String category, String path) {
        Music music = new Music(ResourceLocation.withDefaultNamespace(path));

        MUSIC_BY_NAMESPACE.computeIfAbsent(category, ignored -> new HashSet<>()).add(music);
        MUSIC_BY_NAMESPACE.computeIfAbsent(ALL_MUSICS, ignored -> new HashSet<>()).add(music);
    }
}