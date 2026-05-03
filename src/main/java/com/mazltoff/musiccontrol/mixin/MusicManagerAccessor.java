package com.mazltoff.musiccontrol.mixin;

import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.MusicManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MusicManager.class)
public interface MusicManagerAccessor {
    @Accessor("currentMusic")
    SoundInstance musiccontrol$getCurrentMusic();

    @Accessor("currentMusic")
    void musiccontrol$setCurrentMusic(SoundInstance currentMusic);

    @Accessor("nextSongDelay")
    int musiccontrol$getNextSongDelay();

    @Accessor("nextSongDelay")
    void musiccontrol$setNextSongDelay(int nextSongDelay);

    @Invoker("stopPlaying")
    void musiccontrol$invokeStopPlaying();
}