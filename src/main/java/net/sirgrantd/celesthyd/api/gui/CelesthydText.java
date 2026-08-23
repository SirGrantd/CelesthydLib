package net.sirgrantd.celesthyd.api.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
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

    public void render(GuiGraphics guiGraphics) {
        int x = parentGui.getGuiLeft() + xOffset;
        int y = parentGui.getGuiTop() + yOffset;

        guiGraphics.drawString(
                Minecraft.getInstance().font, this.text, x, y, -1, false);
    }

    public void renderText(GuiGraphics guiGraphics) {
        render(guiGraphics);
    }
}