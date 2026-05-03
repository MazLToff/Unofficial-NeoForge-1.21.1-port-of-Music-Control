package com.mazltoff.musiccontrol;

import com.mazltoff.musiccontrol.categories.Music;
import com.mazltoff.musiccontrol.categories.MusicCategories;
import com.mazltoff.musiccontrol.categories.MusicLibrary;
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
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

@Mod(value = MusicControlNeoForge.MODID, dist = Dist.CLIENT)
public class MusicControlNeoForge {
    public static final String MODID = "music_control";
    public static final Logger LOGGER = LogUtils.getLogger();

    public MusicControlNeoForge(IEventBus modBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.CLIENT, MusicControlConfig.SPEC);

        SoundEventRegistry.init();

        // Do not read config values here. Client config is not loaded yet.
        MusicControlClient.currentCategory = Music.DEFAULT_MUSICS;
        MusicCategories.initBasic();
        MusicLibrary.init();

        modBus.addListener(this::onConfigLoaded);
        modBus.addListener(this::onConfigReloaded);

        modBus.addListener(MusicControlKeyMappings::register);
        NeoForge.EVENT_BUS.addListener(MusicControlKeyMappings::onClientTick);

        LOGGER.info("Music Control NeoForge loaded");
    }

    private void onConfigLoaded(ModConfigEvent.Loading event) {
        if (event.getConfig().getSpec() == MusicControlConfig.SPEC) {
            applyClientConfig();
        }
    }

    private void onConfigReloaded(ModConfigEvent.Reloading event) {
        if (event.getConfig().getSpec() == MusicControlConfig.SPEC) {
            applyClientConfig();
        }
    }

    private static void applyClientConfig() {
        MusicControlClient.currentCategory = MusicControlConfig.getStartCategory();
        MusicCategories.initBasic();

        LOGGER.info("Music Control client config loaded");
    }
}