package net.sirgrantd.celesthyd.api.gui;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

public class CelesthydImage {

    private final AbstractContainerScreen<?> parentGui;
    private final int xOffset;
    private final int yOffset;
    private final Identifier texture;

    public CelesthydImage(
            AbstractContainerScreen<?> parentGui,
            int xOffset,
            int yOffset,
            Identifier texture) {
        this.parentGui = parentGui;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.texture = texture;
    }

    public void extractContents(GuiGraphicsExtractor guiGraphics, int sizeX, int sizeY) {
        int x = parentGui.getLeftPos() + xOffset;
        int y = parentGui.getTopPos() + yOffset;

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, texture, x, y, 0, 0, sizeX, sizeY, sizeX, sizeY);
    }
}