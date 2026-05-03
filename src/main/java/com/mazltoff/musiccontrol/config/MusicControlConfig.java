package com.mazltoff.musiccontrol.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class MusicControlConfig {
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.ConfigValue<String> START_CATEGORY;
    public static final ModConfigSpec.IntValue VOLUME_INCREMENT;
    public static final ModConfigSpec.BooleanValue SHOW_ACTION_BAR_MESSAGES;
    public static final ModConfigSpec.BooleanValue DEBUG_LOGGING;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder.push("general");

        START_CATEGORY = builder
                .comment("Music category selected when Minecraft starts. Valid values for now: default, all, disc.")
                .define("startCategory", "default");

        VOLUME_INCREMENT = builder
                .comment("How many percent the music volume changes when pressing the volume keys.")
                .defineInRange("volumeIncrement", 5, 1, 100);

        SHOW_ACTION_BAR_MESSAGES = builder
                .comment("Show Music Control messages above the hotbar.")
                .define("showActionBarMessages", true);

        DEBUG_LOGGING = builder
                .comment("Enable extra debug logs.")
                .define("debugLogging", false);

        builder.pop();

        SPEC = builder.build();
    }

    private MusicControlConfig() {
    }

    public static String getStartCategory() {
        return START_CATEGORY.get();
    }

    public static int getVolumeIncrement() {
        return VOLUME_INCREMENT.get();
    }

    public static boolean shouldShowActionBarMessages() {
        return SHOW_ACTION_BAR_MESSAGES.get();
    }

    public static boolean debugLogging() {
        return DEBUG_LOGGING.get();
    }
}