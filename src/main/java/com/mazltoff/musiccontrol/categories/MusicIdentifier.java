package com.mazltoff.musiccontrol.categories;

import net.minecraft.resources.ResourceLocation;

public final class MusicIdentifier {
    private MusicIdentifier() {
    }

    public static boolean isDimension(ResourceLocation identifier) {
        String path = identifier.getPath();

        return path.equals("music.game")
                || path.equals("music.nether.nether_wastes")
                || path.equals("music.end");
    }

    public static boolean isBiome(ResourceLocation identifier) {
        String path = identifier.getPath();

        return path.startsWith("music.overworld.")
                || path.startsWith("music.nether.")
                || path.startsWith("music.end.");
    }

    public static boolean isDisc(ResourceLocation identifier) {
        return identifier.getPath().startsWith("music_disc");
    }

    public static boolean isMisc(ResourceLocation identifier) {
        return identifier.getPath().startsWith("music.misc.");
    }
}