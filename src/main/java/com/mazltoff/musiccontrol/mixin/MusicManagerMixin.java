package com.mazltoff.musiccontrol.mixin;

import com.mazltoff.musiccontrol.client.MusicControlClient;
import net.minecraft.client.sounds.MusicManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MusicManager.class)
public abstract class MusicManagerMixin {
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void musiccontrol$pauseMusicTick(CallbackInfo ci) {
        if (MusicControlClient.isPaused) {
            ci.cancel();
        }
    }
}