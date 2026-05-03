package com.mazltoff.musiccontrol.client;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.biome.Biome;

import java.util.HashMap;
import java.util.Map;

public final class SoundEventRegistry {
    public static final Map<ResourceKey<Biome>, SoundEvent> BIOME_MUSIC_MAP = new HashMap<>();
    public static final Map<ResourceLocation, ResourceKey<Biome>> NAME_BIOME_MAP = new HashMap<>();

    public static final ResourceLocation PLAYER_FLYING = vanilla("music.misc.flying");
    public static final ResourceLocation PLAYER_DRIVING = vanilla("music.misc.driving");
    public static final ResourceLocation PLAYER_RIDING = vanilla("music.misc.riding");

    public static final ResourceLocation TIME_NIGHT = vanilla("music.misc.night");

    public static final ResourceLocation WEATHER_RAIN = vanilla("music.misc.rain");
    public static final ResourceLocation WEATHER_THUNDER = vanilla("music.misc.thunder");

    public static final ResourceLocation SNOWY_PLAINS = vanilla("music.overworld.snowy_plains");
    public static final ResourceLocation ICE_SPIKES = vanilla("music.overworld.ice_spikes");
    public static final ResourceLocation SNOWY_TAIGA = vanilla("music.overworld.snowy_taiga");
    public static final ResourceLocation SNOWY_BEACH = vanilla("music.overworld.snowy_beach");

    public static final ResourceLocation WINDSWEPT_HILLS = vanilla("music.overworld.windswept_hills");
    public static final ResourceLocation WINDSWEPT_GRAVELLY_HILLS = vanilla("music.overworld.windswept_gravelly_hills");
    public static final ResourceLocation WINDSWEPT_FOREST = vanilla("music.overworld.windswept_forest");

    public static final ResourceLocation TAIGA = vanilla("music.overworld.taiga");
    public static final ResourceLocation OLD_GROWTH_PINE_TAIGA = vanilla("music.overworld.old_growth_pine_taiga");
    public static final ResourceLocation OLD_GROWTH_SPRUCE_TAIGA = vanilla("music.overworld.old_growth_spruce_taiga");

    public static final ResourceLocation STONY_SHORE = vanilla("music.overworld.stony_shore");
    public static final ResourceLocation PLAINS = vanilla("music.overworld.plains");
    public static final ResourceLocation SUNFLOWER_PLAINS = vanilla("music.overworld.sunflower_plains");

    public static final ResourceLocation BIRCH_FOREST = vanilla("music.overworld.birch_forest");
    public static final ResourceLocation OLD_GROWTH_BIRCH_FOREST = vanilla("music.overworld.old_growth_birch_forest");
    public static final ResourceLocation DARK_FOREST = vanilla("music.overworld.dark_forest");

    public static final ResourceLocation MANGROVE_SWAMP = vanilla("music.overworld.mangrove_swamp");
    public static final ResourceLocation BEACH = vanilla("music.overworld.beach");
    public static final ResourceLocation MUSHROOM_FIELDS = vanilla("music.overworld.mushroom_fields");

    public static final ResourceLocation SAVANNA = vanilla("music.overworld.savanna");
    public static final ResourceLocation SAVANNA_PLATEAU = vanilla("music.overworld.savanna_plateau");
    public static final ResourceLocation WINDSWEPT_SAVANNA = vanilla("music.overworld.windswept_savanna");

    public static final ResourceLocation WOODED_BADLANDS = vanilla("music.overworld.wooded_badlands");
    public static final ResourceLocation ERODED_BADLANDS = vanilla("music.overworld.eroded_badlands");

    public static final ResourceLocation RIVER = vanilla("music.overworld.river");
    public static final ResourceLocation FROZEN_RIVER = vanilla("music.overworld.frozen_river");

    public static final ResourceLocation WARM_OCEAN = vanilla("music.overworld.warm_ocean");
    public static final ResourceLocation LUKEWARM_OCEAN = vanilla("music.overworld.lukewarm_ocean");
    public static final ResourceLocation DEEP_LUKEWARM_OCEAN = vanilla("music.overworld.deep_lukewarm_ocean");
    public static final ResourceLocation OCEAN = vanilla("music.overworld.ocean");
    public static final ResourceLocation DEEP_OCEAN = vanilla("music.overworld.deep_ocean");
    public static final ResourceLocation COLD_OCEAN = vanilla("music.overworld.cold_ocean");
    public static final ResourceLocation DEEP_COLD_OCEAN = vanilla("music.overworld.deep_cold_ocean");
    public static final ResourceLocation FROZEN_OCEAN = vanilla("music.overworld.frozen_ocean");
    public static final ResourceLocation DEEP_FROZEN_OCEAN = vanilla("music.overworld.deep_frozen_ocean");

    public static final ResourceLocation THE_END = vanilla("music.end.the_end");
    public static final ResourceLocation END_HIGHLANDS = vanilla("music.end.end_highlands");
    public static final ResourceLocation END_MIDLANDS = vanilla("music.end.end_midlands");
    public static final ResourceLocation SMALL_END_ISLANDS = vanilla("music.end.small_end_islands");
    public static final ResourceLocation END_BARRENS = vanilla("music.end.end_barrens");

    public static final ResourceLocation NETHER = vanilla("music.nether");

    private SoundEventRegistry() {
    }

    public static void init() {
        // Registry bootstrap placeholder.
        // Actual NeoForge SoundEvent registration will be added when we port the music mixins.
    }

    private static ResourceLocation vanilla(String path) {
        return ResourceLocation.withDefaultNamespace(path);
    }
}