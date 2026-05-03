package com.mazltoff.musiccontrol.categories;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class Music implements Comparable<Music> {
    public static final String ALL_MUSICS = "all";
    public static final String ALL_MUSIC_DISCS = "disc";
    public static final String DEFAULT_MUSICS = "default";

    public static final ResourceLocation EMPTY_MUSIC_ID = ResourceLocation.fromNamespaceAndPath("minecraft", "missing_sound");
    public static final String EMPTY_MUSIC = EMPTY_MUSIC_ID.toString();

    public static final HashMap<String, HashSet<Music>> MUSIC_BY_NAMESPACE = new HashMap<>();
    public static final HashSet<ResourceLocation> EVENTS = new HashSet<>();

    public static final HashSet<ResourceLocation> BLACK_LISTED_EVENTS = new HashSet<>(
            List.of(ResourceLocation.withDefaultNamespace("music.overworld.old_growth_taiga"))
    );

    public static final HashMap<ResourceLocation, HashSet<Music>> MUSIC_BY_EVENT = new HashMap<>();
    public static final HashMap<ResourceLocation, HashSet<ResourceLocation>> EVENTS_OF_EVENT = new HashMap<>();

    public static final Comparator<ResourceLocation> TRANSLATED_ORDER = (a, b) ->
            String.CASE_INSENSITIVE_ORDER.compare(
                    getTranslatedText(a).getString(),
                    getTranslatedText(b).getString()
            );

    private final ResourceLocation identifier;
    private final HashSet<ResourceLocation> events = new HashSet<>();

    public Music(ResourceLocation identifier) {
        this.identifier = identifier;
    }

    public static Music getMusicFromIdentifier(ResourceLocation identifier) {
        HashSet<Music> allMusics = MUSIC_BY_NAMESPACE.get(ALL_MUSICS);

        if (allMusics == null) {
            return null;
        }

        Optional<Music> music = allMusics.stream()
                .filter(candidate -> candidate.getIdentifier().equals(identifier))
                .findAny();

        return music.orElse(null);
    }

    public ResourceLocation getIdentifier() {
        return identifier;
    }

    public HashSet<ResourceLocation> getEvents() {
        return events;
    }

    public void addEvent(ResourceLocation event) {
        HashSet<Music> eventMusics = MUSIC_BY_EVENT.get(event);

        if (eventMusics != null) {
            eventMusics.add(this);
            events.add(event);
        }
    }

    public void removeEvent(ResourceLocation event) {
        HashSet<Music> eventMusics = MUSIC_BY_EVENT.get(event);

        if (eventMusics != null) {
            eventMusics.remove(this);
            events.remove(event);
        }
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Music music && identifier.equals(music.identifier);
    }

    @Override
    public int hashCode() {
        return identifier.hashCode();
    }

    @Override
    public int compareTo(@NotNull Music music) {
        return identifier.compareTo(music.identifier);
    }

    public static Component getTranslatedText(ResourceLocation identifier) {
        String idString = identifier.toString();
        String path = identifier.getPath();

        if (MusicIdentifier.isBiome(identifier)) {
            String[] split = path.split("\\.", 3);
            String biomePath = split.length >= 3 ? split[2] : path;

            return Component.translatable(
                    "music.format.biome",
                    Component.translatable("biome." + identifier.getNamespace() + "." + biomePath)
            );
        }

        if (MusicIdentifier.isDimension(identifier)) {
            return Component.translatable("music.format.dimension", Component.translatable(path));
        }

        if (MusicIdentifier.isDisc(identifier)) {
            return Component.translatable("music.format.disc", Component.translatable(path));
        }

        if (MusicIdentifier.isMisc(identifier)) {
            return Component.translatable("music.format.misc", Component.translatable(path));
        }

        return Component.translatable(idString);
    }
}