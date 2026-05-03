package com.mazltoff.musiccontrol.categories;

import com.mazltoff.musiccontrol.client.MusicControlClient;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static com.mazltoff.musiccontrol.categories.Music.ALL_MUSICS;
import static com.mazltoff.musiccontrol.categories.Music.ALL_MUSIC_DISCS;
import static com.mazltoff.musiccontrol.categories.Music.DEFAULT_MUSICS;
import static com.mazltoff.musiccontrol.categories.Music.MUSIC_BY_NAMESPACE;

public final class MusicCategories {
    public static final ArrayList<String> CATEGORIES = new ArrayList<>(
            Arrays.asList(ALL_MUSICS, DEFAULT_MUSICS, ALL_MUSIC_DISCS)
    );

    public static final ArrayList<String> NAMESPACES = new ArrayList<>(
            List.of("minecraft")
    );

    public static final LinkedList<ResourceLocation> PLAYED_MUSICS = new LinkedList<>();

    private MusicCategories() {
    }

    public static void initBasic() {
        MUSIC_BY_NAMESPACE.putIfAbsent(ALL_MUSICS, new java.util.HashSet<>());
        MUSIC_BY_NAMESPACE.putIfAbsent(DEFAULT_MUSICS, new java.util.HashSet<>());
        MUSIC_BY_NAMESPACE.putIfAbsent(ALL_MUSIC_DISCS, new java.util.HashSet<>());

        if (!CATEGORIES.contains(MusicControlClient.currentCategory)) {
            MusicControlClient.currentCategory = DEFAULT_MUSICS;
        }
    }

    public static void changeCategory(boolean nextCategory) {
        initBasic();

        int currentIndex = CATEGORIES.indexOf(MusicControlClient.currentCategory);

        if (currentIndex < 0) {
            currentIndex = CATEGORIES.indexOf(DEFAULT_MUSICS);
        }

        int newIndex = nextCategory
                ? (currentIndex + 1) % CATEGORIES.size()
                : currentIndex - 1;

        if (newIndex < 0) {
            newIndex = CATEGORIES.size() - 1;
        }

        MusicControlClient.currentCategory = CATEGORIES.get(newIndex);
        MusicControlClient.categoryChanged = true;
    }

    public static Component getCurrentCategoryText() {
        return getCategoryText(MusicControlClient.currentCategory);
    }

    public static Component getCategoryText(String category) {
        return switch (category) {
            case ALL_MUSICS -> Component.translatable("music.category.all");
            case DEFAULT_MUSICS -> Component.translatable("music.category.default");
            case ALL_MUSIC_DISCS -> Component.translatable("music.category.disc");
            default -> Component.literal(category);
        };
    }
}