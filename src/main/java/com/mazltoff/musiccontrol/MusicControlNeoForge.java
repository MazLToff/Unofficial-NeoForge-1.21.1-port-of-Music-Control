package com.mazltoff.musiccontrol;

import com.mazltoff.musiccontrol.categories.MusicCategories;
import com.mazltoff.musiccontrol.client.MusicControlClient;
import com.mazltoff.musiccontrol.client.MusicControlKeyMappings;
import com.mazltoff.musiccontrol.client.SoundEventRegistry;
import com.mazltoff.musiccontrol.config.MusicControlConfig;
import com.mojang.logging.LogUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;
import com.mazltoff.musiccontrol.categories.MusicLibrary;
import com.mazltoff.musiccontrol.categories.MusicScanner;

@Mod(value = MusicControlNeoForge.MODID, dist = Dist.CLIENT)
public class MusicControlNeoForge {
    public static final String MODID = "music_control";
    public static final Logger LOGGER = LogUtils.getLogger();

    public MusicControlNeoForge(IEventBus modBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.CLIENT, MusicControlConfig.SPEC);

        SoundEventRegistry.init();

        MusicControlClient.currentCategory = MusicControlConfig.getStartCategory();
        MusicCategories.initBasic();
        MusicLibrary.init();
        MusicScanner.scanBuiltInSoundEvents();

        modBus.addListener(MusicControlKeyMappings::register);
        NeoForge.EVENT_BUS.addListener(MusicControlKeyMappings::onClientTick);

        LOGGER.info("Music Control NeoForge loaded");
    }
}