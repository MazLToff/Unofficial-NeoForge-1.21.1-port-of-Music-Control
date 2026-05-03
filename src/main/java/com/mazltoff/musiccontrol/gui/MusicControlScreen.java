package com.mazltoff.musiccontrol.gui;

import com.mazltoff.musiccontrol.categories.MusicCategories;
import com.mazltoff.musiccontrol.categories.MusicLibrary;
import com.mazltoff.musiccontrol.client.MusicControlClient;
import com.mazltoff.musiccontrol.client.MusicPlaybackController;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class MusicControlScreen extends Screen {
    private static final int BUTTON_WIDTH = 180;
    private static final int BUTTON_HEIGHT = 20;
    private static final int BUTTON_GAP = 24;

    public MusicControlScreen() {
        super(Component.translatable("screen.music_control.title"));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int startY = Math.max(48, this.height / 2 - 96);

        addCenteredButton(
                centerX,
                startY,
                Component.translatable("screen.music_control.previous_category"),
                button -> MusicPlaybackController.previousCategory(Minecraft.getInstance())
        );

        addCenteredButton(
                centerX,
                startY + BUTTON_GAP,
                Component.translatable("screen.music_control.next_category"),
                button -> MusicPlaybackController.nextCategory(Minecraft.getInstance())
        );

        addCenteredButton(
                centerX,
                startY + BUTTON_GAP * 2,
                Component.translatable("screen.music_control.previous"),
                button -> MusicPlaybackController.previousMusic(Minecraft.getInstance())
        );

        addCenteredButton(
                centerX,
                startY + BUTTON_GAP * 3,
                Component.translatable("screen.music_control.next"),
                button -> MusicPlaybackController.nextMusic(Minecraft.getInstance())
        );

        addCenteredButton(
                centerX,
                startY + BUTTON_GAP * 4,
                Component.translatable("screen.music_control.pause"),
                button -> MusicPlaybackController.togglePause(Minecraft.getInstance())
        );

        addCenteredButton(
                centerX,
                startY + BUTTON_GAP * 5,
                Component.translatable("screen.music_control.loop"),
                button -> MusicPlaybackController.toggleLoop(Minecraft.getInstance())
        );

        addCenteredButton(
                centerX,
                startY + BUTTON_GAP * 6,
                Component.translatable("screen.music_control.current"),
                button -> MusicPlaybackController.printCurrentMusic(Minecraft.getInstance())
        );

        addCenteredButton(
                centerX,
                startY + BUTTON_GAP * 7,
                Component.translatable("screen.music_control.stop"),
                button -> MusicPlaybackController.stopMusic(Minecraft.getInstance())
        );

        addCenteredButton(
                centerX,
                startY + BUTTON_GAP * 8,
                Component.translatable("screen.music_control.close"),
                button -> this.onClose()
        );
    }

    private void addCenteredButton(int centerX, int y, Component text, Button.OnPress onPress) {
        addRenderableWidget(Button.builder(text, onPress)
                .bounds(centerX - BUTTON_WIDTH / 2, y, BUTTON_WIDTH, BUTTON_HEIGHT)
                .build());
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics, mouseX, mouseY, partialTick);

        graphics.drawCenteredString(
                this.font,
                this.title,
                this.width / 2,
                40,
                0xFFFFFF
        );

        graphics.drawCenteredString(
                this.font,
                getStatusText(),
                this.width / 2,
                54,
                0xA0A0A0
        );

        graphics.drawCenteredString(
                this.font,
                getPlaybackStateText(),
                this.width / 2,
                68,
                0xA0A0A0
        );

        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public void onClose() {
        Minecraft.getInstance().setScreen(null);
    }

    private static Component getStatusText() {
        return Component.translatable(
                "screen.music_control.status",
                MusicCategories.getCurrentCategoryText(),
                MusicLibrary.getSelectedMusicText()
        );
    }

    private static Component getPlaybackStateText() {
        Component pauseState = Component.translatable(
                MusicControlClient.isPaused
                        ? "screen.music_control.paused"
                        : "screen.music_control.playing"
        );

        Component loopState = Component.translatable(
                MusicControlClient.loopMusic
                        ? "screen.music_control.loop_enabled"
                        : "screen.music_control.loop_disabled"
        );

        return Component.translatable(
                "screen.music_control.playback_state",
                pauseState,
                loopState
        );
    }
}