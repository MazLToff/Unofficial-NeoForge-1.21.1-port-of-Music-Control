package com.mazltoff.musiccontrol.gui;

import com.mazltoff.musiccontrol.categories.MusicCategories;
import com.mazltoff.musiccontrol.categories.MusicLibrary;
import com.mazltoff.musiccontrol.client.MusicPlaybackController;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class MusicControlScreen extends Screen {
    public MusicControlScreen() {
        super(Component.translatable("screen.music_control.title"));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int startY = this.height / 2 - 72;
        int buttonWidth = 160;
        int buttonHeight = 20;
        int gap = 24;

        addRenderableWidget(Button.builder(
                Component.translatable("screen.music_control.previous"),
                button -> MusicPlaybackController.previousMusic(Minecraft.getInstance())
        ).bounds(centerX - buttonWidth / 2, startY, buttonWidth, buttonHeight).build());

        addRenderableWidget(Button.builder(
                Component.translatable("screen.music_control.next"),
                button -> MusicPlaybackController.nextMusic(Minecraft.getInstance())
        ).bounds(centerX - buttonWidth / 2, startY + gap, buttonWidth, buttonHeight).build());

        addRenderableWidget(Button.builder(
                Component.translatable("screen.music_control.pause"),
                button -> MusicPlaybackController.togglePause(Minecraft.getInstance())
        ).bounds(centerX - buttonWidth / 2, startY + gap * 2, buttonWidth, buttonHeight).build());

        addRenderableWidget(Button.builder(
                Component.translatable("screen.music_control.loop"),
                button -> MusicPlaybackController.toggleLoop(Minecraft.getInstance())
        ).bounds(centerX - buttonWidth / 2, startY + gap * 3, buttonWidth, buttonHeight).build());

        addRenderableWidget(Button.builder(
                Component.translatable("screen.music_control.current"),
                button -> MusicPlaybackController.printCurrentMusic(Minecraft.getInstance())
        ).bounds(centerX - buttonWidth / 2, startY + gap * 4, buttonWidth, buttonHeight).build());

        addRenderableWidget(Button.builder(
                Component.translatable("screen.music_control.close"),
                button -> this.onClose()
        ).bounds(centerX - buttonWidth / 2, startY + gap * 5, buttonWidth, buttonHeight).build());
    }

    @Override
    public void onClose() {
        Minecraft.getInstance().setScreen(null);
    }

    public static Component getStatusText() {
        return Component.translatable(
                "screen.music_control.status",
                MusicCategories.getCurrentCategoryText(),
                MusicLibrary.getSelectedMusicText()
        );
    }
}