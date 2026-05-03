package com.mazltoff.musiccontrol;

import com.mazltoff.musiccontrol.client.MusicControlKeyMappings;
import com.mojang.logging.LogUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

@Mod(value = MusicControlNeoForge.MODID, dist = Dist.CLIENT)
public class MusicControlNeoForge {
    public static final String MODID = "music_control";
    public static final Logger LOGGER = LogUtils.getLogger();

    public MusicControlNeoForge(IEventBus modBus) {
        modBus.addListener(MusicControlKeyMappings::register);
        NeoForge.EVENT_BUS.addListener(MusicControlKeyMappings::onClientTick);

        LOGGER.info("Music Control NeoForge loaded");
    }
}
