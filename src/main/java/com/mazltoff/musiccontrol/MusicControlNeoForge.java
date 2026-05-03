package com.mazltoff.musiccontrol;

import com.mojang.logging.LogUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(value = MusicControlNeoForge.MODID, dist = Dist.CLIENT)
public class MusicControlNeoForge {
    public static final String MODID = "music_control";
    public static final Logger LOGGER = LogUtils.getLogger();

    public MusicControlNeoForge() {
        LOGGER.info("Music Control NeoForge loaded");
    }
}