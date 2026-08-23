package net.sirgrantd.celesthyd.api.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.resources.ResourceLocation;

public class CelesthydImage {

    private final AbstractContainerScreen<?> parentGui;
    private final int xOffset;
    private final int yOffset;
    private final ResourceLocation texture;

    public CelesthydImage(
            AbstractContainerScreen<?> parentGui,
            int xOffset,
            int yOffset,
            ResourceLocation texture) {
        this.parentGui = parentGui;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.texture = texture;
    }

    public void render(GuiGraphics guiGraphics, int sizeX, int sizeY) {
        int x = parentGui.getGuiLeft() + xOffset;
        int y = parentGui.getGuiTop() + yOffset;

        guiGraphics.blit(texture, x, y, 0, 0, sizeX, sizeY, sizeX, sizeY);
    }

    public void renderWidget(GuiGraphics guiGraphics, int sizeX, int sizeY) {
        render(guiGraphics, sizeX, sizeY);
    }
}