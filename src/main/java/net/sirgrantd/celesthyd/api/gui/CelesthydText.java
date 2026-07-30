package net.sirgrantd.celesthyd.api.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

public class CelesthydText {
    private final AbstractContainerScreen<?> parentGui;
    private final int xOffset;
    private final int yOffset;
    private final String text;

    public CelesthydText(AbstractContainerScreen<?> parentGui, int xOffset, int yOffset, String text) {
        this.parentGui = parentGui;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.text = text;
    }

    public void extractContents(GuiGraphicsExtractor guiGraphics) {
        int x = parentGui.getLeftPos() + xOffset;
        int y = parentGui.getTopPos() + yOffset;

        guiGraphics.text(
                Minecraft.getInstance().font, this.text, x, y, -1, false);
    }
}